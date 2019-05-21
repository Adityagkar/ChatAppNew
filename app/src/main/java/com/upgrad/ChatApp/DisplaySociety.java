package com.upgrad.ChatApp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class DisplaySociety extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase mfirebaseDatabase;
    DatabaseReference mRef;
    AlertDialog.Builder dialog;


    FirebaseRecyclerAdapter<SocietyData,ViewHolder> firebaseRecyclerAdapter;
    SocietyData SocietyData;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.society_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.add:
         Intent intent = new Intent(DisplaySociety.this,EditSociety.class);
         startActivity(intent);
            return(true);


    }
        return(super.onOptionsItemSelected(item));
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        SocietyData = new SocietyData();

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetch();
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mfirebaseDatabase.getReference("Societies");

    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseRecyclerAdapter.startListening();


    }



    @Override
    protected void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
    }


    private void fetch() {
        final Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Societies");

        FirebaseRecyclerOptions<SocietyData> options =
                new FirebaseRecyclerOptions.Builder<SocietyData>()
                        .setQuery(query, SocietyData.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<SocietyData, ViewHolder>(options) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item, parent, false);

                return new ViewHolder(view);
            }


            @Override
            protected void onBindViewHolder(ViewHolder holder, final int position, final SocietyData SocietyData) {

                holder.setDetails(getApplicationContext(), SocietyData.getName() , SocietyData.getDetails(), SocietyData.getUrl());
                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(DisplaySociety.this,MainActivity.class);

                        //Toast.makeText(DisplaySociety.this, String.valueOf(SocietyData.getName()), Toast.LENGTH_SHORT).show();
                        intent.putExtra("SOCIETY",SocietyData.getName());
                        startActivity(intent);
                    }
                });
            }

        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
