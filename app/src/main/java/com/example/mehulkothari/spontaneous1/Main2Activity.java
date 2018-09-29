package com.example.mehulkothari.spontaneous1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import Sponsor.*;
import android.app.AlarmManager;
import android.app.PendingIntent;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageView profile_pic;
    TextView email;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private PendingIntent pendingIntent;
    private AlarmManager manager;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // Intent alarmIntent = new Intent(this, AlarmReceiver.class);
       // pendingIntent = PendingIntent.getBroadcast(this, 234324243, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //startAlarm();
        profile_pic = (ImageView) findViewById(R.id.profile_pic_1);
        email = (TextView) findViewById(R.id.email_1);
        auth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(Main2Activity.this, SponsorStudent.class));
                }
            }
        };






        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        SharedPreferences shared = this.getSharedPreferences(SponsorStudent.PREFS_NAME, Context.MODE_PRIVATE);
        String channel = shared.getString("which_user", null);
        System.out.println(channel);
        if(channel.equals("student"))
        {
            navigationView.getMenu().setGroupVisible(R.id.group_sponsor, false);
        }
        else{
            navigationView.getMenu().setGroupVisible(R.id.group_student, false);
        }

        navigationView.setNavigationItemSelectedListener(this);
        insertImage();
        startService(new Intent(getBaseContext(),MyServices.class));
        /*handler=new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                Log.d("mehul","going on");
                handler.postDelayed(this,1000);

            }
        };
        handler.postDelayed(runnable,1000);*/
    }
//service notification
    private void startAlarm() {
        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        int interval = 100;

        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }
//saving profile image
    private void insertImage() {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("profile_images");
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        imagesRef.child(user.getUid()).getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {

                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Toast.makeText(getApplicationContext(), bm.toString(), Toast.LENGTH_LONG).show();
                Log.d("mehul", bm.toString());
                //profile_pic.setImageBitmap(bm);
                // Use the bytes to display the image
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

                Toast.makeText(getApplicationContext(), "sorry no image found", Toast.LENGTH_LONG).show();
                // Handle any errors
            }
        });
        Toast.makeText(getApplicationContext(), user.getEmail().toString(), Toast.LENGTH_LONG).show();


        /*Glide.with(this /* context */ /*)
                .using(new FirebaseImageLoader())
                .load(imagesRef_1)
                .into(profile_pic);*/
            }

            @Override
            public void onBackPressed() {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    super.onBackPressed();
                }
            }

            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.main2, menu);
                return true;
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                // Handle action bar item clicks here. The action bar will
                // automatically handle clicks on the Home/Up button, so long
                // as you specify a parent activity in AndroidManifest.xml.
                int id = item.getItemId();

                //noinspection SimplifiableIfStatement
                if (id == R.id.action_settings) {
                    return true;
                }
                if (id == R.id.action_signout) {
                    stopService(new Intent(getBaseContext(),MyServices.class));

                    auth.signOut();


// this listener will be called when there is change in firebase user session

                }

                return super.onOptionsItemSelected(item);
            }

            @SuppressWarnings("StatementWithEmptyBody")
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                // Handle navigation view item clicks here.
                displaySelectedScreen(item.getItemId());
                return true;
            }


//navbar functionalities.
    private void displaySelectedScreen(int itemId) {

        Fragment fragment=null;

        if (itemId == R.id.home) {
            // Handle the camera action
        } else if (itemId == R.id.create) {
            fragment = new CreateEvent();

        }
        else if (itemId == R.id.my_events) {
            Toast.makeText(getApplicationContext(), "entered11", Toast.LENGTH_SHORT).show();
            fragment = new MyEvents();
        } else if (itemId == R.id.sponsors_list) {
            fragment=new SponsorsList();


        } else if (itemId == R.id.messages) {
            fragment = new Messages();
            /*Intent in=new Intent(Main2Activity.this,Personal_Message.class);
            in.putExtra("rec", "Eyd9wIUCrGMr6oxWGS3t7hiGiQ33");
            startActivity(in);
            finish();*/
        } else if (itemId == R.id.nav_send) {

        }
        else if (itemId == R.id.create_sponsor) {
            fragment = new MyEvents();
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
    @Override
    public void onStart() {
        super.onStart();
        System.out.println("hello everyone");
        auth.addAuthStateListener(mAuthStateListener);
    }
}
