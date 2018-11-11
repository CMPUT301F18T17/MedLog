package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cs.ualberta.ca.medlog.R;

/**
 * <p>
 *     Description: <br>
 *         The care provider sign up screen activity for the Application, this presents the gui for
 *         a new Provider to sign up by entering a username. With this information added they can
 *         then be registered.
 * </p>
 * <p>
 *     Issues: <br>
 *         Need a Provider controller to test if a username is valid
 *         Need a Provider/System controller to add the new Provider to the system.
 *         Need a Provider/System controller to set the input username to be the logged in User.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.2
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
                performProviderRegistration();
            }
        });
    }

    private void performProviderRegistration() {
        EditText usernameField = findViewById(R.id.activityProviderLogin_UsernameEditText);
        String username = usernameField.getText().toString();
        if (username.isEmpty()) {
            Toast.makeText(this,"No username entered",Toast.LENGTH_SHORT).show();
            return;
        }

        boolean usernameAvailable = true;    //TODO Set to false once controller added.
        //TODO Check this username using a Care Provider Controller, if free change the boolean
        if (!usernameAvailable) {
            Toast.makeText(this,"Username already used",Toast.LENGTH_SHORT).show();
            return;
        }

        //TODO Add controller call to add the given Care Provider to the system

        //TODO Add code contacting the system to inform it that the given Provider is logged in

        Intent intent = new Intent(this, PatientMenuActivity.class);
        startActivity(intent);
    }
}
