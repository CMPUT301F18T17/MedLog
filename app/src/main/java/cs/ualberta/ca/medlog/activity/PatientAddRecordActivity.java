package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
 * @version 0.6
 * @see PatientProblemViewActivity
 * @see PhotoSelectorActivity
 * @see TextEditorFragment
 */
public class PatientAddRecordActivity extends AppCompatActivity implements TextEditorFragment.OnTextSetListener {

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

    public void onTextSet(String text, int editorId) {
        switch (editorId) {
            case 0:
                //TODO Save the title to the record.
                openCommentEditor();
                break;

            case 1:
                //TODO Save the comment to the record.
                break;
        }
    }

    private void openBodyLocationSelector() {
        //TODO Add transfer to a body location selector fragment.
    }

    private void openMapLocationSelector() {
        //TODO Add transfer to a map location selector fragment.
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
