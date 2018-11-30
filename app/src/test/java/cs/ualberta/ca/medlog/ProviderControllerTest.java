package cs.ualberta.ca.medlog;

import android.content.Context;

import org.junit.Test;

import java.net.ConnectException;

import cs.ualberta.ca.medlog.controller.ProviderController;
import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.entity.user.ContactInfo;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.exception.UserNotFoundException;
import cs.ualberta.ca.medlog.helper.Database;
import static junit.framework.TestCase.assertEquals;
public class ProviderControllerTest {

    @Test
    public void testaddPatient() {
        ProviderController controller = new ProviderController(null);
        CareProvider provider = new CareProvider("Test Provider");
        ContactInfo info = new ContactInfo("0000000000", "email@email.ca");
        Patient patient= new Patient(info,"Test Patient");
        controller.addPatient(provider,patient);
        Database database=controller.getDatabase();
        try {
            provider=database.loadProvider("Test Provider");
        }catch(UserNotFoundException | ConnectException e){}

        assertEquals(patient,provider.getPatients().get(0));


    }


}
