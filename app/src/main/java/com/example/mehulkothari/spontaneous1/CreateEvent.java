package com.example.mehulkothari.spontaneous1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import CreateEventPack.CreateEventObject;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by mehulkothari on 3/9/2017.
 */
public class CreateEvent extends DialogFragment {
    private DatePicker datePicker;
    Button submit_create;
    private Calendar calendar;
    TextView dateView;
    private int year, month, day;
    TextView name_of_event;
    TextView college_name;
    TextView about_the_event;
    TextView address;
    Spinner expected_footfall;
    Spinner type_of_event;
    Button date_event;
    byte[] data_2;
    public static final String CREATE_EVENT="create_event";
    LinearLayout linearLayout;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    int i = 0;
    ImageView i3;
    ImageView imageView_1;
    ImageView create_event;
    View view;
    int count=1;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
Context context;
    ArrayList<Bitmap> event_photos= new ArrayList<>();
    ArrayList<byte[]> event_photos_1= new ArrayList<>();
    ArrayList<ImageView> image_view= new ArrayList<>();
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200,200);
    private FirebaseStorage storage;
    int likes=0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.activity_create_event,
                container, false);
        date_event=(Button) view.findViewById(R.id.set_date);

        return inflater.inflate(R.layout.activity_create_event, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Menu 1");

        //linearLayout=(LinearLayout) view.findViewById(R.id.linear_layout);
        name_of_event=(TextView) view.findViewById(R.id.name);
        college_name=(TextView) view.findViewById(R.id.college_name);
        address=(TextView) view.findViewById(R.id.address);
        about_the_event=(TextView) view.findViewById(R.id.event_description);
        expected_footfall=(Spinner) view.findViewById(R.id.expected_footfall);
        type_of_event=(Spinner) view.findViewById(R.id.event_type);
       // create_event=(ImageView)view.findViewById(R.id.create_event_1);
        date_event=(Button)view.findViewById(R.id.set_date);
        linearLayout=(LinearLayout) view.findViewById(R.id.linear_layout_2);
       // date_event=(Button) view.findViewById(R.id.set_date);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        dateView=(TextView) view.findViewById(R.id.date_view);
        submit_create=(Button) view.findViewById(R.id.create_submit);
        context=getActivity();

        generate();


        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        submit_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

        date_event.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {

                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");


            }
        });
        /*create_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

            }
        });*/

        //for (int i = 0; i < 10; i++) {
        /*imageView_1= new ImageView(getActivity());
        //int i = 0;
        imageView_1.setId(i);
        imageView_1.setPadding(40, 2, 2, 2);
        linearLayout.addView(imageView_1);*/

        //linearLayout=(LinearLayout) view.findViewById(R.id.linear_layout);


    }
    private void generate() {
        Log.d("Mehul", "entered");
        i3=new ImageView(getActivity());
        Log.d("Mehul", String.valueOf(i3));
        i3.setLayoutParams(params);
        i3.setImageResource(R.mipmap.ic_launcher);
        i3.setTag("ic");
        linearLayout.addView(i3);
        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Mehul","entered_click");
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

// Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);


            }
        });


    }




    public void validate()
    {
        String s1,s2,s3,s4,s5,s6;
        s1=name_of_event.getText().toString();
        s2=college_name.getText().toString();

        s3=address.getText().toString();
        s4=about_the_event.getText().toString();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); // Make sure user insert date into edittext in this format.

        Date dateObject = null;

        try{
            String dob_var=(dateView.getText().toString());

            dateObject = formatter.parse(dob_var);

        }

        catch (java.text.ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.i("E11111111111", e.toString());
        }
        s5=type_of_event.getSelectedItem().toString();
        s6=expected_footfall.getSelectedItem().toString();


        if(s1==null || s2==null ){
            Toast.makeText(getActivity(), "enter all necessary fields",
                    Toast.LENGTH_LONG).show();
            return;
        }
        else {

            CreateEventObject createEvent = new CreateEventObject(s1, s2, s3, s4, s5,s6,dateObject,likes);
            FirebaseUser user_1 = FirebaseAuth.getInstance().getCurrentUser();

            mFirebaseInstance = FirebaseDatabase.getInstance();

            // get reference to 'users' node
            mFirebaseDatabase = mFirebaseInstance.getReference("CREATE_EVENT");
            DatabaseReference key0= mFirebaseDatabase.child(user_1.getUid());
            DatabaseReference key1= key0.push();
            key1.setValue(createEvent);
            storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            StorageReference imagesRef = storageRef.child("event_images");

            Iterator<byte[]> iterator = event_photos_1.iterator();
            int no=0;
            while (iterator.hasNext()) {


                StorageReference imagesRef_2 = imagesRef.child(user_1.getUid()).child(key1.getKey()).child("image"+no);



                UploadTask uploadTask = imagesRef_2.putBytes(iterator.next());
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
                no++;
            }
            new AlertDialog.Builder(context)
                    .setTitle("Create Event")
                    .setMessage("do you want to create an event")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Fragment fragment = new MyEvents();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.content_frame, fragment);

                            fragmentTransaction.commit();

                            // continue with delete
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

    }






    /*@Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }*/



            /*});
            imageView.setImageBitmap(BitmapFactory.decodeResource(
                    getResources(), R.mipmap.ic_launcher));

            linearLayout.addView(imageView);

        //}
    }*/

/*    public void imageSelection_2(View view){


        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
             android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
// Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
        //ImageView image = new ImageView(CreateEvent.this);
        //image.setBackgroundResource(R.mipmap.ic_launcher);
        //linearLayout.addView(image);
        //ImageUpload imageUpload=new ImageUpload();
        //data_2 = imageUpload.imageSelection(CREATE_EVENT);
        ImageView image = new ImageView(CreateEvent.this);
        image.setBackgroundResource(R.mipmap.ic_launcher);
        linearLayout.addView(image);


    */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("hello");
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode ==getActivity().RESULT_OK && null != data) {
                // Get the Image from data
                System.out.println("hello");
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = context.getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();
                System.out.println("hello");
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                System.out.println("hello");
                cursor.close();
                //ImageView i4=new ImageView(getActivity());


                //imageView.setMaxHeight(20);
                //imageView.setMaxWidth(20);
                // Set the Image in ImageView after decoding the String
                Bitmap bm=BitmapFactory.decodeFile(imgDecodableString);
                i3.setImageBitmap(bm);
                event_photos.add(bm);
                Log.d("mehul",String.valueOf(event_photos));
                i3.setId(0);
                count++;
                //imageView.setMaxHeight(20);
                //imageView.setMaxWidth(20);
                //linearLayout.addView(imageView);

                i3.setDrawingCacheEnabled(true);
                i3.buildDrawingCache();
                Bitmap bitmap = i3.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                data_2 = baos.toByteArray();
                event_photos_1.add(data_2);


                System.out.println("hello");
                image_view.add(i3);
                generate_1();

            } else {
                Toast.makeText(getActivity(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }
    private void generate_1() {
        Log.d("Mehul", "entered");
        i3=new ImageView(getActivity());
        Log.d("Mehul", String.valueOf(i3));
        i3.setLayoutParams(params);
        i3.setImageResource(R.mipmap.ic_launcher);
        i3.setTag("ic");

        linearLayout.addView(i3);
        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  s= String.valueOf(i3);


                    //if(i3.get)
                    Log.d("Mehul", "entered_click");
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

// Start the Intent
                    startActivityForResult(galleryIntent, RESULT_LOAD_IMG);



            }
        });


    }
}
