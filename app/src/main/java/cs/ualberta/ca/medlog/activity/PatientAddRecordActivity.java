package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import cs.ualberta.ca.medlog.R;

/**
 * <p>
 *     Description: <br>
 *         The Activity for the Patient add record screen, this presents the gui for a Patient
 *         to add a new record to one of their problems. From here they can choose to proceed to
 *         add any of the four different problem fields and once they have they can save the record
 *         to the problem.
 *         The problem fields are title & comment, map location, body location and additional
 *         photos, each of which are added using fragments or their own separate activities.
 * </p>
 * <p>
 *     Issues: <br>
 *         Code to save changes to the title and comment must be added.
 *         Transfer to a Body Location Selector Fragment must be added.
 *         Transfer to a Map Location Selector Fragment must be added.
 *         Code for sending existing added photos when adding more must be added.
 *         Code for adding the photos selected must be added.
 *         Must add code to add the record to the actual model.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.4
 * @see PatientProblemViewActivity
 * @see PhotoSelectorActivity
 * @see TextEditorFragment
 * @see PhotoSelectorActivity
 */
public class PatientAddRecordActivity extends AppCompatActivity implements TextEditorFragment.OnTextSetListener {

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
    }

    private void openTitleEditor() {
        //TODO Add code to send any existing title.

        DialogFragment newFragment = new TextEditorFragment();
        Bundle editorData = new Bundle();
        editorData.putInt("argEditorId",0);
        editorData.putString("argHint",getString(R.string.fragmentTextEditor_TitleHint));
        newFragment.setArguments(editorData);
        newFragment.show(getSupportFragmentManager(),"titleEditor");
    }

    private void openCommentEditor() {
        //TODO Add code to send any existing comment.

        DialogFragment newFragment = new TextEditorFragment();
        Bundle editorData = new Bundle();
        editorData.putInt("argEditorId",1);
        editorData.putString("argHint",getString(R.string.fragmentTextEditor_CommentHint));
        editorData.putInt("argInputType", InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        newFragment.setArguments(editorData);
        newFragment.show(getSupportFragmentManager(),"commentEditor");
    }

    public void onTextSet(String text, int editorId) {
        switch (editorId) {
            case 0:
                //TODO Add code to save the title
                openCommentEditor();
                break;

            case 1:
                //TODO Add code to save the comment
                break;
        }
    }

    private void openBodyLocationSelector() {
        //TODO Add transfer to a body location selector fragment.
    }

    private void openMapLocationSelector() {
        //TODO Add transfer to a map location selector fragment.

        //PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
    }

    private void openPhotosSelector() {

        Intent intent = new Intent(this, PhotoSelectorActivity.class);

        //TODO Add code to transfer any existing photos.

        startActivity(intent);
    }

    private void completeRecord() {
        //TODO Add the completed record to the problem in the code.

        Intent intent = new Intent(this, PatientProblemViewActivity.class);
        startActivity(intent);
    }
}
