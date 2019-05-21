package com.upgrad.ChatApp;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<FriendlyMessage> {
    static String  MyUsername="aditya";

    public static String getMyUsername() {
        return MyUsername;
    }

    public static void setMyUsername(String myUsername) {
        MyUsername = myUsername;
    }

    public MessageAdapter(Context context, int resource, List<FriendlyMessage> objects) {
        super(context, resource, objects);

    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message, parent, false);
        }

        ImageView photoImageView = (ImageView) convertView.findViewById(R.id.photoImageView);
        TextView messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
        TextView authorTextView = (TextView) convertView.findViewById(R.id.nameTextView);

        FriendlyMessage message = getItem(position);


        boolean isPhoto = message.getPhotoUrl() != null;
        if (isPhoto) {
            if(message.getName().equals(MyUsername)){
                photoImageView.setBackgroundResource(R.drawable.rounded_corner);
            }else{
                photoImageView.setBackgroundResource(R.drawable.rounded_corner2);

            }
            messageTextView.setVisibility(View.GONE);
            photoImageView.setVisibility(View.VISIBLE);
            Glide.with(photoImageView.getContext())
                    .load(message.getPhotoUrl())
                    .into(photoImageView);
        } else {
            if(message.getName().equals(MyUsername)){
                messageTextView.setBackgroundResource(R.drawable.rounded_corner);
                messageTextView.setGravity(Gravity.LEFT);
            }else{
                messageTextView.setBackgroundResource(R.drawable.rounded_corner2);
                messageTextView.setGravity(Gravity.RIGHT);
            }
            messageTextView.setVisibility(View.VISIBLE);
            photoImageView.setVisibility(View.GONE);
            messageTextView.setText(message.getText());
        }
        authorTextView.setText(message.getName());

        return convertView;
    }
}
