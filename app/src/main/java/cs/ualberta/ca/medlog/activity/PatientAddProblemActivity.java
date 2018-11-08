package cs.ualberta.ca.medlog.activity;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.util.Calendar;

import cs.ualberta.ca.medlog.R;

/**
 * <p>
 *     Description: <br>
 *         The Activity for the Patient add problem screen, this presents the gui for a Patient
 *         to add a new problem by setting a title, start date and comment. From there the Patient
 *         is prompted on further navigation to either add another problem, return to the main
 *         menu or to view their newly created problem.
 * </p>
 * <p>
 *     Issues: <br>
 *         Add the finalization for adding a problem.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.1
 * @see PatientMenuActivity
 */
public class PatientAddProblemActivity extends AppCompatActivity implements DatePickerFragment.OnNewDateSetListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_add_problem);
        Button dateButton = findViewById(R.id.activityPatientAddProblem_DateEditButton);
        Button addButton = findViewById(R.id.activityPatientAddProblem_AddButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalizeAddingProblem();
            }
        });

        Calendar cal = Calendar.getInstance();
        changeDateButtonDisplay(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
    }

    private void openDatePicker() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public void onNewDateSet(int newYear, int newMonth, int newDay) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,newYear);
        cal.set(Calendar.MONTH,newMonth);
        cal.set(Calendar.DAY_OF_MONTH,newDay);
        changeDateButtonDisplay(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));

        //TODO Add problem date value updating code

    }

    private void changeDateButtonDisplay(int year, int month, int day) {
        Button dateButton = findViewById(R.id.activityPatientAddProblem_DateEditButton);
        dateButton.setText(String.format("%04d-%02d-%02d",year,month,day));
    }

    private void finalizeAddingProblem() {
        //TODO Add code for finalizing problem addition

    }


}
