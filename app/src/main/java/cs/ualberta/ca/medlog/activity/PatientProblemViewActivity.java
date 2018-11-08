package cs.ualberta.ca.medlog.activity;

import android.app.ActionBar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Locale;

import cs.ualberta.ca.medlog.R;

/**
 * <p>
 *     Description: <br>
 *         The Activity for the Patient problem view screen, this presents the gui for the Patient
 *         to view one of their problems data, as well as the ability to proceed to screens to
 *         view all of the attached records to the problem, add a new record to the problem, and
 *         a fragment to view a slideshow of all the problems record photos.
 *         Additionally there is an options menu from which teh user can edit the details of the
 *         problem, or delete the problem.
 * </p>
 * <p>
 *     Issues: <br>
 *         Transfer to a Patient Problem Records List must be added.
 *         Transfer to a Patient Add Record must be added.
 *         Transfer to a slideshow view must be added
 *         Actual code to read a problem and present it must be added.
 *         Actual code to update problem details must be added.
 *         Actual code to update fragments with problem data must be added.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.1
 * @see PatientViewProblemsActivity
 * @see PatientAddProblemActivity
 */
public class PatientProblemViewActivity extends AppCompatActivity implements DatePickerFragment.OnNewDateSetListener, DescriptionEditorFragment.OnDescriptionSetListener, TitleEditorFragment.OnTitleSetListener {

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

        //TODO Add code to receive a provided problem object and set the related fields to its data.
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
        //TODO Add transfer to Patient Problem Records List Activity
    }


    private void openPhotoSlideshow() {
        //TODO Add transfer to slideshow view
    }


    private void openAddRecord() {
        //TODO Add transfer to Add Record Activity
    }

    private void openTitleEditor() {
        //TODO Add arguments to send the existing title

        DialogFragment newFragment = new TitleEditorFragment();
        newFragment.show(getSupportFragmentManager(),"titleEditor");
    }

    public void onNewTitleSet(String newTitle) {
        setTitleDisplay(newTitle);

        //TODO Add problem title value updating code
    }

    private void setTitleDisplay(String title) {
        TextView titleView = findViewById(R.id.activityPatientProblemView_RecordTitleView);
        titleView.setText(title);
    }

    private void openDatePicker() {
        //TODO Add arguments to send the existing date

        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public void onNewDateSet(int newYear, int newMonth, int newDay) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,newYear);
        cal.set(Calendar.MONTH,newMonth);
        cal.set(Calendar.DAY_OF_MONTH,newDay);
        setDateDisplay(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));

        //TODO Add problem date value updating code

    }

    private void setDateDisplay(int year, int month, int day) {
        TextView dateView = findViewById(R.id.activityPatientProblemView_RecordDateView);
        dateView.setText(String.format(Locale.getDefault(),"Since: %04d/%02d/%02d",year,month,day));
    }

    private void openDescriptionEditor() {
        //TODO Add arguments to send the existing description

        DialogFragment newFragment = new DescriptionEditorFragment();
        newFragment.show(getSupportFragmentManager(),"descEditor");
    }

    public void onNewDescriptionSet(String newDesc) {
        setDescriptionDisplay(newDesc);

        //TODO Add problem description value updating code
    }

    private void setDescriptionDisplay(String description) {
        TextView descView = findViewById(R.id.activityPatientProblemView_RecordDescriptionView);
        descView.setText(description);
    }

    private void deleteProblem() {
        //TODO Add code to delete problem in model

        finish();
    }
}
