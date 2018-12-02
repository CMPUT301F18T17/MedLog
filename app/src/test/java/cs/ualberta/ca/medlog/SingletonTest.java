package cs.ualberta.ca.medlog;

import org.junit.Test;

import java.util.Date;

import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.Record;
import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.entity.user.ContactInfo;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.singleton.AppStatus;

import static org.junit.Assert.*;

/**
 * <p>
 *     Description: <br>
 *         Test class for the AppStatus singleton.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Thomas Roskewich, Kyle Androschuk, Calvin Chomyc
 * @version 1.0
 */

public class SingletonTest {

    @Test
    public void testGetInstance(){
        assertNotNull(AppStatus.getInstance());
    }

    @Test
    public void testCurrentUser(){
        assertNull(AppStatus.getInstance().getCurrentUser());
        Patient p = new Patient(new ContactInfo("1234567894", "me@md.ca"), "super");
        AppStatus.getInstance().setCurrentUser(p);
        Patient k = (Patient)AppStatus.getInstance().getCurrentUser();
        assertEquals(p, k);

        CareProvider cp = new CareProvider("super");
        AppStatus.getInstance().setCurrentUser(cp);
        CareProvider ck = (CareProvider)AppStatus.getInstance().getCurrentUser();
        assertEquals(cp, ck);
    }

    @Test
    public void testViewedPatient(){
        assertNull(AppStatus.getInstance().getViewedPatient());
        Patient p = new Patient(new ContactInfo("1234567894", "me@md.ca"), "super");
        AppStatus.getInstance().setViewedPatient(p);
        Patient k = (Patient)AppStatus.getInstance().getViewedPatient();
        assertEquals(p, k);
    }

    @Test
    public void testViewedProblem(){
        assertNull(AppStatus.getInstance().getViewedProblem());

        String title = "ProblemTitleString";
        Date date = new Date();
        String description = "This is a problem description.";
        Problem testProblem = new Problem(title, date, description);

        AppStatus.getInstance().setViewedProblem(testProblem);
        Problem retrievedProblem = AppStatus.getInstance().getViewedProblem();
        assertEquals(testProblem, retrievedProblem);
    }

    @Test
    public void testViewedRecord(){
        assertNull(AppStatus.getInstance().getViewedRecord());
        Record testRecord = new Record("John Does");
        AppStatus.getInstance().setViewedRecord(testRecord);
        Record retrievedRecord = AppStatus.getInstance().getViewedRecord();
        assertEquals(testRecord, retrievedRecord);
    }
}
