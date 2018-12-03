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

    /**
     * Tests if a Patient's username is successfully returned.
     */
    @Test
    public void testUsername() {
        ContactInfo info = new ContactInfo("0000000000", "email@email.ca");
        Patient patient = new Patient(info, "Test Patient");

        assertEquals(patient.getUsername(), "Test Patient");
    }

    /**
     * Tests if multiple Problems are successfully added to a Patient and returned.
     */
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

    /**
     * Tests if a single Problem is successfully added to a Patient and returned.
     */
    @Test
    public void addProblemTest() {
        ContactInfo info = new ContactInfo("0000000000", "email@email.ca");
        Patient patient = new Patient(info, "Test Patient");
        Problem problem = new Problem("Test Problem", new Date(), "description");

        assertEquals(0, patient.getProblems().size());

        patient.addProblem(problem);
        assertEquals(patient.getProblems().get(0), problem);
    }

    /**
     * Tests if Problems are successfully deleted from a Patient.
     */
    @Test
    public void deleteProblemTest() {
        ContactInfo info = new ContactInfo("0000000000", "email@email.ca");
        Patient patient = new Patient(info, "Test Patient");
        Problem problem1 = new Problem("Test Problem 1", new Date(), "description 1");
        Problem problem2 = new Problem("Test Problem 2", new Date(), "description 2");
        Problem problem3 = new Problem("Test Problem 3", new Date(), "description 3");

        patient.addProblem(problem1);
        patient.addProblem(problem2);
        patient.addProblem(problem3);
        assertEquals(3, patient.getProblems().size());

        patient.deleteProblem(1);
        assertEquals(2, patient.getProblems().size());
        assertEquals(patient.getProblems().get(0), problem1);
        assertEquals(patient.getProblems().get(1), problem3);

        patient.deleteProblem(1);
        assertEquals(1, patient.getProblems().size());
        assertEquals(patient.getProblems().get(0), problem1);

        patient.deleteProblem(0);
        assertEquals(0, patient.getProblems().size());
    }

    /**
     * Tests if multiple body photos are successfully added to a Patient and returned.
     */
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

    /**
     * Tests if a single body photo is successfully added to a Patient and returned.
     */
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

    /**
     * Tests if a Patient's ContactInfo is successfully returned.
     */
    @Test
    public void getContactInfoTest() {
        ContactInfo info = new ContactInfo("0000000000", "email@email.ca");
        Patient patient = new Patient(info, "Test Patient");

        assertEquals(patient.getContactInfo(), info);
    }

}
