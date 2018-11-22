/**
 *
 * <h1>
 *     CareProvider
 * </h1>
 *
 *  <p>
 *     Description: <br>
 *         This class represents a CareProvider in the application.
 *
 * </p>
 *
 * @author Thomas Roskewich
 * @contact roskewic@ualberta.ca
 * @see cs.ualberta.ca.medlog.entity.user.User
 */
package cs.ualberta.ca.medlog.entity.user;

import java.util.ArrayList;

public class CareProvider extends User {

    private ArrayList<Patient> patients = new ArrayList<>();

    public CareProvider(String username){
        super(username);
    }

    /**
     * <p>Add a patient to the current care provider.</p>
     * @param newPatient The patient to add.
     */
    public void addPatient(Patient newPatient){
        patients.add(newPatient);
    }

    /**
     * <p>Get the current patients.</p>
     * @return The current patients.
     */
    public ArrayList<Patient> getPatients(){
        return patients;
    }
}
