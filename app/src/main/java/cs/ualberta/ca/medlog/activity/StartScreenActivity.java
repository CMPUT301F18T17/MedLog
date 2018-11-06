package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cs.ualberta.ca.medlog.R;

/**
 * <p>
 *     Description: <br>
 *         The Activity for the initial start screen, this presents the gui for a user to proceed
 *         to Patient or Care Provider login/sign up.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.1
 * @see StartScreenActivity
 */
public class StartScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        Button patientButton = findViewById(R.id.activityStartScreen_PatientButton);
        Button providerButton = findViewById(R.id.activityStartScreen_ProviderButton);
        patientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPatientLogin();
            }
        });
        providerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProviderLogin();
            }
        });
    }

    private void openPatientLogin() {
        Intent intent = new Intent(this, PatientLoginActivity.class);
        startActivity(intent);
    }

    private void openProviderLogin() {
        Intent intent = new Intent(this, ProviderLoginActivity.class);
        startActivity(intent);
    }
}
