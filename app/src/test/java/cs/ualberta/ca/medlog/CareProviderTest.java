/*
 * Test class for the CareProvider entity
 *
 * Author: Tem Tamre
 * Contact: ttamre@ualberta.ca
 * Created: October 31, 2018
 */

package cs.ualberta.ca.medlog;

import org.junit.Test;
import java.util.ArrayList;

import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.entity.user.ContactInfo;
import cs.ualberta.ca.medlog.entity.user.Patient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class CareProviderTest {

    @Test
    public void testUsername() {
        CareProvider careProvider = new CareProvider("John Doe");
        assertEquals(careProvider.getUsername(), "John Doe");
    }

    @Test
    public void addPatientTest() {
        ContactInfo info = new ContactInfo("1234567890", "sam@gmail.com");
        Patient patient = new Patient(info, "Sam Smith");

        CareProvider careProvider = new CareProvider("John Doe");
        careProvider.addPatient(patient);
        assertEquals(careProvider.getPatients().get(0), patient);
    }

    @Test
    public void getPatientsTest() {
        CareProvider careProvider = new CareProvider("John Doe");
        assertTrue(careProvider.getPatients().isEmpty());

        ArrayList<Patient> patientList = new ArrayList<>();
        ContactInfo info = new ContactInfo("1234567890", "sam@gmail.com");

        assertEquals(0, careProvider.getPatients().size());

        for (int i = 0; i < 5; i++) {
            Patient patient = new Patient(info, "Sam Smith " + i);
            patientList.add(patient);
            careProvider.addPatient(patient);

            assertEquals(careProvider.getPatients(), patientList);
            assertEquals(i+1, careProvider.getPatients().size());
        }
    }
}
