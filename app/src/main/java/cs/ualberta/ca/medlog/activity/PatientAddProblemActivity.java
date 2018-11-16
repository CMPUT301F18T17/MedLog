package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.controller.PatientController;
import cs.ualberta.ca.medlog.entity.Problem;

/**
 * <p>
 *     Description: <br>
 *         The patient add problem screen for the Application, this presents the gui for a patient
 *         to add a new problem by setting a title, start date and a comment.
 *         Following their confirmation of the addition the patient is prompted on further
 *         navigation to either add another problem, return to the main menu or to view their
 *         newly created problem.
 * </p>
 * <p>
 *     Issues: <br>
 *         Call to a controller to add the given problem to the patient in the system must be added.
 *         Post problem navigation popup must be added.
 *         Call to a controller to get the newly added problems index must be added.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.3
 * @see PatientMenuActivity
 * @see PatientProblemViewActivity
 * @see DatePickerFragment
 */
public class PatientAddProblemActivity extends AppCompatActivity implements DatePickerFragment.OnNewDateSetListener {

    private Calendar cal;
    private Date date;
    Intent intent=getIntent();
    private String username = intent.getStringExtra(PatientLoginActivity.EXTRA_MESSAGE);
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
                finalizeProblemAddition();
            }
        });

        cal = Calendar.getInstance();
        updateDateButtonDisplay();
    }

    private void openDatePicker() {
        DialogFragment newFragment = new DatePickerFragment();
        Bundle datePickerData = new Bundle();
        datePickerData.putSerializable("argCal",cal);
        newFragment.setArguments(datePickerData);
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public void onNewDateSet(Calendar newCal) {
        cal = newCal;
        updateDateButtonDisplay();
    }

    private void updateDateButtonDisplay() {
        Button dateButton = findViewById(R.id.activityPatientAddProblem_DateEditButton);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        dateButton.setText(String.format(Locale.getDefault(),"%04d/%02d/%02d",year,month,day));
    }

    private void finalizeProblemAddition() {
        EditText titleField = findViewById(R.id.activityPatientAddProblem_TitleEditText);
        String title = titleField.getText().toString();
        if (title.isEmpty()) {
            Toast.makeText(this,"No title entered",Toast.LENGTH_SHORT).show();
            return;
        }

        if (title.length()>30) {
            Toast.makeText(this,"Title too long",Toast.LENGTH_SHORT).show();
            return;
        }

        EditText descField = findViewById(R.id.activityPatientAddProblem_DescriptionEditText);
        String description = descField.getText().toString();
        if (description.isEmpty()) {
            Toast.makeText(this,"No description entered",Toast.LENGTH_SHORT).show();
            return;
        }
        if (description.length()>300) {
            Toast.makeText(this,"Description too long",Toast.LENGTH_SHORT).show();
            return;
        }

        date=cal.getTime();

        // controller call to add the given problem to the patient in the system

        Problem problem = new Problem(title,date,description);
        PatientController controller = new PatientController(this);
        controller.addProblem(problem,username);

        Toast.makeText(this,"Problem added",Toast.LENGTH_SHORT).show();

        //TODO Add popup for post problem creation navigation.

        //This currently is a stand in for this popup navigation
        Intent intent = new Intent(this, PatientProblemViewActivity.class);
        //TODO Add controller call to get the index for the added problem.
        intent.putExtra("problemIndex",0);
        startActivity(intent);
    }
}
