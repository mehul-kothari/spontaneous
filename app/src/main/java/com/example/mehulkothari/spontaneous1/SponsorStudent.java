package com.example.mehulkothari.spontaneous1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by mehulkothari on 3/25/2017.
 */

//class to select whether student or sponsor
public class SponsorStudent extends AppCompatActivity {
    Button student;
    Button sponsor;
    public static String which_user;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth auth;
    private static final String TAG ="GoogleActivity";
    public static final String PREFS_NAME = "MyPrefsFile";
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sponsor_student_layout);
        student=(Button)findViewById(R.id.btn_student);
        sponsor=(Button)findViewById(R.id.btn_sponsor);
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("which_user", "student");
                editor.commit();
                Intent intent = new Intent(SponsorStudent.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        sponsor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("which_user", "sponsor");
                editor.commit();
                Intent intent = new Intent(SponsorStudent.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(SponsorStudent.this, Main2Activity.class);
                    startActivity(intent);
                    finish();
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }
    public void onStart() {
        super.onStart();
        System.out.println("hello everyone");
        FirebaseUser user_1 = FirebaseAuth.getInstance().getCurrentUser();
        if(user_1!=null)
        {
            Intent intent = new Intent(SponsorStudent.this, Main2Activity.class);
            startActivity(intent);
            finish();
        }
    }
}
