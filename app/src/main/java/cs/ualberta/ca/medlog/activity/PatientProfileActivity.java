package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import java.util.ArrayList;

import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.controller.PatientController;
import cs.ualberta.ca.medlog.entity.MapLocation;
import cs.ualberta.ca.medlog.entity.Photo;
import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.Record;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.singleton.AppStatus;

/**
 * <p>
 *     Description: <br>
 *         The patient profile viewing screen activity for the Application, this presents the gui
 *         for the patient to view their username and contact information. From here they can proceed
 *         to screens to add additional body photos or ot view a map of all their records with a
 *         map location.
 *         An options menu is also present which allows patients to edit their contact information
 *         as well as proceed to a screen where they can see to add another phone as a possible
 *         location to view the Patient's account.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.1
 * @see PatientMenuActivity
 * @see ViewMapLocationActivity
 * @see PhotoSelectorActivity
 * @see TextEditorFragment
 * @see PatientViewRegisterCodeActivity
 */
public class PatientProfileActivity extends AppCompatActivity implements TextEditorFragment.OnTextSetListener {
    final int PHOTO_REQUEST = 1;

    private Patient patient;

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

        patient = (Patient)AppStatus.getInstance().getCurrentUser();

        TextView usernameView = findViewById(R.id.activityPatientProfile_UsernameView);
        usernameView.setText(patient.getUsername());
        updateEmailDisplay(patient.getContactInfo().getEmail());
        updatePhoneNumberDisplay(patient.getContactInfo().getPhoneNumber());
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
            case R.id.menuPatientProfile_GenerateCode:
                openRegisterCodeView();
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
        editorData.putString("argInitialText",patient.getContactInfo().getEmail());
        editorData.putInt("argMaxLength",50);
        newFragment.setArguments(editorData);
        newFragment.show(getSupportFragmentManager(),"emailEditor");
    }

    private void openPhoneNumberEditor() {
        DialogFragment newFragment = new TextEditorFragment();
        Bundle editorData = new Bundle();
        editorData.putInt("argEditorId",1);
        editorData.putString("argHint",getString(R.string.fragmentTextEditor_PhoneNumberHint));
        editorData.putInt("argInputType", InputType.TYPE_CLASS_PHONE);
        editorData.putString("argInitialText",patient.getContactInfo().getPhoneNumber());
        editorData.putInt("argMaxLength",20);
        newFragment.setArguments(editorData);
        newFragment.show(getSupportFragmentManager(),"phoneNumberEditor");
    }

    private  void openRegisterCodeView() {
        Intent intent = new Intent(this, PatientViewRegisterCodeActivity.class);
        startActivity(intent);
    }

    public void onTextSet(String newText, int editorId) {
        PatientController controller = new PatientController(this);
        switch (editorId) {
            case 0:
                if (newText.isEmpty()) {
                    Toast.makeText(this,R.string.activityPatientProfile_NoEmail, Toast.LENGTH_SHORT).show();
                    break;
                }
                controller.setEmail(patient,newText);
                Toast.makeText(this, R.string.activityPatientProfile_EmailSet, Toast.LENGTH_SHORT).show();
                updateEmailDisplay(newText);
                break;
            case 1:
                if (newText.isEmpty()) {
                    Toast.makeText(this, R.string.activityPatientProfile_NoPhone, Toast.LENGTH_SHORT).show();
                    break;
                }
                controller.setPhoneNumber(patient,newText);
                Toast.makeText(this, R.string.activityPatientProfile_PhoneSet, Toast.LENGTH_SHORT).show();
                updatePhoneNumberDisplay(newText);
                break;
        }
    }

    private void updateEmailDisplay(String email) {
        TextView titleView = findViewById(R.id.activityPatientProfile_EmailView);
        titleView.setText(email);
    }

    private void updatePhoneNumberDisplay(String phoneNumber) {
        TextView descView = findViewById(R.id.activityPatientProfile_PhoneNumberView);
        descView.setText(phoneNumber);
    }

    private void openBodyPictures() {
        Intent intent = new Intent(this, PhotoSelectorActivity.class);
        intent.putExtra("PHOTOS",patient.getBodyPhotos());
        startActivityForResult(intent, PHOTO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PHOTO_REQUEST) {
                ArrayList<Photo> photos = (ArrayList<Photo>)data.getSerializableExtra("PHOTOS");
                PatientController controller = new PatientController(this);
                controller.setBodyPhotos((Patient)AppStatus.getInstance().getCurrentUser(),photos);
                Toast.makeText(this,R.string.activityPatientProfile_BodyPhotosAdded,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openRecordsMap() {
        Intent intent = new Intent(this, ViewAllMapLocationsActivity.class);
        startActivity(intent);
    }
}
