package cs.ualberta.ca.medlog.entity.user;

import java.io.Serializable;
import java.util.ArrayList;

public class CareProvider extends User implements Serializable {

    ArrayList<Patient> patients = new ArrayList<>();

    public CareProvider(String username){
        super(username);
    }

    public void addPatient(Patient newPatient){
        patients.add(newPatient);
    }

    public ArrayList<Patient> getPatients(){
        return patients;
    }
}
