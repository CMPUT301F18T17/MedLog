package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.controller.SyncController;
import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.helper.Database;
import cs.ualberta.ca.medlog.singleton.AppStatus;

/**
 * <p>
 *     Description: <br>
 *         The care provider login screen activity for the Application, this presents the gui for
 *         a Care Provider to enter their username and proceed to login, or to move to a provider
 *         registration screen if they don't have an account.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.0
 * @see StartScreenActivity
 * @see ProviderMenuActivity
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
                performProviderLogin();
            }
        });
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProviderRegistration();
            }
        });
    }

    private void performProviderLogin() {
        EditText usernameField = findViewById(R.id.activityProviderLogin_UsernameEditText);
        String username = usernameField.getText().toString();

        if (username.isEmpty()) {
            Toast.makeText(this,R.string.activityProviderLogin_NoUsername,Toast.LENGTH_SHORT).show();
            return;
        }

        SyncController sc = new SyncController(this);
        try{
            sc.syncCareProvider(username);
        }catch(Exception e){
            Toast.makeText(this,R.string.activityProviderLogin_NoServer,Toast.LENGTH_SHORT).show();
            return;
        }


        Database db = new Database(this);
        CareProvider toLogin;
        try{
            toLogin = db.loadProvider(username);
            toLogin = sc.updateCareProviderPatients(toLogin);
        } catch(Exception e) {
            Toast.makeText(this, R.string.activityProviderLogin_NoCareProvider, Toast.LENGTH_SHORT).show();
            return;
        }

        if (toLogin != null) {
            AppStatus.getInstance().setCurrentUser(toLogin);
            Intent intent = new Intent(this, ProviderMenuActivity.class);
            Toast.makeText(this,R.string.activityProviderLogin_LoggedIn,Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
        else {
            Toast.makeText(this,R.string.activityProviderLogin_FailedLogin,Toast.LENGTH_SHORT).show();
        }
    }

    private void openProviderRegistration() {
        Intent intent = new Intent(this, ProviderRegistrationActivity.class);
        startActivity(intent);
    }
}
