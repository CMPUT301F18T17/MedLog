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
import cs.ualberta.ca.medlog.controller.ProblemController;
import cs.ualberta.ca.medlog.entity.Photo;
import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.Record;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.singleton.AppStatus;

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
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.0
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
    private Problem problem;
    private ProblemController controller = new ProblemController(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_problem_view);

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

        problem = AppStatus.getInstance().getViewedProblem();

        updateTitleDisplay(problem.getTitle());
        Calendar cal = Calendar.getInstance();
        cal.setTime(problem.getDate());
        updateDateDisplay(cal);
        updateDescriptionDisplay(problem.getDescription());
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
        startActivity(intent);
    }


    private void openPhotoSlideshow() {
        ArrayList<Photo> photos = new ArrayList<>();
        for(Record record:problem.getRecords()) {
            if (!record.getPhotos().isEmpty()) {
                photos.addAll(record.getPhotos());
            }
        }

        if (photos.isEmpty()) {
            Toast.makeText(this,"No record photos",Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, SlideshowActivity.class);
        intent.putExtra("PHOTOS",photos);
        startActivity(intent);
    }


    private void openAddRecord() {
        Intent intent = new Intent(this, PatientAddRecordActivity.class);
        startActivity(intent);
    }

    private void openTitleEditor() {
        DialogFragment newFragment = new TextEditorFragment();
        Bundle editorData = new Bundle();
        editorData.putInt("argEditorId",0);
        editorData.putString("argHint",getString(R.string.fragmentTextEditor_TitleHint));
        editorData.putString("argInitialText",problem.getTitle());
        editorData.putInt("argMaxLength",30);
        newFragment.setArguments(editorData);
        newFragment.show(getSupportFragmentManager(),"titleEditor");
    }

    private void openDescriptionEditor() {
        DialogFragment newFragment = new TextEditorFragment();
        Bundle editorData = new Bundle();
        editorData.putInt("argEditorId",1);
        editorData.putString("argHint",getString(R.string.fragmentTextEditor_DescHint));
        editorData.putInt("argInputType", InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editorData.putString("argInitialText",problem.getDescription());
        editorData.putInt("argMaxLength",300);
        newFragment.setArguments(editorData);
        newFragment.show(getSupportFragmentManager(),"descEditor");
    }

    public void onTextSet(String newText, int editorId) {
        switch(editorId) {
            case 0:
                if (newText.isEmpty()) {
                    Toast.makeText(this,"No title entered",Toast.LENGTH_SHORT).show();
                    break;
                }
                controller.setTitle((Patient)AppStatus.getInstance().getCurrentUser(),problem,newText);
                updateTitleDisplay(newText);
                break;
            case 1:
                if (newText.isEmpty()) {
                    Toast.makeText(this,"No description entered",Toast.LENGTH_SHORT).show();
                    break;
                }
                controller.setDesc((Patient)AppStatus.getInstance().getCurrentUser(),problem,newText);
                updateDescriptionDisplay(newText);
                break;
        }
    }

    private void updateTitleDisplay(String title) {
        TextView titleView = findViewById(R.id.activityPatientProblemView_ProblemTitleView);
        titleView.setText(title);
    }

    private void updateDescriptionDisplay(String description) {
        TextView descView = findViewById(R.id.activityPatientProblemView_ProblemDescriptionView);
        descView.setText(description);
    }

    private void openDatePicker() {
        DialogFragment newFragment = new DatePickerFragment();
        Bundle pickerData = new Bundle();
        pickerData.putSerializable("argCal",problem.getDate());
        newFragment.setArguments(pickerData);
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public void onNewDateSet(Calendar cal) {
        controller.setDate((Patient)AppStatus.getInstance().getCurrentUser(),problem,cal);
        updateDateDisplay(cal);
    }

    private void updateDateDisplay(Calendar cal) {
        TextView dateView = findViewById(R.id.activityPatientProblemView_ProblemDateView);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        dateView.setText(String.format(Locale.getDefault(),"Since: %04d/%02d/%02d",year,month,day));
    }

    private void deleteProblem() {
        PatientController patientController = new PatientController(this);
        patientController.deleteProblem((Patient)AppStatus.getInstance().getCurrentUser(),problem);
        finish();
    }

    @Override
    public void onBackPressed() {
        AppStatus.getInstance().setViewedProblem(null);
        super.onBackPressed();
    }
}
