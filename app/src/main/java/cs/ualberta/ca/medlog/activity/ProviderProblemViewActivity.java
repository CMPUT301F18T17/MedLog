package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.net.ConnectException;
import java.util.Calendar;
import java.util.Locale;

import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.controller.ProblemController;
import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.Record;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.exception.UserNotFoundException;
import cs.ualberta.ca.medlog.helper.Database;
import cs.ualberta.ca.medlog.singleton.CurrentUser;

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
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.0
 * @see ProviderSearchActivity
 * @see ProviderPatientViewProblemsActivity
 * @see ProviderPatientViewRecordsActivity
 * @see SlideshowActivity
 * @see TextEditorFragment
 */
public class ProviderProblemViewActivity extends AppCompatActivity implements TextEditorFragment.OnTextSetListener {
    private Problem problem;
    private String patientUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_problem_view);

        Intent intent = getIntent();
        patientUsername = intent.getStringExtra("PATIENT_USERNAME");
        problem = (Problem) intent.getSerializableExtra("PROBLEM");

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
        patientUsernameView.setText(patientUsername);
        patientUsernameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPatientProfile();
            }
        });

        TextView problemTitleView = findViewById(R.id.activityProviderProblemView_ProblemTitleView);
        problemTitleView.setText(problem.getTitle());
        TextView problemDateView = findViewById(R.id.activityProviderProblemView_ProblemDateView);
        Calendar cal = Calendar.getInstance();
        cal.setTime(problem.getDate());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        problemDateView.setText(String.format(Locale.getDefault(),"Since: %04d/%02d/%02d",year,month,day));
        TextView problemDescView = findViewById(R.id.activityProviderProblemView_ProblemDescriptionView);
        problemDescView.setText(patientUsername);
    }

    private void openRecordsList() {
        Intent intent = new Intent(this, ProviderPatientViewRecordsActivity.class);
        intent.putExtra("PROBLEM_TITLE",problem.getTitle());
        intent.putExtra("RECORDS",problem.getRecords());
        startActivity(intent);
    }


    private void openPhotoSlideshow() {
        Intent intent = new Intent(this, SlideshowActivity.class);
        intent.putExtra("RECORDS",problem.getRecords());
        startActivity(intent);
    }


    private void openAddCommentRecord() {
        DialogFragment newFragment = new TextEditorFragment();
        Bundle editorData = new Bundle();
        editorData.putString("argHint",getString(R.string.fragmentTextEditor_CommentHint));
        editorData.putInt("argInputType", InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editorData.putInt("argMaxLength",300);
        newFragment.setArguments(editorData);
        newFragment.show(getSupportFragmentManager(),"commentEditor");
    }

    public void onTextSet(String newText, int editorId) {
        if(newText.isEmpty()) {
            Toast.makeText(this,"No comment entered", Toast.LENGTH_SHORT).show();
            return;
        }

        Database db = new Database(this);
        Patient patient;
        try {
            patient = db.loadPatient(patientUsername);
        } catch(UserNotFoundException e) {
            Toast.makeText(this,"Patient doesn't exist", Toast.LENGTH_SHORT).show();
            return;
        } catch(ConnectException e) {
            Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();
            return;
        }

        Record newRecord = new Record(CurrentUser.getInstance().getAsProvider().getUsername());
        newRecord.setTitleComment("Care Provider Comment",newText);

        ProblemController controller = new ProblemController(this);
        controller.addRecord(patient,problem,newRecord);
    }

    private void openPatientProfile() {
        Database db = new Database(this);
        Patient toOpen;
        try {
            toOpen = db.loadPatient(patientUsername);
        } catch(UserNotFoundException e) {
            Toast.makeText(this,"Patient doesn't exist", Toast.LENGTH_SHORT).show();
            return;
        } catch(ConnectException e) {
            Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, ProviderPatientProfileActivity.class);
        intent.putExtra("PATIENT", toOpen);
        startActivity(intent);
    }
}
