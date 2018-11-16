/**
 *
 * <h1>
 *     DatabaseConnectionTest
 * </h1>
 *
 *  <p>
 *     Description: <br>
 *         Unit testing for the Database class
 *
 * </p>
 *
 * @author Tem Tamre
 * @contact ttamre@ualberta.ca
 * @see cs.ualberta.ca.medlog.helper.Database
 */

package cs.ualberta.ca.medlog;

import android.graphics.Bitmap;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Array;
import java.util.Date;

import cs.ualberta.ca.medlog.activity.PatientLoginActivity;
import cs.ualberta.ca.medlog.entity.BodyLocation;
import cs.ualberta.ca.medlog.entity.MapLocation;
import cs.ualberta.ca.medlog.entity.Photo;
import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.Record;
import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.entity.user.ContactInfo;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.entity.user.User;
import cs.ualberta.ca.medlog.exception.UserNotFoundException;
import cs.ualberta.ca.medlog.helper.Database;
import cs.ualberta.ca.medlog.helper.ElasticSearchController;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DatabaseConnectionTest {

    /* Tests for loading/GET methods */

    @Test
    public void testLoadPatientLocal() {}

    @Test
    public void testLoadProviderLocal() {}

    @Test
    public void testUpdatingPatient(){
        Patient test = new Patient(new ContactInfo("18002672001", "alarm@force.ca"), "testpatient");
        // Try save the Patient
        try {
            boolean result = ElasticSearchController.savePatient(test);
            assertTrue("Could not save user", result);
        }catch(Exception e){
            e.printStackTrace();
            fail("Exception occurred saving user.");
        }

        // Update the user
        test.addProblem(new Problem("Cool Issue", new Date(), "Test Issue"));
        assertEquals(test.getProblems().size(), 1);
        try{
            boolean result = ElasticSearchController.savePatient(test);
            assertTrue("Could not update user.", result);
        }catch(Exception e){
            e.printStackTrace();
            fail("Exception occurred saving user.");
        }

        // Load the patient from the database
        Patient toLoad;
        try {
            toLoad = ElasticSearchController.loadPatient(test.getUsername());
        }catch(Exception e){
            e.printStackTrace();
            fail("Could not load user");
            toLoad = null;
        }
        // Make sure information is still the same
        assertEquals(test.getUsername(), toLoad.getUsername());
        assertEquals(test.getBodyPhotos().size(), test.getBodyPhotos().size());
        assertEquals(test.getContactInfo(), test.getContactInfo());

        // Check if the problem is updated
        assertEquals(test.getProblems().size(), 1);
        assertEquals(test.getProblems().get(0).getDate(), test.getProblems().get(0).getDate());
        assertEquals(test.getProblems().get(0).getRecords().size(), test.getProblems().get(0).getRecords().size());
        assertEquals(test.getProblems().get(0).getTitle(), test.getProblems().get(0).getTitle());
        assertEquals(test.getProblems().get(0).getDescription(), test.getProblems().get(0).getDescription());


        // We are good! Delete the Patient
        try {
            boolean deleteResult = ElasticSearchController.deletePatient(test.getUsername());
            if(!deleteResult){
                fail("Failed to delete the user");
            }
        }catch(Exception e){
            e.printStackTrace();
            fail("Failed to delete the user");
        }
    }


    @Test
    public void testUpdatingProvider(){
        CareProvider test = new CareProvider("testprovider");
        // Try save the Patient
        try {
            boolean result = ElasticSearchController.saveCareProvider(test);
            assertTrue("Could not save user", result);
        }catch(Exception e){
            e.printStackTrace();
            fail("Exception occurred saving user.");
        }

        // Update the user
        test.addPatient(new Patient(new ContactInfo("18002672001", "alarm@force.ca"), "testpatient"));
        try{
            boolean result = ElasticSearchController.saveCareProvider(test);
            assertTrue("Could not update user.", result);
        }catch(Exception e){
            e.printStackTrace();
            fail("Exception occurred saving user.");
        }

        // Load the patient from the database
        CareProvider toLoad;
        try {
            toLoad = ElasticSearchController.loadCareProvider(test.getUsername());
        }catch(Exception e){
            e.printStackTrace();
            fail("Could not load user");
            toLoad = null;
        }
        // Make sure information is still the same
        assertEquals(test.getUsername(), toLoad.getUsername());
        assertEquals(test.getPatients().get(0).getUsername(), "testpatient");


        // We are good! Delete the Care Provider
        try {
            boolean deleteResult = ElasticSearchController.deleteCareProvider(test.getUsername());
            if(!deleteResult){
                fail("Failed to delete the user");
            }
        }catch(Exception e){
            e.printStackTrace();
            fail("Failed to delete the user");
        }
    }

    @Test
    public void testSaveLoadDeleteProviderRemote() {
        CareProvider test = new CareProvider("testprovider");

        // Try save the Care Provider
        try {
            boolean result = ElasticSearchController.saveCareProvider(test);
            assertTrue("Could not save user", result);
        }catch(Exception e){
            e.printStackTrace();
            fail("Exception occurred saving user.");
        }

        // Try load the Care Provider
        CareProvider toLoad;
        try {
            toLoad = ElasticSearchController.loadCareProvider(test.getUsername());
        }catch(Exception e){
            e.printStackTrace();
            fail("Could not load user");
            toLoad = null;
        }
        assertNotNull(toLoad);
        assertEquals(test.getUsername(), toLoad.getUsername());

        // Try and delete the Care Provider
        try {
            boolean deleteResult = ElasticSearchController.deleteCareProvider(test.getUsername());
            if(!deleteResult){
                fail("Failed to delete the user");
            }
        }catch(Exception e){
            e.printStackTrace();
            fail("Failed to delete the user");
        }
    }


    /* Tests for saving/PUT methods */

    @Test
    public void testSavePatientLocal() {}


    /* We need to delete test users from the db, so I combined the test cases into one. */
    @Test
    public void testSaveLoadDeletePatientRemote() {
        Patient test = new Patient(new ContactInfo("18002672001", "alarm@force.com"), "testuser");
        Database db = new Database(null);


        // Try save the patient remotely
        try {
            boolean result = ElasticSearchController.savePatient(test);
            assertTrue("Could not save user", result);
        }catch(Exception e){
            e.printStackTrace();
            fail("Exception occurred saving user.");
        }

        // Try load the patient
        Patient toLoad;
        try {
            toLoad = ElasticSearchController.loadPatient(test.getUsername());
        }catch(Exception e){
            e.printStackTrace();
            fail("Could not load user");
            toLoad = null;
        }
        assertEquals(test.getUsername(), toLoad.getUsername());
        assertEquals(test.getBodyPhotos().size(), test.getBodyPhotos().size());
        assertEquals(test.getContactInfo(), test.getContactInfo());

        // Try and delete the patient
        try {
            boolean deleteResult = ElasticSearchController.deletePatient(test.getUsername());
            if(!deleteResult){
                fail("Failed to delete the user");
            }
        }catch(Exception e){
            e.printStackTrace();
            fail("Failed to delete the user");
        }
    }

    @Test
    public void testSaveProviderLocal() {}

    @Test
    public void testSaveProviderRemote() {}


    /* Tests for other methods */

    /**
     * Used in initializing a database for further testing.
     * @return If the database was successfully initialized.
     */
    private boolean initializeTestDatabaase() throws UserNotFoundException {

        // Initialize Patients
        Patient patient = new Patient(new ContactInfo("123456789", "me@da.ca"), "testuser");
        Patient patient1 = new Patient(new ContactInfo("8796543215", "epic@dab.ca"), "superuser");
        Patient patient2 = new Patient(new ContactInfo("9874587854", "mega@member.ca"), "megauser");
        CareProvider careProvider = new CareProvider("care");
        CareProvider careProvider1 = new CareProvider("supercare");
        CareProvider careProvider2 = new CareProvider("nouser");

        // Initialize Problem
        Problem problem = new Problem("Leg Hurts", new Date(), "Leg does not feel well.");
        Record record = new Record(patient.getUsername());
        record.setTitleComment("Title for Record", "See my leg hurts!");
        record.setBodyLocation(new BodyLocation(new Photo(0, null), 12, 32));
        record.setMapLocation(new MapLocation(18.15525, 126.15));
        patient.addProblem(problem);

        // Initialize problem1
        Problem problem1 = new Problem("Arm Hurts", new Date(), "Arm does hurt.");
        Record record1 = new Record(patient1.getUsername());
        record1.setTitleComment("Title", "Super comment");
        problem1.addRecord(record1);
        patient.addProblem(problem1);


        // Initialize problem2
        Problem problem2 = new Problem("Stomach Hurts", new Date(), "Sad");
        Record record2 = new Record(patient2.getUsername());
        record2.setTitleComment("Title", "Super comment");
        problem2.addRecord(record2);
        patient1.addProblem(problem2);

        careProvider.addPatient(patient);
        careProvider.addPatient(patient1);
        careProvider1.addPatient(patient2);
        // Try and save users
        try {
            if((ElasticSearchController.savePatient(patient)
                    && ElasticSearchController.savePatient(patient1)
                    && ElasticSearchController.savePatient(patient2))
               && (ElasticSearchController.saveCareProvider(careProvider)
                    && ElasticSearchController.saveCareProvider(careProvider1)
                    && ElasticSearchController.saveCareProvider(careProvider2))){
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }


        return false;

    }

    /**
     * Cleaup the test database
     * @return if the operation succeeded.
     */
    private boolean cleanupTestDatabase() throws UserNotFoundException {
        return ElasticSearchController.deletePatient("testuser")
                && ElasticSearchController.deletePatient("superuser")
                && ElasticSearchController.deletePatient("megauser")
                && ElasticSearchController.deleteCareProvider("care")
                && ElasticSearchController.deleteCareProvider("supercare")
                && ElasticSearchController.deleteCareProvider("nouser");
    }

    @Test
    public void testSearchPatients() {
        try {
            assertTrue(initializeTestDatabaase());
        }catch(UserNotFoundException e){
            fail("User was not found in the creation of the database (?)");
        }

        



        try{
            assertTrue(cleanupTestDatabase());
        }catch (UserNotFoundException e){
            fail("User was not found in the deletion of the database.");
        }
    }

    @Test
    public void testSearchProviders() {
        try {
            assertTrue(initializeTestDatabaase());
        }catch(UserNotFoundException e){
            fail("User was not found in the creation of the database (?)");
        }





        try{
            assertTrue(cleanupTestDatabase());
        }catch (UserNotFoundException e){
            fail("User was not found in the deletion of the database.");
        }
    }

}
