package cs.ualberta.ca.medlog.activity;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import cs.ualberta.ca.medlog.R;

/**
 * <p>
 *     Description: <br>
 *         The Activity for the Patient profile viewing screen, this presents the gui for the Patient
 *         to view their profile contact information, and to open screens to view and edit their
 *         body photos or to view a map of all their records with a map location.
 *         Additionally there is an options menu from which the user can edit their contact
 *         information.
 * </p>
 * <p>
 *     Issues: <br>
 *         Transfer to a Body Pictures editing view must be added.
 *         Transfer to a Map of all Records view must be added.
 *         Actual code to read a users details must be added.
 *         Actual code to update a users details must be added.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.1
 * @see PatientMenuActivity
 */
public class PatientProfileActivity extends AppCompatActivity implements TextEditorFragment.OnTextSetListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);
        Button bodyPicturesButton = findViewById(R.id.activityPatientProfile_BodyPicturesButton);
        Button recordsMapButton = findViewById(R.id.activityPatientProfile_RecordsMapButton);
        bodyPicturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBodyPictures();
            }
        });
        recordsMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRecordsMap();
            }
        });

        //TODO Add code to receive a provided patient object and set the related fields to its data.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_patient_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuPatientProfile_EditEmail:
                openEmailEditor();
                return true;
            case R.id.menuPatientProfile_EditPhoneNumber:
                openPhoneNumberEditor();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openEmailEditor() {
        DialogFragment newFragment = new TextEditorFragment();
        Bundle editorData = new Bundle();
        editorData.putInt("argEditorId",0);
        editorData.putString("argHint",getString(R.string.fragmentTextEditor_EmailHint));
        editorData.putInt("argInputType", InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        newFragment.setArguments(editorData);
        newFragment.show(getSupportFragmentManager(),"emailEditor");
    }

    private void openPhoneNumberEditor() {
        DialogFragment newFragment = new TextEditorFragment();
        Bundle editorData = new Bundle();
        editorData.putInt("argEditorId",1);
        editorData.putString("argHint",getString(R.string.fragmentTextEditor_PhoneNumberHint));
        editorData.putInt("argInputType", InputType.TYPE_CLASS_PHONE);
        newFragment.setArguments(editorData);
        newFragment.show(getSupportFragmentManager(),"phoneNumberEditor");
    }


    public void onTextSet(String newText, int editorId) {
        switch(editorId) {
            case 0:
                //TODO Add patient email value updating code.
                setEmailDisplay(newText);
                break;
            case 1:
                //TODO Add patient phone number value updating code.
                setPhoneNumberDisplay(newText);
                break;
        }
    }

    private void setEmailDisplay(String email) {
        TextView titleView = findViewById(R.id.activityPatientProfile_EmailView);
        titleView.setText(email);
    }

    private void setPhoneNumberDisplay(String phoneNumber) {
        TextView descView = findViewById(R.id.activityPatientProfile_PhoneNumberView);
        descView.setText(phoneNumber);
    }

    private void openBodyPictures() {
        //TODO Add code to open a body pictures fragment.
    }

    private void openRecordsMap() {
        //TODO Add code to open a records map fragment.
    }
}
