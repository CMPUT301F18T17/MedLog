package cs.ualberta.ca.medlog;

import org.junit.Test;

import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.entity.user.ContactInfo;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.singleton.CurrentUser;

import static junit.framework.TestCase.*;

public class SingletonTest {

    @Test
    public void testCurrentUser(){
        Patient p = new Patient(new ContactInfo("1234567894", "me@md.ca"), "super");
        CurrentUser.getInstance().set(p);
        Patient k = CurrentUser.getInstance().getAsPatient();
        assertEquals(p, k);
        try {
            CareProvider cp = CurrentUser.getInstance().getAsProvider();
            fail("Cast should fail since Patient -> Care provider");
        }catch(Exception ignored){ }
    }
}
