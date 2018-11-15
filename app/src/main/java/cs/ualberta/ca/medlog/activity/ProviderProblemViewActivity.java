package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Locale;

import cs.ualberta.ca.medlog.R;

/**
 * <p>
 *     Description: <br>
 *         The Activity for the Care Provider patient problem view screen, this presents the gui
 *         for the Provider to view one of their patient's problems data, as well as the ability
 *         view all of the attached records to the problem or to view a slideshow of all the problem's
 *         records photos.
 *         Additionally there is a fragment to add a comment record.
 *         Furthermore the username can be clicked to travel to the owning patient's profile.
 * </p>
 * <p>
 *     Issues: <br>
 *         Adding the records list as an argument for opening view records must be added.
 *         Actual code to send photos to the slideshow activity must be added.
 *         Actual code to add a comment record to the problem must be added.
 *         Actual code to read a problem and present it must be added.
 *         Actual code to pass the specific patient owner as an argument must be added on username click.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.3
 * @see ProviderSearchActivity
 * @see ProviderPatientViewProblemsActivity
 * @see SlideshowActivity
 * @see TextEditorFragment
 */
public class ProviderProblemViewActivity extends AppCompatActivity implements TextEditorFragment.OnTextSetListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_problem_view);
        Button viewRecordsButton = findViewById(R.id.activityProviderProblemView_ViewRecordsButton);
        Button slideShowButton = findViewById(R.id.activityProviderProblemView_SlideshowButton);
        Button addCommentRecordButton = findViewById(R.id.activityProviderProblemView_AddRecordButton);
        viewRecordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRecordsList();
            }
        });
        slideShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPhotoSlideshow();
            }
        });
        addCommentRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddCommentRecord();
            }
        });

        TextView patientUsernameView = findViewById(R.id.activityProviderProblemView_PatientUsernameView);
        patientUsernameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPatientProfile();
            }
        });

        //TODO Add code to receive a provided problem object and set the related fields to its data.
    }

    private void openRecordsList() {
        Intent intent = new Intent(this, ProviderPatientViewRecordsActivity.class);

        //TODO Add argument code to pass the problems record list.

        startActivity(intent);
    }


    private void openPhotoSlideshow() {
        Intent intent = new Intent(this, SlideshowActivity.class);

        //TODO Add argument code to pass a photos list of all the records photos.

        startActivity(intent);
    }


    private void openAddCommentRecord() {
        DialogFragment newFragment = new TextEditorFragment();
        Bundle editorData = new Bundle();
        editorData.putString("argHint",getString(R.string.fragmentTextEditor_CommentHint));
        editorData.putInt("argInputType", InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        newFragment.setArguments(editorData);
        newFragment.show(getSupportFragmentManager(),"commentEditor");
    }

    public void onTextSet(String newText, int editorId) {
        //TODO Add code to update the problem with the new comment record.

    }

    private void openPatientProfile() {

        Intent intent = new Intent(this, ProviderPatientProfileActivity.class);

        //TODO Add argument code to pass the problems record list.

        startActivity(intent);
    }
}
