package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import cs.ualberta.ca.medlog.R;

import cs.ualberta.ca.medlog.controller.ProblemController;
import cs.ualberta.ca.medlog.entity.BodyLocation;
import cs.ualberta.ca.medlog.entity.MapLocation;
import cs.ualberta.ca.medlog.entity.Photo;
import cs.ualberta.ca.medlog.entity.Record;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.singleton.AppStatus;

/**
 * <p>
 *     Description: <br>
 *         The patient add record screen activity for the Application, this presents the gui for a patient
 *         to add a new record to one of their problems. This presents them buttons to proceed to
 *         screens where they can add any of the four different record fields of title & comment,
 *         map location, body location and additional photos, which each record must have at least
 *         one of. Once a field has been added the patient can save the record.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.1
 * @see PatientProblemViewActivity
 * @see AddBodyLocationActivity
 * @see AddMapLocationActivity
 * @see PhotoSelectorActivity
 * @see TextEditorFragment
 */
public class PatientAddRecordActivity extends AppCompatActivity implements TextEditorFragment.OnTextSetListener {
    final int MAP_LOCATION_REQUEST = 1;
    final int BODY_LOCATION_REQUEST = 2;
    final int PHOTO_REQUEST = 3;

    private Record newRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_add_record);

        Button titleCommentButton = findViewById(R.id.activityPatientAddRecord_TitleCommentButton);
        Button bodyLocationButton = findViewById(R.id.activityPatientAddRecord_BodyLocationButton);
        Button mapLocationButton = findViewById(R.id.activityPatientAddRecord_MapLocationButton);
        Button photosButton = findViewById(R.id.activityPatientAddRecord_PhotosButton);
        Button saveButton = findViewById(R.id.activityPatientAddRecord_SaveButton);
        titleCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTitleEditor();
            }
        });
        bodyLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBodyLocationSelector();
            }
        });
        mapLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapLocationSelector();
            }
        });
        photosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPhotosSelector();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeRecord();
            }
        });

        newRecord = new Record(AppStatus.getInstance().getCurrentUser().getUsername());
    }

    private void openTitleEditor() {
        DialogFragment newFragment = new TextEditorFragment();
        Bundle editorData = new Bundle();
        editorData.putInt("argEditorId",0);
        editorData.putString("argHint",getString(R.string.fragmentTextEditor_TitleHint));
        editorData.putString("argInitialText",newRecord.getTitle());
        editorData.putInt("argMaxLength",30);
        newFragment.setArguments(editorData);
        newFragment.show(getSupportFragmentManager(),"titleEditor");
    }

    private void openCommentEditor() {
        DialogFragment newFragment = new TextEditorFragment();
        Bundle editorData = new Bundle();
        editorData.putInt("argEditorId",1);
        editorData.putString("argHint",getString(R.string.fragmentTextEditor_CommentHint));
        editorData.putInt("argInputType", InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editorData.putString("argInitialText",newRecord.getComment());
        editorData.putInt("argMaxLength",300);
        newFragment.setArguments(editorData);
        newFragment.show(getSupportFragmentManager(),"commentEditor");
    }

    public void onTextSet(String newText, int editorId) {
        switch (editorId) {
            case 0:
                if (newText.isEmpty()) {
                    Toast.makeText(this,R.string.activityPatientAddRecord_NoTitleEntered,Toast.LENGTH_SHORT).show();
                    break;
                }
                newRecord.setTitleComment(newText,newRecord.getComment());
                Toast.makeText(this,R.string.activityPatientAddRecord_TitleAdded,Toast.LENGTH_SHORT).show();
                openCommentEditor();
                break;

            case 1:
                updateButtonColour(findViewById(R.id.activityPatientAddRecord_TitleCommentButton));
                if (newText.isEmpty()) {
                    Toast.makeText(this,R.string.activityPatientAddRecord_NoCommentEntered,Toast.LENGTH_SHORT).show();
                    break;
                }
                newRecord.setTitleComment(newRecord.getTitle(),newText);
                Toast.makeText(this, R.string.activityPatientAddRecord_CommentAdded, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void openBodyLocationSelector() {
        if (!((Patient)AppStatus.getInstance().getCurrentUser()).getBodyPhotos().isEmpty()) {
            Intent intent = new Intent(this, AddBodyLocationActivity.class);
            startActivityForResult(intent, BODY_LOCATION_REQUEST);
        }
        else {
            Toast.makeText(this,R.string.activityPatientAddRecord_NoBodyPhotos,Toast.LENGTH_SHORT).show();
        }
    }

    private void openMapLocationSelector() {
        Intent intent = new Intent(this, AddMapLocationActivity.class);
        startActivityForResult(intent, MAP_LOCATION_REQUEST);
    }

    private void openPhotosSelector() {
        Intent intent = new Intent(this, PhotoSelectorActivity.class);
        if (newRecord.getBodyLocation() != null) {
            intent.putExtra("GUIDE_PHOTO", newRecord.getBodyLocation().getPhoto());
        }
        intent.putExtra("PHOTOS",newRecord.getPhotos());
        startActivityForResult(intent,PHOTO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == MAP_LOCATION_REQUEST) {
                double latitude = data.getDoubleExtra("LATITUDE", -1);
                double longitude = data.getDoubleExtra("LONGITUDE", -1);
                Toast.makeText(this, R.string.activityPatientAddRecord_MapLocationAdded, Toast.LENGTH_SHORT).show();
                newRecord.setMapLocation(new MapLocation(latitude, longitude));
                updateButtonColour(findViewById(R.id.activityPatientAddRecord_MapLocationButton));
            }
            else if (requestCode == BODY_LOCATION_REQUEST) {
                BodyLocation bodyLocation = (BodyLocation)data.getSerializableExtra("BODY_LOCATION");
                Toast.makeText(this,R.string.activityPatientAddRecord_BodyLocationAdded,Toast.LENGTH_SHORT).show();
                newRecord.setBodyLocation(bodyLocation);
                updateButtonColour(findViewById(R.id.activityPatientAddRecord_BodyLocationButton));
            }
            else if (requestCode == PHOTO_REQUEST) {
                ArrayList<Photo> photos = (ArrayList<Photo>)data.getSerializableExtra("PHOTOS");
                Toast.makeText(this, R.string.activityPatientAddRecord_PhotosAdded,Toast.LENGTH_SHORT).show();
                newRecord.setPhotos(photos);
                updateButtonColour(findViewById(R.id.activityPatientAddRecord_PhotosButton));
            }
        }
    }

    private void updateButtonColour(Button button) {
        ColorStateList colorStateList = new ColorStateList(new int[][]{new int[0]}, new int[]{getResources().getColor(R.color.app_buttonColour)});
        button.setBackgroundTintList(colorStateList);
    }
    private void completeRecord() {
        if (!newRecord.isValid()) {
            Toast.makeText(this,R.string.activityPatientAddRecord_NoRecordData,Toast.LENGTH_SHORT).show();
            return;
        }

        ProblemController controller = new ProblemController(this);
        controller.addRecord((Patient)AppStatus.getInstance().getCurrentUser(),AppStatus.getInstance().getViewedProblem(),newRecord);
        Toast.makeText(this,R.string.activityPatientAddRecord_RecordAdded,Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, PatientProblemViewActivity.class);
        AppStatus.getInstance().setViewedRecord(newRecord);
        startActivity(intent);
    }
}
