package cs.ualberta.ca.medlog.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;

import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.entity.Photo;
import cs.ualberta.ca.medlog.helper.Database;
import cs.ualberta.ca.medlog.singleton.AppStatus;

/**
 * <p>
 *     Description: <br>
 *         The Activity for a User photo addition screen, this presents the gui for a User
 *         to view already added photos and to proceed to add further ones either by taking new
 *         ones or by choosing existing ones from their phone photo library.
 * </p>
 * <p>
 *     Issues: <br>
 *         Fix potential failure with choose photo.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.8
 * @see PhotoAdapter
 */
public class PhotoSelectorActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_LOAD_IMAGE = 2;

    private ArrayList<Photo> photos;
    private PhotoAdapter photoAdapter;

    private String photoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_selector);

        Intent intent = getIntent();
        Photo guidePhoto = (Photo)intent.getSerializableExtra("GUIDE_PHOTO");
        photos = (ArrayList<Photo>)intent.getSerializableExtra("PHOTOS");
        if (photos == null) {
            photos = new ArrayList<>();
        }

        if (guidePhoto != null) {
            ImageView guideImageView = findViewById(R.id.activityPhotoSelector_GuideImage);
            guideImageView.setImageBitmap(guidePhoto.getBitmap(guideImageView.getWidth(),guideImageView.getHeight()));
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

        Database db = new Database(this);

        Button saveButton = findViewById(R.id.activityPhotoSelector_SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (photos.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No photos added", Toast.LENGTH_SHORT).show();
                    return;
                }
                for(Photo photo:photos) {
                    try {
                        db.savePhoto(photo);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed to save photos", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                intent.putExtra("PHOTOS",photos);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;

            String username = AppStatus.getInstance().getCurrentUser().getUsername();
            String timeStamp = Long.toHexString(new Date().getTime());
            String imageFileName = "JPEG_" + "_" + username + "_" + timeStamp + "_";
            try {
                photoFile = File.createTempFile(imageFileName,".jpg",getFilesDir());
                photoPath = photoFile.getPath();
            } catch (IOException e) {
                Toast.makeText(this, "Failed to find photo space", Toast.LENGTH_SHORT).show();
                return;
            }
            if (photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(this, "cs.ualberta.ca.medlog.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
            else {
                Toast.makeText(this, "Failed to find photo space", Toast.LENGTH_SHORT).show();
                return;
            }
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
            photos.add(new Photo(photoPath));
            photoAdapter.notifyDataSetChanged();
        }
        else if (requestCode == REQUEST_LOAD_IMAGE && resultCode == RESULT_OK) {
            String sourceFilename = getRealPathFromURI(data.getData());
            Toast.makeText(this,sourceFilename,Toast.LENGTH_SHORT).show();

            File photoFile;

            String username = AppStatus.getInstance().getCurrentUser().getUsername();
            String timeStamp = Long.toHexString(new Date().getTime());
            String imageFileName = "JPEG_" + "_" + username + "_" + timeStamp + "_";
            try {
                photoFile = File.createTempFile(imageFileName, ".jpg", getFilesDir());

                FileInputStream inStream = new FileInputStream(sourceFilename);
                FileOutputStream outStream = new FileOutputStream(photoFile);
                FileChannel inChannel = inStream.getChannel();
                FileChannel outChannel = outStream.getChannel();
                inChannel.transferTo(0, inChannel.size(), outChannel);
                inStream.close();
                outStream.close();

                photoPath = photoFile.getPath();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to find photo space", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this,"Photo Added",Toast.LENGTH_SHORT).show();
            photos.add(new Photo(photoPath));
            photoAdapter.notifyDataSetChanged();
        }
        else if (resultCode != RESULT_OK){
            Toast.makeText(this,"Cancelled",Toast.LENGTH_SHORT).show();
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
}