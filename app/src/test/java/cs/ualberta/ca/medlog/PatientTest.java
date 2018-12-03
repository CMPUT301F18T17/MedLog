package cs.ualberta.ca.medlog;

import cs.ualberta.ca.medlog.entity.Photo;
import cs.ualberta.ca.medlog.entity.user.ContactInfo;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.entity.Problem;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;

/**
 * <p>
 *     Description: <br>
 *         Test class for the Patient entity.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Tem Tamre
 * @version 1.0
 * @see Patient
 */
public class PatientTest {

    @Test
    public void testUsername() {
        ContactInfo info = new ContactInfo("0000000000", "email@email.ca");
        Patient patient = new Patient(info, "Test Patient");

        assertEquals(patient.getUsername(), "Test Patient");
    }

    @Test
    public void getProblemsTest() {
        ContactInfo info = new ContactInfo("0000000000", "email@email.ca");
        Patient patient = new Patient(info, "Test Patient");
        ArrayList<Problem> problemList = new ArrayList<>();

        assertEquals(0, patient.getProblems().size());

        for (int i = 0; i < 5; i++) {
            Problem problem = new Problem("Test Problem " + i, new Date(), "description");
            problemList.add(problem);
            patient.addProblem(problem);

            assertEquals(problemList, patient.getProblems());
            assertEquals(i+1, patient.getProblems().size());
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
    public void getBodyPhotosTest() {
        ContactInfo info = new ContactInfo("0000000000", "email@email.ca");
        Patient patient = new Patient(info, "Test Patient");

        assertEquals(0, patient.getBodyPhotos().size());
        ArrayList<Photo> photos = new ArrayList<Photo>(1);
        Photo photo = new Photo( null);
        photos.add(0,photo);
        patient.setBodyPhotos(photos);

        assertEquals(photo, patient.getBodyPhotos().get(0));

    }

    @Test
    public void addBodyPhotoTest() {
        ContactInfo info = new ContactInfo("0000000000", "email@email.ca");
        Patient patient = new Patient(info, "Test Patient");
        ArrayList<Photo> photos = new ArrayList<Photo>(5);
        for (int i = 0; i < 5; i++) {
            Photo photo = new Photo(null);
            photos.add(i,photo);
            patient.setBodyPhotos(photos);
            assertEquals(photo, patient.getBodyPhotos().get(i));
            assertEquals(i+1, patient.getBodyPhotos().size());
        }
    }

    @Test
    public void getContactInfoTest() {
        ContactInfo info = new ContactInfo("0000000000", "email@email.ca");
        Patient patient = new Patient(info, "Test Patient");

        assertEquals(patient.getContactInfo(), info);
    }

}
