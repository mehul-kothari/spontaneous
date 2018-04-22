package com.example.mehulkothari.spontaneous1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.app.Notification;
import android.app.NotificationManager;


import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.ChildEventListener;

import java.util.ArrayList;

import All_Users.*;

public class Personal_Message extends AppCompatActivity {
    Button b1;
    EditText e1;
    LinearLayout l1;
    ChildEventListener ce;


    private DatabaseReference mFirebaseDatabase;
    private DatabaseReference mFirebaseDatabase1;
    private FirebaseDatabase mFirebaseInstance;
    LinearLayoutManager mLayout;
    private DatabaseReference mPostReference;
    private static RecyclerView.Adapter adapter;
    ArrayList<message_users> array_mu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal__message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        array_mu=new ArrayList<>();
        final RecyclerView rc2=(RecyclerView)findViewById(R.id.rc2);
        mLayout = new LinearLayoutManager(this);
        rc2.setLayoutManager(mLayout);
        rc2.setItemAnimator(new DefaultItemAnimator());
        Intent in = getIntent();
        final String key0=in.getStringExtra("rec");
        Toast.makeText(getApplicationContext(), key0, Toast.LENGTH_SHORT).show();
        adapter = new Message_Adapter(getApplicationContext(),array_mu);
        rc2.setAdapter(adapter);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mPostReference = mFirebaseInstance.getReference("messages")
                .child(user.getUid()).child(key0);
       ce=new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {




                    System.out.println(dataSnapshot.toString());

                    message_users user_info = dataSnapshot.getValue(message_users.class);
                    array_mu.add(user_info);
                    System.out.println(user_info);
                adapter.notifyItemInserted(array_mu.size() - 1);
                addNotification();
                    //adapter.notifyDataSetChanged();



            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        //mPostReference.addValueEventListener(postListener);
       // mPostReference.removeEventListener(postListener);
        //mPostReference.addChildEventListener(ce);
        mPostReference.addChildEventListener(ce);
        //mPostReference.addValueEventListener(postListener);


        // get reference to 'users' node

        //final DatabaseReference key1=mFirebaseDatabase.child("sponsor");
        b1=(Button)findViewById(R.id.send_msg);
        e1=(EditText)findViewById(R.id.enter_text);
        l1=(LinearLayout)findViewById(R.id.msg_list_2);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text=e1.getText().toString();
                message_users mu=new message_users(text,user.getUid());
                mFirebaseDatabase=mFirebaseInstance.getReference("messages").child(user.getUid()).child(key0).push();
                mFirebaseDatabase.setValue(mu);
                mFirebaseDatabase1=mFirebaseInstance.getReference("messages").child(key0).child(user.getUid()).push();
                mFirebaseDatabase1.setValue(mu);
                //mPostReference.removeEventListener(postListener);
               // mPostReference.addChildEventListener(ce);
                //adapter.notifyDataSetChanged();

                //adapter = new Message_Adapter(getApplicationContext(),mu);
                //rc2.setAdapter(adapter);


            }
        });
    }

    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Notifications Example")
                        .setContentText("This is a test notification");

        Intent notificationIntent = new Intent(this, Main2Activity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        builder.build().flags |= Notification.FLAG_AUTO_CANCEL;
        manager.notify(0, builder.build());
    }

}
