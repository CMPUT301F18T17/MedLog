package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.controller.PatientController;
import cs.ualberta.ca.medlog.entity.Problem;

/**
 * <p>
 *     Description: <br>
 *         The patient problem viewing screen activity for the Application, this presents the gui
 *         for the Patient to view one of their problems data, as well as the ability to proceed to
 *         screens where they can view all of the problem's attached records, add a new record to the
 *         problem or view a slideshow of all the problem's record's photos.
 *         An options menu is also present that allows the patient to edit the title, date or
 *         description of the problem. As well as an option to delete the problem.
 * </p>
 * <p>
 *     Issues: <br>
 *         A call to the system to get the currently logged in patient's problems must be added.
 *         Updating the problem data display fields to match the given problem on open must be added.
 *         Calls to a controller to update problem details must be added.
 *         Sending problem data to editing fragments must be added.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.6
 * @see PatientViewProblemsActivity
 * @see PatientAddProblemActivity
 * @see PatientSearchActivity
 * @see TextEditorFragment
 * @see DatePickerFragment
 * @see PatientAddRecordActivity
 * @see PatientViewRecordsActivity
 * @see SlideshowActivity
 */
public class PatientProblemViewActivity extends AppCompatActivity implements DatePickerFragment.OnNewDateSetListener, TextEditorFragment.OnTextSetListener {
    private ArrayList<Problem> problems;
    private Problem problem;
    private int problemIndex;
    Intent intent = getIntent();
    private String username=intent.getStringExtra(PatientViewProblemsActivity.EXTRA_MESSAGE);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_problem_view);

        problemIndex = intent.getIntExtra("problemIndex",0);
        // Call system to get the patient's list of problems and then grab the given index.

        PatientController controller = new PatientController(this);
        problems=controller.getProblems(username);
        problem=problems.get(problemIndex);

        Button viewRecordsButton = findViewById(R.id.activityPatientProblemView_ViewRecordsButton);
        Button slideShowButton = findViewById(R.id.activityPatientProblemView_SlideshowButton);
        Button addRecordButton = findViewById(R.id.activityPatientProblemView_AddRecordButton);
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
        addRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddRecord();
            }
        });

        //TODO Read data from the given problem to display in the related fields.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_patient_problem_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuPatientProblemView_EditTitle:
                openTitleEditor();
                return true;
            case R.id.menuPatientProblemView_EditDate:
                openDatePicker();
                return true;
            case R.id.menuPatientProblemView_EditDescription:
                openDescriptionEditor();
                return true;
            case R.id.menuPatientProblemView_DeleteProblem:
                deleteProblem();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openRecordsList() {
        Intent intent = new Intent(this, PatientViewRecordsActivity.class);
        intent.putExtra("problemIndex",problemIndex);
        startActivity(intent);
    }


    private void openPhotoSlideshow() {
        Intent intent = new Intent(this, SlideshowActivity.class);
        intent.putExtra("problemIndex",problemIndex);
        startActivity(intent);
    }


    private void openAddRecord() {
        Intent intent = new Intent(this, PatientAddRecordActivity.class);
        intent.putExtra("problemIndex",problemIndex);
        startActivity(intent);
    }

    private void openTitleEditor() {
        DialogFragment newFragment = new TextEditorFragment();
        Bundle editorData = new Bundle();
        editorData.putInt("argEditorId",0);
        editorData.putString("argHint",getString(R.string.fragmentTextEditor_TitleHint));
        //TODO Argument to send the existing title.
        newFragment.setArguments(editorData);
        newFragment.show(getSupportFragmentManager(),"titleEditor");
    }

    private void openDescriptionEditor() {
        DialogFragment newFragment = new TextEditorFragment();
        Bundle editorData = new Bundle();
        editorData.putInt("argEditorId",1);
        editorData.putString("argHint",getString(R.string.fragmentTextEditor_DescHint));
        editorData.putInt("argInputType", InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        //TODO Argument to send the existing description.
        newFragment.setArguments(editorData);
        newFragment.show(getSupportFragmentManager(),"descEditor");
    }

    public void onTextSet(String newText, int editorId) {
        PatientController controller = new PatientController(this);
        switch(editorId) {
            case 0:
                if (newText.isEmpty()) {
                    Toast.makeText(this,"No title entered",Toast.LENGTH_SHORT);
                    break;
                }
                // Call to controller to update the problem title value.
                controller.setTitle(username,problemIndex,newText);
                updateTitleDisplay(newText);
                break;
            case 1:
                if (newText.isEmpty()) {
                    Toast.makeText(this,"No description entered",Toast.LENGTH_SHORT);
                    break;
                }
                // Call to controller to update the problem description value.
                controller.setDesc(username,problemIndex,newText);
                updateDescriptionDisplay(newText);
                break;
        }
    }

    private void updateTitleDisplay(String title) {
        TextView titleView = findViewById(R.id.activityPatientProblemView_RecordTitleView);
        titleView.setText(title);
    }

    private void updateDescriptionDisplay(String description) {
        TextView descView = findViewById(R.id.activityPatientProblemView_RecordDescriptionView);
        descView.setText(description);
    }

    private void openDatePicker() {
        DialogFragment newFragment = new DatePickerFragment();
        //TODO Argument to send the existing date.
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public void onNewDateSet(Calendar cal) {
        // Call to controller to update the problem date value.
        PatientController controller = new PatientController(this);
        controller.setDate(username,problemIndex, cal);
        updateDateDisplay(cal);
    }

    private void updateDateDisplay(Calendar cal) {
        TextView dateView = findViewById(R.id.activityPatientProblemView_RecordDateView);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        dateView.setText(String.format(Locale.getDefault(),"Since: %04d/%02d/%02d",year,month,day));
    }

    private void deleteProblem() {
        //TODO Call to controller to delete the patient's problem

        PatientController controller = new PatientController(this);
        controller.deleteProblem(username,problemIndex);

        finish();
    }
}
