package cs.ualberta.ca.medlog;

import org.junit.Test;
import java.util.ArrayList;

import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.entity.user.ContactInfo;
import cs.ualberta.ca.medlog.entity.user.Patient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * <p>
 *     Description: <br>
 *         Test class for the CareProvider entity.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Tem Tamre, Calvin Chomyc
 * @version 1.0
 * @see CareProvider
 */
public class CareProviderTest {

    /**
     * Tests if a CareProvider's username is successfully set and returned.
     */
    @Test
    public void testUsername() {
        CareProvider careProvider = new CareProvider("Test Patient");
        assertEquals("Test Patient", careProvider.getUsername());
    }

    /**
     * Tests if a single patient is successfully added and returned from a CareProvider.
     */
    @Test
    public void addPatientTest() {
        ContactInfo info = new ContactInfo("0000000000", "email@email.ca");
        Patient patient = new Patient(info, "Test Patient");

        CareProvider careProvider = new CareProvider("Test Provider");
        careProvider.addPatient(patient);

        assertEquals(patient, careProvider.getPatients().get(0));
    }

    /**
     * Tests if all of a CareProvider's patients are successfully set and returned.
     */
    @Test
    public void getPatientsTest() {
        CareProvider careProvider = new CareProvider("Test Provider");
        assertTrue(careProvider.getPatients().isEmpty());

        ArrayList<Patient> patientList = new ArrayList<>();
        ContactInfo info = new ContactInfo("0000000000", "email@email.ca");

        assertEquals(0, careProvider.getPatients().size());

        for (int i = 0; i < 5; i++) {
            Patient patient = new Patient(info, "Test Patient " + i);
            patientList.add(patient);
            careProvider.addPatient(patient);

            assertEquals(careProvider.getPatients(), patientList);
            assertEquals(i+1, careProvider.getPatients().size());
        }
    }
}
