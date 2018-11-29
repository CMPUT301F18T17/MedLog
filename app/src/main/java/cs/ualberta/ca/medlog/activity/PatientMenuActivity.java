package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;

import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.controller.PatientController;
import cs.ualberta.ca.medlog.entity.Photo;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.helper.ElasticSearchController;
import cs.ualberta.ca.medlog.singleton.AppStatus;

/**
 * <p>
 *     Description: <br>
 *         The patient main menu screen activity for the Application, this presents the gui
 *         for the Patient to proceed to screens in which they can add new problems, view their
 *         existing problems or search their problems.
 *         An options menu is also present from which the user can proceed to a screen to view
 *         their profile or logout and return back to the start screen.
 *         On the first login of a user an additional popup asking the user if they want to proceed
 *         to a screen to add body pictures is also displayed.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.1
 * @see StartScreenActivity
 * @see PatientLoginActivity
 * @see PatientSignUpActivity
 * @see PatientProfileActivity
 * @see PatientAddProblemActivity
 * @see PatientViewProblemsActivity
 * @see PatientSearchActivity
 * @see PhotoSelectorActivity
 */
public class PatientMenuActivity extends AppCompatActivity {
    final int PHOTO_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_menu);

        Button addProblemButton = findViewById(R.id.activityPatientMenu_AddProblemButton);
        Button viewProblemsButton = findViewById(R.id.activityPatientMenu_ViewProblemsButton);
        Button searchProblemsButton = findViewById(R.id.activityPatientMenu_SearchProblemsButton);
        addProblemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPatientAddProblem();
            }
        });
        viewProblemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPatientViewProblems();
            }
        });
        searchProblemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPatientSearchProblems();
            }
        });

        boolean firstCreation = getIntent().getBooleanExtra("FIRST",false);

        if(firstCreation) {
            Intent intent = new Intent(this, PhotoSelectorActivity.class);
            Toast.makeText(this, "Add body photos", Toast.LENGTH_SHORT).show();
            startActivityForResult(intent, PHOTO_REQUEST);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_patient_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuPatientMain_ViewProfile:
                openPatientProfile();
                return true;
            case R.id.menuPatientMain_Logout:
                logoutPatient();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openPatientAddProblem() {
        Intent intent = new Intent(this, PatientAddProblemActivity.class);
        startActivity(intent);
    }

    private void openPatientViewProblems() {
        Intent intent = new Intent(this, PatientViewProblemsActivity.class);
        startActivity(intent);
    }

    private void openPatientSearchProblems() {
        Intent intent = new Intent(this, PatientSearchActivity.class);
        startActivity(intent);
    }

    private void openPatientProfile() {
        Intent intent = new Intent(this, PatientProfileActivity.class);
        startActivity(intent);
    }

    private void logoutPatient() {
        AppStatus.getInstance().setCurrentUser(null);

        Intent intent = new Intent(this, StartScreenActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PHOTO_REQUEST) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this,"Body images added",Toast.LENGTH_SHORT).show();
                ArrayList<Photo> photos = (ArrayList<Photo>)data.getSerializableExtra("PHOTOS");
                PatientController controller = new PatientController(this);
                controller.setBodyPhotos((Patient)AppStatus.getInstance().getCurrentUser(),photos);
            }
        }
    }
}
