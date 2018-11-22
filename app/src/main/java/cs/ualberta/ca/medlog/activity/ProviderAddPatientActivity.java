package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;
import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.controller.ProviderController;
import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.helper.Database;
import cs.ualberta.ca.medlog.singleton.AppStatus;

/**
 * <p>
 *     Description: <br>
 *         The Activity for the Care Provider add patient screen, this presents the gui for a
 *         Provider to add a patient they are assigned via their username. From there the Provider
 *         is prompted on further navigation to either add another patient, return to the main menu
 *         or to view their newly added patient..
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.0
 * @see ProviderMenuActivity
 * @see ProviderPatientProfileActivity
 * @see PopupWindow
 */
public class ProviderAddPatientActivity extends AppCompatActivity {
    private PopupWindow postAddNavigationPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_add_patient);
        Button addButton = findViewById(R.id.activityProviderAddPatient_AddButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalizeAddingPatient();
            }
        });
    }

    private void finalizeAddingPatient() {
        EditText usernameField = findViewById(R.id.activityProviderAddPatient_TitleEditText);
        String username = usernameField.getText().toString();

        if (username.isEmpty()) {
            Toast.makeText(this,"No username entered",Toast.LENGTH_SHORT).show();
            return;
        }

        Database db = new Database(this);
        Patient newPatient;
        try {
            newPatient = db.loadPatient(username);
        } catch(Exception e){
            Toast.makeText(this,"Invalid patient username",Toast.LENGTH_SHORT).show();
            return;
        }

        if (newPatient == null) {
            Toast.makeText(this,"Failed to find patient",Toast.LENGTH_SHORT).show();
            return;
        }

        CareProvider provider = (CareProvider)AppStatus.getInstance().getCurrentUser();
        if (provider.getPatients().contains(newPatient)) {
            Toast.makeText(this,"Patient already added",Toast.LENGTH_SHORT).show();
            return;
        }

        ProviderController controller = new ProviderController(this);
        controller.addPatient(provider,newPatient);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View postAddNavigationLayout = inflater.inflate(R.layout.fragment_add_patient_navigation,null);
        Button viewButton = postAddNavigationLayout.findViewById(R.id.fragmentAddPatientNavigation_ViewButton);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openViewPatient(newPatient);
            }
        });
        Button addButton = postAddNavigationLayout.findViewById(R.id.fragmentAddPatientNavigation_AddButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAnotherPatient();
            }
        });
        Button menuButton = postAddNavigationLayout.findViewById(R.id.fragmentAddPatientNavigation_MenuButton);
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

    private void openViewPatient(Patient newPatient) {
        Intent intent = new Intent(this, ProviderPatientProfileActivity.class);
        AppStatus.getInstance().setViewedPatient(newPatient);
        startActivity(intent);
    }

    private void addAnotherPatient() {
        postAddNavigationPopup.dismiss();
    }

    private void openMainMenu() {
        finish();
    }
}
