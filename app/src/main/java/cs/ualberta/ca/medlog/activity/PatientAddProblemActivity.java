package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Locale;
import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.controller.PatientController;
import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.singleton.AppStatus;

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
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.1
 * @see PatientMenuActivity
 * @see PatientProblemViewActivity
 * @see DatePickerFragment
 * @see PopupWindow
 */
public class PatientAddProblemActivity extends AppCompatActivity implements DatePickerFragment.OnNewDateSetListener {
    private Calendar cal;

    private PopupWindow postAddNavigationPopup;

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
        datePickerData.putSerializable("argCal",cal.getTime());
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
            Toast.makeText(this,R.string.activityPatientAddProblem_NoTitle,Toast.LENGTH_SHORT).show();
            return;
        }

        EditText descField = findViewById(R.id.activityPatientAddProblem_DescriptionEditText);
        String description = descField.getText().toString();
        if (description.isEmpty()) {
            Toast.makeText(this,R.string.activityPatientAddProblem_NoDesc,Toast.LENGTH_SHORT).show();
            return;
        }

        Problem newProblem = new Problem(title,cal.getTime(),description);
        PatientController controller = new PatientController(this);
        controller.addProblem((Patient)AppStatus.getInstance().getCurrentUser(),newProblem);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View postAddNavigationLayout = inflater.inflate(R.layout.fragment_add_problem_navigation,null);
        Button viewButton = postAddNavigationLayout.findViewById(R.id.fragmentAddProblemNavigation_ViewButton);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openViewProblem(newProblem);
            }
        });
        Button addButton = postAddNavigationLayout.findViewById(R.id.fragmentAddProblemNavigation_AddButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAnotherProblem();
            }
        });
        Button menuButton = postAddNavigationLayout.findViewById(R.id.fragmentAddProblemNavigation_MenuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainMenu();
            }
        });

        int width = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        int height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        postAddNavigationPopup = new PopupWindow(postAddNavigationLayout,width,height,true);
        postAddNavigationPopup.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.popup_background));
        postAddNavigationPopup.showAtLocation(postAddNavigationLayout,Gravity.CENTER,0,0);
    }

    private void openViewProblem(Problem newProblem) {
        Intent intent = new Intent(this, PatientProblemViewActivity.class);
        AppStatus.getInstance().setViewedProblem(newProblem);
        startActivity(intent);
    }

    private void addAnotherProblem() {
        postAddNavigationPopup.dismiss();
    }

    private void openMainMenu() {
        finish();
    }
}
