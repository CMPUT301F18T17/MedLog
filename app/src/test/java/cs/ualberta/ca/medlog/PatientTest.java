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
    public void getProblemsTest() {
        ContactInfo info = new ContactInfo("0000000000", "email@email.ca");
        Patient patient = new Patient(info, "Test Patient");
        ArrayList<Problem> problemList = new ArrayList<Problem>();

        assertEquals(0, patient.getProblems().size());

        for (int i = 0; i < 5; i++) {
            Problem problem = new Problem("Test Problem " + i, new Date(), "description");
            problemList.add(problem);
            patient.addProblem(problem);

            assertEquals(problemList, patient.getProblems());
        }

    }

    @Test
    public void addProblemTest() {
        ContactInfo info = new ContactInfo("0000000000", "email@email.ca");
        Patient patient = new Patient(info, "Test Patient");
        Problem problem = new Problem("Test Problem", new Date(), "description");

        assertEquals(0, patient.getProblems().size());

        patient.addProblem(problem);
        assertEquals(patient.getProblems().get(0), problem);
    }

    @Test
    public void addBodyPhotoTest() {
        ContactInfo info = new ContactInfo("0000000000", "email@email.ca");
        Patient patient = new Patient(info, "Test Patient");

        for (int i = 0; i < 5; i++) {
            Photo photo = new Photo(100 + i, null);
            patient.addBodyPhoto(photo);
            assertEquals(photo, patient.getBodyPhotos().get(i));
        }
    }

    @Test
    public void getBodyPhotosTest() {
        ContactInfo info = new ContactInfo("0000000000", "email@email.ca");
        Patient patient = new Patient(info, "Test Patient");

        assertEquals(0, patient.getBodyPhotos().size());

        Photo photo = new Photo(00000000, null);
        patient.addBodyPhoto(photo);

        assertEquals(photo, patient.getBodyPhotos().get(0));

    }

    @Test
    public void getContactInfoTest() {
        ContactInfo info = new ContactInfo("0000000000", "email@email.ca");
        Patient patient = new Patient(info, "Test Patient");

        assertEquals(patient.getContactInfo(), info);
    }
}
