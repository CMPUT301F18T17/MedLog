package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cs.ualberta.ca.medlog.R;

/**
 * <p>
 *     Description: <br>
 *         The Activity for the Care Provider login screen, this presents the gui for a Provider
 *         to enter their username and login, or to proceed to a sign up screen.
 * </p>
 * <p>
 *     Issues: <br>
 *         Connection to a Care Provider controller is required to validate their username.
 *         Transfer to a Care Provider Main Menu must be added.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.1
 * @see StartScreenActivity
 * @see ProviderRegistrationActivity
 */
public class ProviderLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_login);
        Button loginButton = findViewById(R.id.activityProviderLogin_LoginButton);
        Button registrationButton = findViewById(R.id.activityProviderLogin_RegisterButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptProviderLogin();
            }
        });
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProviderRegistration();
            }
        });
    }

    private void attemptProviderLogin() {
        //TODO Connect to a Care Provider controller to check if the Provider exists

        //TODO Add transfer to Care Provider Main Menu Activity
    }

    private void openProviderRegistration() {
        Intent intent = new Intent(this, ProviderRegistrationActivity.class);
        startActivity(intent);
    }
}
