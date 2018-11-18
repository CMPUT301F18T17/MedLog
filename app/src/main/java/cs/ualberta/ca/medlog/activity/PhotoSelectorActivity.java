package cs.ualberta.ca.medlog.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import cs.ualberta.ca.medlog.R;

import static android.app.Activity.RESULT_OK;

/**
 * <p>
 *     Description: <br>
 *         The Activity for a User photo addition screen, this presents the gui for a User
 *         to view already added photos and to proceed to add further ones either by taking new
 *         ones or by choosing existing ones from their phone photo library.
 * </p>
 * <p>
 *     Issues: <br>
 *         Add code to return an ArrayList of the photos that have been added.
 *         Fix issues in grabbing added photos and displaying them.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.1
 * @see PhotoBitmapAdapter
 */
public class PhotoSelectorActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_LOAD_IMAGE = 2;

    private ArrayList<Bitmap> photos = new ArrayList<Bitmap>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_selector);

        Bitmap guidePhoto = null;

        Intent passedIntent = getIntent();
        guidePhoto = passedIntent.getParcelableExtra("argGuidePhoto");
        //TODO Add code to load the passed existing images.

        if (guidePhoto != null) {
            ImageView guideImageView = findViewById(R.id.activityPhotoSelector_GuideImage);
            guideImageView.setImageBitmap(guidePhoto);
        }

        final GridView photoGrid = findViewById(R.id.activityPhotoSelector_PhotoGridView);
        ArrayAdapter<Bitmap> gridAdapter = new PhotoBitmapAdapter(this,photos);
        gridAdapter.setNotifyOnChange(true);
        photoGrid.setAdapter(gridAdapter);

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
            Toast.makeText(this,"Photo Added",Toast.LENGTH_SHORT);
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            photos.add(imageBitmap);

        }
        else if (requestCode == REQUEST_LOAD_IMAGE && resultCode == RESULT_OK) {
            Toast.makeText(this,"Photo Added",Toast.LENGTH_SHORT);
            Uri imageUri = data.getData();
            Bitmap imageBitmap = null;
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            }
            catch (IOException e) {
                Toast.makeText(this,"Couldn't Load Image",Toast.LENGTH_SHORT);
            }
            if (photos != null) {
                photos.add(imageBitmap);
            }
        }
        else if (resultCode != RESULT_OK){
            Toast.makeText(this,"Cancelled",Toast.LENGTH_SHORT);
        }
    }
}