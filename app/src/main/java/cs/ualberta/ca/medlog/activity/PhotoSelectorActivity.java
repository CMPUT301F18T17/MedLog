package cs.ualberta.ca.medlog.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.entity.Photo;

/**
 * <p>
 *     Description: <br>
 *         The Activity for a User photo addition screen, this presents the gui for a User
 *         to view already added photos and to proceed to add further ones either by taking new
 *         ones or by choosing existing ones from their phone photo library.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.6
 * @see PhotoAdapter
 */
public class PhotoSelectorActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_LOAD_IMAGE = 2;

    private ArrayList<Photo> photos;
    private PhotoAdapter photoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_selector);

        Intent intent = getIntent();
        Photo guidePhoto = intent.getParcelableExtra("GUIDE_PHOTO");
        photos = intent.getParcelableArrayListExtra("PHOTOS");
        if (photos == null) {
            photos = new ArrayList<>();
        }

        if (guidePhoto != null) {
            ImageView guideImageView = findViewById(R.id.activityPhotoSelector_GuideImage);
            guideImageView.setImageBitmap(guidePhoto.getPhotoBitmap());
        }

        final GridView photoGrid = findViewById(R.id.activityPhotoSelector_PhotoGridView);
        photoAdapter = new PhotoAdapter(this,photos);
        photoGrid.setAdapter(photoAdapter);

        Button takeNewButton = findViewById(R.id.activityPhotoSelector_TakeNewButton);
        Button addExistingButton = findViewById(R.id.activityPhotoSelector_AddExistingButton);
        takeNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        addExistingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchChoosePictureIntent();
            }
        });


        Button saveButton = findViewById(R.id.activityPhotoSelector_SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (photos.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No photos added", Toast.LENGTH_SHORT).show();
                    return;
                }
                intent.putParcelableArrayListExtra("PHOTOS",photos);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void dispatchChoosePictureIntent() {
        Intent choosePictureIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (choosePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(choosePictureIntent, REQUEST_LOAD_IMAGE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Toast.makeText(this,"Photo Added",Toast.LENGTH_SHORT).show();
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            photos.add(new Photo(imageBitmap));
            photoAdapter.notifyDataSetChanged();
        }
        else if (requestCode == REQUEST_LOAD_IMAGE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            Bitmap imageBitmap;
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                Toast.makeText(this,"Photo Added",Toast.LENGTH_SHORT).show();
            }
            catch (IOException e) {
                Toast.makeText(this,"Couldn't Load Image",Toast.LENGTH_SHORT).show();
                return;
            }
            photos.add(new Photo(imageBitmap));
            photoAdapter.notifyDataSetChanged();
        }
        else if (resultCode != RESULT_OK){
            Toast.makeText(this,"Cancelled",Toast.LENGTH_SHORT).show();
        }
    }
}