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
import static org.junit.Assert.assertNull;


public class CareProviderTest {

    @Test
    public void testUsername() {
        CareProvider careProvider = new CareProvider("Test Provider");
        assertEquals(careProvider.getUsername(), "Test Provider");
    }

    @Test
    public void addPatientTest() {
        ContactInfo info = new ContactInfo("0000000000", "email@email.ca");
        Patient patient = new Patient(info, "Test Patient");

        CareProvider careProvider = new CareProvider("Test Provider");
        careProvider.addPatient(patient);
        assertEquals(careProvider.getPatients().get(0), patient);
    }

    @Test
    public void getPatientsTest() {
        CareProvider careProvider = new CareProvider("Test Provider");
        assertNull(careProvider.getPatients());

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
