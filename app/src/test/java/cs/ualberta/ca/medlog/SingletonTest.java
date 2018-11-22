package cs.ualberta.ca.medlog;

import org.junit.Test;

import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.entity.user.ContactInfo;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.singleton.AppStatus;

import static junit.framework.TestCase.*;

public class SingletonTest {

    @Test
    public void testCurrentUser(){
        Patient p = new Patient(new ContactInfo("1234567894", "me@md.ca"), "super");
        AppStatus.getInstance().setCurrentUser(p);
        Patient k = (Patient)AppStatus.getInstance().getCurrentUser();
        assertEquals(p, k);

        CareProvider cp = new CareProvider("super");
        AppStatus.getInstance().setCurrentUser(p);
        CareProvider ck = (CareProvider)AppStatus.getInstance().getCurrentUser();
        assertEquals(cp, ck);
    }
}
