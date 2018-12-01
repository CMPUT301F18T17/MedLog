package cs.ualberta.ca.medlog.entity.user;

import java.util.ArrayList;

/**
 * <p>
 *     Description: <br>
 *         This class represents a CareProvider in the application, providers possess a list of
 *         patients they are assigned to and have getters and setters to access this information.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Thomas Roskewich
 * @version 1.0
 * @see User
 */
public class CareProvider extends User {

    private ArrayList<Patient> patients = new ArrayList<>();

    /**
     * Constructs a new Patient with the given username.
     * @param username The care provider username.
     */
    public CareProvider(String username){
        super(username);
    }

    /**
     * Retrieves all of the patients assigned to the care provider.
     * @return ArrayList of the assigned patients.
     */
    public ArrayList<Patient> getPatients(){
        return patients;
    }

    /**
     * Adds a patient to those assigned to the care provider.
     * @param newPatient The patient to add.
     */
    public void addPatient(Patient newPatient){
        patients.add(newPatient);
    }
}
