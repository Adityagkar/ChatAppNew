package com.upgrad.ChatApp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EditSociety extends AppCompatActivity {
    EditText societyName, description;
    TextView heading;
    Button add, delete;
    private RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_society);

        societyName = findViewById(R.id.societyname);
        description = findViewById(R.id.description);
        add = findViewById(R.id.add);
        delete = findViewById(R.id.delete);
        heading = findViewById(R.id.heading);
        description.setVisibility(View.INVISIBLE);
        radioGroup = findViewById(R.id.rg);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.radioButton2){
                    //add
                    add.setText("add");
                    add.setVisibility(View.VISIBLE);
                    delete.setVisibility(View.INVISIBLE);
                    heading.setVisibility(View.VISIBLE);
                    description.setVisibility(View.VISIBLE);
                    findViewById(R.id.warning).setVisibility(View.INVISIBLE);

                }else if(checkedId==R.id.radioButton3){
                    //delete
                    delete.setVisibility(View.VISIBLE);
                    add.setVisibility(View.GONE);
                    heading.setVisibility(View.GONE);
                    description.setVisibility(View.GONE);
                    findViewById(R.id.warning).setVisibility(View.VISIBLE);
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditSociety.this,DisplaySociety.class);

                if (societyName.getText().toString().isEmpty()) {
                    societyName.setError("Field is Empty !");

                } else if (description.getText().toString().isEmpty()) {
                    description.setError("Field is Empty !");
                } else {
                    SocietyData societyData = new SocietyData(societyName.getText().toString(),
                            description.getText().toString(),
                            "https://firebasestorage.googleapis.com/v0/b/chatapp-55d92.appspot.com/o/upgradcom-logo.png?alt=media&token=bf1d9e05-2938-414c-8f22-a5ab7a202557"
                            , MessageAdapter.getMyUsername());

                    FirebaseDatabase.getInstance()
                            .getReference()
                            .child("Societies").child(societyName.getText().toString().toUpperCase()).setValue(societyData);

                    Toast.makeText(EditSociety.this, "saved", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                description.setVisibility(View.GONE);

              deleteIfAdmin();

            }
        });

    }
    void deleteIfAdmin(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("Societies");


        Query phoneQuery = ref.orderByChild("name").equalTo(societyName.getText().toString());

        phoneQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){

                     SocietyData societyDataTest = singleSnapshot.getValue(SocietyData.class);
                     if(societyDataTest.getAdmin().equals(MessageAdapter.getMyUsername())){
                         FirebaseDatabase.getInstance()
                                 .getReference()
                                 .child("Societies").child(societyName.getText().toString().toUpperCase()).removeValue();
                         Toast.makeText(EditSociety.this, "deleted", Toast.LENGTH_LONG).show();

                         Intent intent = new Intent(EditSociety.this,DisplaySociety.class);
                         startActivity(intent);
                    }else{
                         Toast.makeText(EditSociety.this, "Only admin can delete a society", Toast.LENGTH_LONG).show();
                     }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TEST", "onCancelled", databaseError.toException());
            }


        });



    }
}
