package com.example.mehulkothari.spontaneous1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import All_Users.Users;
import CreateEventPack.CreateEventObject;
import android.app.Dialog;

/**
 * Created by mehulkothari on 3/25/2017.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private LinkedHashMap<String, CreateEventObject> dataSet;
    Context context;
    ArrayList<String> user_key;
    ArrayList<String> user_key_1;
    int flag;
    ImageView imageView;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public CustomAdapter(LinkedHashMap<String, CreateEventObject> data, Context context, ArrayList<String> user_key, ArrayList<String> user_key_1, int flag) {
        Log.d("entered constructor", String.valueOf(data));
        dataSet = data;
        this.context = context;
        this.user_key = user_key;
        this.user_key_1 = user_key_1;
        this.flag = flag;


    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView card_college;
        TextView card_event;
        ImageView first_image;
        ImageView card_like;
        ImageView card_fav;
        Context context;
        ArrayList<String> user_key, user_key_1;

        public MyViewHolder(View itemView, Context context, ArrayList<String> user_key, ArrayList<String> user_key_1) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.user_key = user_key;
            this.user_key_1 = user_key_1;
            this.context = context;
            this.card_college = (TextView) itemView.findViewById(R.id.card_college);
            this.card_event = (TextView) itemView.findViewById(R.id.card_event);
            this.first_image = (ImageView) itemView.findViewById(R.id.first_image);
            this.card_like = (ImageView) itemView.findViewById(R.id.card_like);
            this.card_fav = (ImageView) itemView.findViewById(R.id.card_fav);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Dialog openDialog = new Dialog(context);
            openDialog.setContentView(R.layout.event_dialog);
            openDialog.setTitle("Custom Dialog Box");
            TextView dialogTextContent = (TextView) openDialog.findViewById(R.id.t1);
            dialogTextContent.setText(user_key_1.get(position));
            openDialog.show();


        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d("entered ooncre", "jkn");

        View view = LayoutInflater.from(context)
                .inflate(R.layout.card_layout, parent, false);

        //view.setOnClickListener(MainActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view, context, user_key, user_key_1);
        return myViewHolder;
        //return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final boolean[] check = {false};
        final boolean[] check1 = {false};
        Log.d("onbindView", String.valueOf(position));
        TextView textViewName = holder.card_college;
        TextView textViewVersion = holder.card_event;
        imageView = holder.first_image;
        ImageView imageView_1 = holder.card_fav;
        final ImageView imageView_2 = holder.card_like;
        DatabaseReference db3 = FirebaseDatabase.getInstance().getReference("LIKES").child(user.getUid());
        if(flag==0) {
            insertImage(position);

            db3.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (ds.getKey().equals(user_key.get(position))) {
                            imageView_2.setImageResource(R.drawable.redheart);
                            check[0] = true;

//                        flag[0] =0;

                        }
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            imageView_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (check[0]) {
                        imageView_2.setImageResource(R.drawable.blackheart);
                        check1[0] = true;
                    } else {
                        imageView_2.setImageResource(R.drawable.redheart);
                    }
                    //System.out.println(user_key.get(position));
                    System.out.println(user_key_1.get(position));
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("CREATE_EVENT").child(user_key_1.get(position)).child(user_key.get(position));
                    DatabaseReference likes = db.child("likes");
                    System.out.println(likes);

                    db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Toast.makeText(context, "enetered ondatachange", Toast.LENGTH_SHORT).show();


                            DatabaseReference db2 = FirebaseDatabase.getInstance().getReference("LIKES").child(user.getUid());
                            CreateEventObject user_like = dataSnapshot.getValue(CreateEventObject.class);
                            int likes = user_like.getLikes();
                            if (check1[0]) {
                                likes = likes - 1;
                                dataSnapshot.getRef().child("likes").setValue(likes);
                                db2.child(user_key.get(position)).removeValue();
                            } else {
                                likes = likes + 1;
                                db2.child(user_key.get(position)).setValue("yes");
                                dataSnapshot.getRef().child("likes").setValue(likes);
                            }
                            //dataSnapshot.getRef().child("likes").setValue(likes);
                            //DatabaseReference db2 = FirebaseDatabase.getInstance().getReference("LIKES").child(user.getUid());


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            });
        }

        textViewName.setText(dataSet.get((dataSet.keySet().toArray())[position]).getCollege_name());
        textViewVersion.setText(dataSet.get((dataSet.keySet().toArray())[position]).getName());


    }


    private void insertImage(int position) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference imagesRef = storageRef.child("event_images").child(user_key_1.get(position)).child(user_key.get(position));
        StorageReference imgr;
        if (imagesRef != null) {

            final Bitmap[] bm = new Bitmap[1];
            final Bitmap[] bm1 = new Bitmap[1];
            bm[0] = null;
            final int[] flag = {0};
            for (int i = 0; i < 100; i++) {
                //imgr = imagesRef.child("image" + i);
                //StorageReference imagesRef_1=imagesRef.child((String) (dataSet.keySet().toArray())[position]);
                //Toast.makeText(context, (String) (dataSet.keySet().toArray())[position], Toast.LENGTH_LONG).show();


                imagesRef.child("image0").getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        // Toast.makeText(context, "successs", Toast.LENGTH_LONG).show();
                        bm[0] = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        //Toast.makeText(context, bm[0].toString(), Toast.LENGTH_LONG).show();
                        Log.d("mehul", bm[0].toString());
                        imageView.setImageBitmap(bm[0]);
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


                //Toast.makeText(context, user.getEmail().toString(), Toast.LENGTH_LONG).show();

            }
        }
            else
            {
                imageView.setImageResource(R.mipmap.ic_launcher);
            }

            //Toast.makeText(context, bm[0].toString(), Toast.LENGTH_LONG).show();

        /*Glide.with(this /* context */ /*)
                .using(new FirebaseImageLoader())
                .load(imagesRef_1)
                .into(profile_pic);*/


    }


        @Override
        public int getItemCount () {
            Log.d("size", String.valueOf(dataSet.size()));
            return dataSet.size();
        }

    }


