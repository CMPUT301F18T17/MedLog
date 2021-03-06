package cs.ualberta.ca.medlog.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
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
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.0
 * @see PhotoAdapter
 * @see TextEditorFragment
 */
public class PhotoSelectorActivity extends AppCompatActivity implements TextEditorFragment.OnTextSetListener {
    static final int REQUEST_IMAGE_CAPTURE = 1;

    static final int MAX_PHOTOS = 10;

    private ArrayList<Photo> photos;
    private ArrayList<Photo> toDelete = new ArrayList<>();
    private PhotoAdapter photoAdapter;

    private String photoPath;
    private int selectedIndex;

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
            guideImageView.setImageBitmap(guidePhoto.getBitmap());
        }

        final GridView photoGrid = findViewById(R.id.activityPhotoSelector_PhotoGridView);
        photoGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopupMenu menu = new PopupMenu(getApplicationContext(),view);
                menu.inflate(R.menu.menu_photo_editing);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menuPhotoEditing_EditLabel) {

                            selectedIndex = position;

                            DialogFragment newFragment = new TextEditorFragment();
                            Bundle editorData = new Bundle();
                            editorData.putInt("argEditorId",0);
                            editorData.putString("argHint",getString(R.string.fragmentTextEditor_LabelHint));
                            editorData.putString("argInitialText",photos.get(position).getLabel());
                            editorData.putInt("argMaxLength",20);
                            newFragment.setArguments(editorData);
                            newFragment.show(getSupportFragmentManager(),"labelEditor");
                        }
                        else {
                            toDelete.add(photos.get(position));
                            photos.remove(position);
                            photoAdapter.notifyDataSetChanged();
                        }
                        return false;
                    }
                });
                menu.show();
            }
        });
        photoAdapter = new PhotoAdapter(this,photos);
        photoGrid.setAdapter(photoAdapter);

        Button takeNewButton = findViewById(R.id.activityPhotoSelector_TakeNewButton);
        takeNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photos.size() < MAX_PHOTOS) {
                    dispatchTakePictureIntent();
                }
                else {
                    Toast.makeText(getApplicationContext(),R.string.activityPhotoSelector_MaxPhotos,Toast.LENGTH_SHORT).show();
                }
            }
        });

        Database db = new Database(this);

        Button saveButton = findViewById(R.id.activityPhotoSelector_SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (photos.isEmpty() && toDelete.isEmpty()) {
                    Toast.makeText(getApplicationContext(), R.string.activityPhotoSelector_NoPhotos, Toast.LENGTH_SHORT).show();
                    return;
                }
                for(Photo photo:photos) {
                    try {
                        db.savePhoto(photo);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), R.string.activityPhotoSelector_FailedSavePhotos, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                for(Photo photo:toDelete) {
                    try {
                        db.deletePhoto(photo);
                    } catch (ConnectException e) {
                        Toast.makeText(getApplicationContext(), R.string.activityPhotoSelector_FailedDeletePhotos, Toast.LENGTH_SHORT).show();
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
            File photoFile;

            String username = AppStatus.getInstance().getCurrentUser().getUsername();
            String timeStamp = Long.toHexString(new Date().getTime());
            String imageFileName = "JPEG_" + "_" + username + "_" + timeStamp + "_";
            try {
                photoFile = File.createTempFile(imageFileName,".jpg",getFilesDir());
                photoPath = photoFile.getPath();
            } catch (IOException e) {
                Toast.makeText(this, R.string.activityPhotoSelector_NoSpace, Toast.LENGTH_SHORT).show();
                return;
            }

            Uri photoUri = FileProvider.getUriForFile(this, getResources().getString(R.string.fileProviderPath), photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Toast.makeText(this, R.string.activityPhotoSelector_PhotoAdded,Toast.LENGTH_SHORT).show();
                photos.add(new Photo(photoPath));
                photoAdapter.notifyDataSetChanged();
            }
            else {
                Toast.makeText(this,R.string.activityPhotoSelector_CancelledAdd,Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onTextSet(String newText, int editorId) {
        if (newText.isEmpty()) {
            Toast.makeText(this,R.string.activityPhotoSelector_NoLabel,Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,R.string.activityPhotoSelector_LabelAdded,Toast.LENGTH_SHORT).show();
            photos.get(selectedIndex).setLabel(newText);
        }
    }
}