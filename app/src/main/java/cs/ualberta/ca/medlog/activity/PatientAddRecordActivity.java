package cs.ualberta.ca.medlog.activity;

import android.content.Context;
import android.content.Intent;
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
import cs.ualberta.ca.medlog.entity.Record;

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
 *         Arguments to send existing titles, comments and photos must be added.
 *         Saving of title and comment changes must be added.
 *         Transfer to a Body Location Selector Fragment must be added.
 *         Transfer to a Map Location Selector Fragment must be added.
 *         Receiving and saving newly added photos must be added.
 *         Checks of record validity must be added.
 *         Should convert the adding of the record to the problem to be done upon return.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.7
 * @see PatientProblemViewActivity
 * @see PhotoSelectorActivity
 * @see TextEditorFragment
 */
public class PatientAddRecordActivity extends AppCompatActivity implements TextEditorFragment.OnTextSetListener {
    final int MAP_LOCATION_REQUEST = 1;

    int problemIndex;
    private Record record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_add_record);

        Intent intent = getIntent();
        problemIndex = intent.getIntExtra("problemIndex",0);

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
    }

    private void openTitleEditor() {
        DialogFragment newFragment = new TextEditorFragment();
        Bundle editorData = new Bundle();
        editorData.putInt("argEditorId",0);
        editorData.putString("argHint",getString(R.string.fragmentTextEditor_TitleHint));
        //TODO Argument to send any existing record title.
        newFragment.setArguments(editorData);
        newFragment.show(getSupportFragmentManager(),"titleEditor");
    }

    private void openCommentEditor() {
        DialogFragment newFragment = new TextEditorFragment();
        Bundle editorData = new Bundle();
        editorData.putInt("argEditorId",1);
        editorData.putString("argHint",getString(R.string.fragmentTextEditor_CommentHint));
        editorData.putInt("argInputType", InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        //TODO Argument to send any existing record comment.
        newFragment.setArguments(editorData);
        newFragment.show(getSupportFragmentManager(),"commentEditor");
    }

    public void onTextSet(String newText, int editorId) {
        switch (editorId) {
            case 0:
                if (newText.isEmpty()) {
                    Toast.makeText(this,"No title entered",Toast.LENGTH_SHORT);
                    break;
                }
                //TODO Save the title to the record.
                openCommentEditor();
                break;

            case 1:
                if (newText.isEmpty()) {
                    Toast.makeText(this,"No comment entered",Toast.LENGTH_SHORT);
                    break;
                }
                //TODO Save the comment to the record.
                Toast.makeText(this,"Title & Comment added",Toast.LENGTH_SHORT);
                break;
        }
    }

    private void openBodyLocationSelector() {
        //TODO Add transfer to a body location selector fragment.
    }

    private void openMapLocationSelector() {
        Intent intent = new Intent(this, PatientAddMapLocationActivity.class);
        startActivityForResult(intent, MAP_LOCATION_REQUEST);
        //Intent intent = new Intent(this, ViewMapLocationActivity.class); // This line and the next are temporary, tests ViewMapLocationActivity
        //startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == MAP_LOCATION_REQUEST) {
            if (resultCode == RESULT_OK) { // If a map location was selected
                double latitude = data.getDoubleExtra("Latitude", -1);
                double longitude = data.getDoubleExtra("Longitude", -1);

                Context context = getApplicationContext();
                String toastMessage = "Map Location Added.";
                Toast toast = Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT);
                toast.show();

                //TODO Save the returned latitude and longitude values
            }
            else { // If the select location button was tapped, but the user never selected a position on the map
                Context context = getApplicationContext();
                String toastMessage = "You must select a position on the map. No map location was added.";
                Toast toast = Toast.makeText(context, toastMessage, Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    private void openPhotosSelector() {
        Intent intent = new Intent(this, PhotoSelectorActivity.class);
        //TODO Argument to send any existing photos.
        startActivity(intent);
    }

    private void completeRecord() {
        boolean validRecord = true; //TODO Change to false once below TODO handled
        //TODO Check if the given record is valid in terms of having at least one field filled

        if (!validRecord) {
            Toast.makeText(this,"Record has no data",Toast.LENGTH_SHORT).show();
            return;
        }

        //TODO Call system to get the patient's problem by the provided index and add the record

        Intent intent = new Intent(this, PatientProblemViewActivity.class);
        startActivity(intent);
    }
}
