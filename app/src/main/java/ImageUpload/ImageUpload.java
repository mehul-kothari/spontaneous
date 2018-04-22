package ImageUpload;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.mehulkothari.spontaneous1.CreateEvent;

import com.example.mehulkothari.spontaneous1.CreateEvent;
import com.example.mehulkothari.spontaneous1.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by mehulkothari on 3/9/2017.
 */
public class ImageUpload extends AppCompatActivity {

    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    private byte[] data_2;
    String choice_1;
    ImageView imageView;

    public byte[] imageSelection(String choice) {
        choice_1=choice;
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        System.out.println("here inside image upload " + galleryIntent);
// Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
        return data_2;
    }

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
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

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
                if(choice_1.equals(CreateEvent.CREATE_EVENT)) {
                 //    imageView = (ImageView) findViewById(R.id.create_photo);
                }
                else{
                    imageView = (ImageView) findViewById(R.id.profile_photo);
                }
                imageView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                imageView.setDrawingCacheEnabled(true);
                imageView.buildDrawingCache();
                Bitmap bitmap = imageView.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                data_2 = baos.toByteArray();
                sendToActivity(data_2);

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void sendToActivity(byte[] data_2) {


    }
}