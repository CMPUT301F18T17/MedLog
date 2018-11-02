/*
 * Test class for the CareProvider entity
 *
 * Author: Tem Tamre
 * Contact: ttamre@ualberta.ca
 * Created: October 31, 2018
 */

package cs.ualberta.ca.medlog;

import cs.ualberta.ca.medlog.entity.Photo;
import cs.ualberta.ca.medlog.entity.user.ContactInfo;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.entity.Problem;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;


public class PatientTest {

    @Test
    public void testUsername() {
        ContactInfo info = new ContactInfo("1234567890", "sam@gmail.com");
        Patient patient = new Patient(info, "Sam Smith");
        assertEquals(patient.getUsername(), "Sam Smith");
    }

    @Test
    public void getProblemsTest() {
        ContactInfo info = new ContactInfo("1234567890", "sam@gmail.com");
        Patient patient = new Patient(info, "Sam Smith");
        ArrayList<Problem> problemList = new ArrayList<Problem>();

        assertEquals(0, patient.getProblems().size());

        for (int i = 0; i < 5; i++) {
            Problem problem = new Problem("Test Problem " + i, new Date(), "Description of test problem.");
            problemList.add(problem);
            patient.addProblem(problem);
            assertEquals(problemList, patient.getProblems());
            assertEquals(i+1, patient.getProblems().size());
        }
    }

    @Test
    public void addProblemTest() {
        ContactInfo info = new ContactInfo("1234567890", "sam@gmail.com");
        Patient patient = new Patient(info, "Sam Smith");
        Problem problem = new Problem("Test Problem", new Date(), "Description of test problem.");

        assertEquals(0, patient.getProblems().size());

        patient.addProblem(problem);
        assertEquals(patient.getProblems().get(0), problem);
    }

    @Test
    public void getBodyPhotosTest() {
        ContactInfo info = new ContactInfo("1234567890", "sam@gmail.com");
        Patient patient = new Patient(info, "Sam Smith");

        assertEquals(0, patient.getBodyPhotos().size());

        Photo photo = new Photo(1, null);
        patient.addBodyPhoto(photo);

        assertEquals(photo, patient.getBodyPhotos().get(0));

    }

    @Test
    public void addBodyPhotoTest() {
        ContactInfo info = new ContactInfo("1234567890", "sam@gmail.com");
        Patient patient = new Patient(info, "Sam Smith");

        for (int i = 0; i < 5; i++) {
            Photo photo = new Photo(100 + i, null);
            patient.addBodyPhoto(photo);
            assertEquals(photo, patient.getBodyPhotos().get(i));
            assertEquals(i+1, patient.getBodyPhotos().size());
        }
    }

    @Test
    public void getContactInfoTest() {
        ContactInfo info = new ContactInfo("1234567890", "sam@gmail.com");
        Patient patient = new Patient(info, "Sam Smith");

        assertEquals(patient.getContactInfo(), info);
    }
}
