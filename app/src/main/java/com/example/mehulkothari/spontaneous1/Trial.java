package com.example.mehulkothari.spontaneous1;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mehulkothari on 3/11/2017.
 */
public class Trial extends AppCompatActivity {
    int RESULT_LOAD_IMG=1;
    String imgDecodableString;
    LinearLayout l1;
    ImageView i1;
    ImageView i2;
    ImageView i3;
    ArrayList<Bitmap> event_photos= new ArrayList<>();
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100,100);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trial);
        l1=(LinearLayout) findViewById(R.id.linear_layout_1);
        generate();



    }

    private void generate() {
        Log.d("Mehul","entered");
        i3=new ImageView(this);
        i3.setLayoutParams(params);
        i3.setImageResource(R.mipmap.ic_launcher);
        l1.addView(i3);
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
                //ImageView i1=(ImageView) data.getParcelableExtra("myobj");
                System.out.println("hello");
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                System.out.println("hello");
                cursor.close();
                //ImageView imageView=(ImageView) findViewById(R.id.create_event_1);
                Bitmap bm=BitmapFactory.decodeFile(imgDecodableString);
                i3.setImageBitmap(bm);
                //i3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                event_photos.add(bm);
                //addNew();
                i3.setDrawingCacheEnabled(true);
                i3.buildDrawingCache();
                Bitmap bitmap = i3.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                generate();
               // data_2 = baos.toByteArray();


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

    private void addNew() {
     ImageView i1=new ImageView(this);
        i1.setLayoutParams(params);
        i1.setImageResource(R.mipmap.ic_launcher);
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


// Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            }
        });
        l1.addView(i1);






    }
}
