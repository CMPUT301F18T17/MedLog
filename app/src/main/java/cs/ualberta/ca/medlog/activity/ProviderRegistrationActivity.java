package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.net.ConnectException;
import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.helper.Database;
import cs.ualberta.ca.medlog.singleton.AppStatus;

/**
 * <p>
 *     Description: <br>
 *         The care provider sign up screen activity for the Application, this presents the gui for
 *         a new Provider to sign up by entering a username. With this information added they can
 *         then be registered.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.0
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
        EditText usernameField = findViewById(R.id.activityProviderRegistration_UsernameEditText);
        String username = usernameField.getText().toString();
        if (username.isEmpty()) {
            Toast.makeText(this,"No username entered",Toast.LENGTH_SHORT).show();
            return;
        }

        boolean usernameAvailable;
        Database db = new Database(this);
        try {
            usernameAvailable = db.usernameAvailable(username);
        } catch(ConnectException e){
            Toast.makeText(this,"Could not connect",Toast.LENGTH_SHORT).show();
            return;
        }

        if (!usernameAvailable) {
            Toast.makeText(this,"Username already used",Toast.LENGTH_SHORT).show();
            return;
        }

        CareProvider toSignUp = new CareProvider(username);
        if (db.saveProvider(toSignUp)) {
            AppStatus.getInstance().setCurrentUser(toSignUp);
            Intent intent = new Intent(this, ProviderMenuActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this,"Failed to register care provider",Toast.LENGTH_SHORT).show();
        }
    }
}
