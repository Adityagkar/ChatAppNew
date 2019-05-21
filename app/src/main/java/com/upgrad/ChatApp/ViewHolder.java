package com.upgrad.ChatApp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;



public class ViewHolder extends RecyclerView.ViewHolder {
    View mView;
    public LinearLayout root;

    public ViewHolder(@NonNull final View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setDetails(Context mContext, String nameFB, String detailsFB, String imageURL){
        TextView mNameTV = mView.findViewById(R.id.namefb);
        TextView mDetailsTV = mView.findViewById(R.id.complaintfb);
        ImageView mImage = mView.findViewById(R.id.img);
        root = mView.findViewById(R.id.list_root);

        mNameTV.setText(nameFB);
        mDetailsTV.setText(detailsFB);
        Glide.with(mContext).load(imageURL).into(mImage);
    }
}
