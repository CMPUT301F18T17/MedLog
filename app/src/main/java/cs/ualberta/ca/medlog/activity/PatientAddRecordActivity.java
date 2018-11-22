package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cs.ualberta.ca.medlog.R;

import cs.ualberta.ca.medlog.controller.ProblemController;
import cs.ualberta.ca.medlog.entity.MapLocation;
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
 *         Transfer to a Body Location Selector Fragment must be added.
 *         Transfer to a Map Location Selector Fragment must be added.
 *         Receiving and saving newly added photos must be added.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.8
 * @see PatientProblemViewActivity
 * @see PhotoSelectorActivity
 * @see TextEditorFragment
 */
public class PatientAddRecordActivity extends AppCompatActivity implements TextEditorFragment.OnTextSetListener {
    final int MAP_LOCATION_REQUEST = 1;

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
                    Toast.makeText(this,"No title entered",Toast.LENGTH_SHORT).show();
                    break;
                }
                newRecord.setTitleComment(newText,newRecord.getComment());
                openCommentEditor();
                break;

            case 1:
                if (newText.isEmpty()) {
                    Toast.makeText(this,"No comment entered",Toast.LENGTH_SHORT).show();
                    break;
                }
                newRecord.setTitleComment(newRecord.getTitle(),newText);
                Toast.makeText(this,"Title & Comment added",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void openBodyLocationSelector() {
        //TODO Add transfer to a body location selector fragment.
    }

    private void openMapLocationSelector() {
        Intent intent = new Intent(this, AddMapLocationActivity.class);
        startActivityForResult(intent, MAP_LOCATION_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == MAP_LOCATION_REQUEST) {
            if (resultCode == RESULT_OK) { // If a map location was selected
                double latitude = data.getDoubleExtra("Latitude", -1);
                double longitude = data.getDoubleExtra("Longitude", -1);
                Toast.makeText(this, R.string.activityPatientAddRecordActivity_MapLocationAdded, Toast.LENGTH_SHORT).show();
                newRecord.setMapLocation(new MapLocation(latitude,longitude));
            }
            else { // If the select location button was tapped, but the user never selected a position on the map
                Toast.makeText(this, R.string.activityPatientAddRecordActivity_NoLocationAdded, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void openPhotosSelector() {
        Intent intent = new Intent(this, PhotoSelectorActivity.class);
        intent.putExtra("GUIDE_PHOTO",newRecord.getBodyLocation().getPhoto().getPhotoBitmap());
        intent.putExtra("PHOTOS",newRecord.getPhotos());
        startActivity(intent);
    }

    private void completeRecord() {
        if (!newRecord.isValid()) {
            Toast.makeText(this,"Record has no data",Toast.LENGTH_SHORT).show();
            return;
        }

        ProblemController controller = new ProblemController(this);
        controller.addRecord((Patient)AppStatus.getInstance().getCurrentUser(),AppStatus.getInstance().getViewedProblem(),newRecord);

        Intent intent = new Intent(this, PatientProblemViewActivity.class);
        AppStatus.getInstance().setViewedRecord(newRecord);
        startActivity(intent);
    }
}
