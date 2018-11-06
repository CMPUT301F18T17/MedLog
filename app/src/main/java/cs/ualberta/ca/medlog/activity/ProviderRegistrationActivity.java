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
 *         The Activity for the Care Provider sign up screen, this presents the gui for a Patient
 *         to enter their new accounts username and proceed to create the account.
 * </p>
 * <p>
 *     Issues: <br>
 *         Connection to a Care Provider controller is required to validate the username isn't used.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.1
 * @see ProviderLoginActivity
 * @see ProviderMenuActivity
 */
public class ProviderRegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_registration);
        Button completeButton = findViewById(R.id.activityProviderRegistration_CompleteButton);
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptProviderRegistration();
            }
        });
    }

    private void attemptProviderRegistration() {
        //TODO Connect to a Care Provider controller to check if the Provider already exists

        Intent intent = new Intent(this, ProviderMenuActivity.class);
        startActivity(intent);
    }
}
