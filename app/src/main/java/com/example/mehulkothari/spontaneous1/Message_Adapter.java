package com.example.mehulkothari.spontaneous1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import All_Users.message_users;


import All_Users.Users;

/**
 * Created by mehulkothari on 4/2/2017.
 */


public class Message_Adapter  extends RecyclerView.Adapter<Message_Adapter.ViewHolder> {

    Context mContext;
    message_users mu;
    ArrayList<message_users> array_mu;
    public Message_Adapter(Context context, ArrayList<message_users> array_mu) {
        //this. sponosrlist = sponosrlist;
        mContext = context;
        this.array_mu=array_mu;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView message_text;
       // public ImageView pro_pic;
        //public Button message_button;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            message_text = (TextView) itemView.findViewById(R.id.message_text);
           // pro_pic = (ImageView) itemView.findViewById(R.id.pro_pic);
            //message_button=(Button)itemView.findViewById(R.id.message_button);
        }
    }

    @Override
    public Message_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.messages_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;

    }



    @Override
    public void onBindViewHolder(Message_Adapter.ViewHolder holder, int position) {


        message_users user=array_mu.get(position);
        FirebaseUser user_1 = FirebaseAuth.getInstance().getCurrentUser();
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        // Set item views based on your views and data model
        TextView textView = holder.message_text;
        textView.setText(user.getText());
        textView.setLayoutParams(params1);

        if(user.getUser().equals( user_1.getUid()))
        {
            textView.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        }
        else{
            //
            // textView.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        }



    }

    @Override
    public int getItemCount() {
        return array_mu.size();
        //return sponosrlist.size();
    }
}






