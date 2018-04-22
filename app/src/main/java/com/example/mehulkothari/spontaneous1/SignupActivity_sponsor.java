package com.example.mehulkothari.spontaneous1;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import All_Users.Users;

public class SignupActivity_sponsor extends AppCompatActivity {
    private EditText inputEmail, inputPassword, confirmpassword, user_name, about_company,company_address,company_name;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    //private Spinner profession;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    private ImageView profile_photo;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseStorage storage;
    private byte[] data_2;
    public static final String SIGNUP="signup";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_signup_activity_sponsor);
        company_address=(EditText)findViewById(R.id.comapny_address);
        company_name=(EditText)findViewById(R.id.company_name);
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        confirmpassword = (EditText) findViewById(R.id.confirm_password);
        user_name = (EditText) findViewById(R.id.name);
        about_company = (EditText) findViewById(R.id.about_company);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        //profession=(Spinner) findViewById(R.id.profession);
        ImageView profile_photo = (ImageView) findViewById(R.id.profile_photo);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String confirm = confirmpassword.getText().toString().trim();
                final String name_1 = user_name.getText().toString().trim();
                final String text = company_name.getText().toString().trim();
                final String company_address_1 = company_address.getText().toString();
                final String about_company_1 = about_company.getText().toString();

                if (TextUtils.isEmpty(name_1)) {
                    Toast.makeText(getApplicationContext(), "Enter your full name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!(password.equals(confirm))) {
                    Toast.makeText(getApplicationContext(), "passwords dont match", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(SignupActivity_sponsor.this,"before auth", Toast.LENGTH_SHORT).show();

                //Insert data
                //Users user=new Users(name_1, email, text, description_1);

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity_sponsor.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignupActivity_sponsor.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                                mFirebaseInstance = FirebaseDatabase.getInstance();

                                // get reference to 'users' node
                                mFirebaseDatabase = mFirebaseInstance.getReference("users");
                                final DatabaseReference key0=mFirebaseDatabase.child("sponsor");
                                storage = FirebaseStorage.getInstance();
                                StorageReference storageRef = storage.getReference();
                                final StorageReference imagesRef = storageRef.child("profile_images");
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity_sponsor.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    System.out.println("inside_signin");
                                    FirebaseUser user_1 = FirebaseAuth.getInstance().getCurrentUser();
                                    System.out.println("user id" + user_1);
                                    Users user = new Users(name_1, email, about_company_1,text,company_address_1);

                                    key0.child(user_1.getUid()).setValue(user);
                                    System.out.println("user set");
                                    StorageReference imagesRef_2 = imagesRef.child(user_1.getUid());
                                    UploadTask uploadTask = imagesRef_2.putBytes(data_2);
                                    uploadTask.addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Handle unsuccessful uploads
                                        }
                                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                        }
                                    });
                                   startActivity(new Intent(SignupActivity_sponsor.this, Main2Activity.class));
                                    finish();
                                }
                            }
                        });
                //insert data
                //Users user=new Users(name_1, email, text, description_1);


            }
        });
    }
    public void imageSelection(View view){
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
// Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
        //ImageUpload imageUpload=new ImageUpload();
        //data_2=imageUpload.imageSelection(SIGNUP);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("hello");
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data
                System.out.println("hello");
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();
                System.out.println("hello");
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                System.out.println("hello");
                cursor.close();
                ImageView imageView = (ImageView) findViewById(R.id.profile_photo);

                // Set the Image in ImageView after decoding the String
                imageView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                imageView.setDrawingCacheEnabled(true);
                imageView.buildDrawingCache();
                Bitmap bitmap = imageView.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                data_2 = baos.toByteArray();


                System.out.println("hello");

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }
}
