package cs.ualberta.ca.medlog.activity;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.exception.EncryptionException;
import cs.ualberta.ca.medlog.helper.Encryption;
import cs.ualberta.ca.medlog.singleton.AppStatus;

/**
 * <p>
 *     Description: <br>
 *         The Activity for viewing a registration code for the given patient which can register
 *         them on another phone.
 *
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * <p>
 *     Resources: <br>
 *         Robertas Uldukis, Response to StackOverflow question "How to read value from string.xml in android?"
 *         https://stackoverflow.com/a/20075784
 *         https://stackoverflow.com/users/2343261/robertas-uldukis
 *         Answered 2013-11-09, accessed 2018-11-30
 *
 * </p>
 *
 * @author Tyler Gobran, Tem Tamre
 * @version 1.0
 * @see PatientProfileActivity
 */
public class PatientViewRegisterCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_register_code);

        TextView registrationCode = findViewById(R.id.activityViewRegisterCode_CodeView);
        byte[] data = AppStatus.getInstance().getCurrentUser().getUsername().getBytes();

        try {
            String encryptionKey = Resources.getSystem().getString(R.string.EncryptionKey);
            String code = Encryption.encryptData(encryptionKey, data);
            registrationCode.setText(code.substring(0,code.length()-3));
        } catch (EncryptionException e) {
            Toast.makeText(this,R.string.activityPatientViewRegisterCode_FailedGeneration,Toast.LENGTH_SHORT).show();
        }
    }
}
