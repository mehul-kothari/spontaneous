package com.example.mehulkothari.spontaneous1;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import All_Users.Users;
import All_Users.message_users;

/**
 * Created by mehulkothari on 3/29/2017.
 */
public class SponsorAdapter extends RecyclerView.Adapter<SponsorAdapter.ViewHolder> {

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;



    private ArrayList sponsorlist;
    private ArrayList sponsorlist_1;
    private ArrayList<String> user_key;
    String flag;
    Set sponsors;
    // Store the context for easy access
    private Context mContext;



    // Pass in the contact array into the constructor


    public SponsorAdapter(Context context, ArrayList sponsorlist2, ArrayList<String> user_key,String flag) {
        this. sponsorlist = sponsorlist2;
        this. user_key = user_key;
        mContext = context;
        this.flag=flag;
    }



    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView nameTextView;
        public ImageView pro_pic;
        public Button message_button;
        ArrayList sponsorlist2,user_key;
        String flag;
        Context context;

        public ViewHolder(View itemView,Context context,ArrayList sponsorlist2,ArrayList user_key,String flag) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            itemView.setOnClickListener(this);
            this.context=context;
            this.sponsorlist2=sponsorlist2;
            this.user_key=user_key;
            this.flag=flag;

            nameTextView = (TextView) itemView.findViewById(R.id.sponsor_name);
            pro_pic = (ImageView) itemView.findViewById(R.id.pro_pic);
            message_button=(Button)itemView.findViewById(R.id.message_button);
        }

        @Override
        public void onClick(View v) {
            if(flag.equals("msg")) {
                int position = getAdapterPosition();
                String key1 = (String) user_key.get(position);
                Intent in=new Intent(context,Personal_Message.class);
                in.putExtra("rec",key1);
                context.startActivity(in);
            }

        }
    }


    @Override
    public SponsorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.row_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView,context,sponsorlist,user_key,flag);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(SponsorAdapter.ViewHolder holder, final int position) {
        TextView textView = holder.nameTextView;
        final ImageView imageView=holder.pro_pic;
        Button message_button1=holder.message_button;
        final String key1 = user_key.get(position);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imageref=storageRef.child("profile_images").child(key1);
        if(imageref!=null)
        {
            imageref.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    // Toast.makeText(context, "successs", Toast.LENGTH_LONG).show();
                    imageView.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                    //Toast.makeText(context, bm[0].toString(), Toast.LENGTH_LONG).show();
                    //flag[0] = 1;
                    //bm1[0] =bm[0];
                    //profile_pic.setImageBitmap(bm);
                    // Use the bytes to display the image
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                    //Toast.makeText(context, "sorry no image found", Toast.LENGTH_LONG).show();
                    // Handle any errors
                }
            });
        }
        if(flag.equals("spon")) {
            Users user = (Users) sponsorlist.get(position);
            textView.setText(user.getName());

            //Button message_button1=holder.message_button;
            message_button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFirebaseInstance = FirebaseDatabase.getInstance();

                    // get reference to 'users' node
                    mFirebaseDatabase = mFirebaseInstance.getReference("users");
                    final DatabaseReference key0 = mFirebaseDatabase.child("sponsor");


                    Intent in = new Intent(getContext(), Personal_Message.class);
                    in.putExtra("rec", key1);
                    mContext.startActivity(in);

                }
            });
        }
        else{
            Users user = (Users) sponsorlist.get(position);
            textView.setText(key1);
            message_button1.setEnabled(false);

        }


        // Set item views based on your views and data model
        //TextView textView = holder.nameTextView;
        //textView.setText(user.getName());


    }

    @Override
    public int getItemCount() {
        return sponsorlist.size();
    }
}
