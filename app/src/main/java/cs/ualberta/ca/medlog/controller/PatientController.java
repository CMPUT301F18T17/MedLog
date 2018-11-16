/**
 *
 * <h1>
 *     PatientController
 * </h1>
 *
 *  <p>
 *     Description: <br>
 *         The purpose of this class is to control the patient changes.
 *  </p>
 *
 */


package cs.ualberta.ca.medlog.controller;

import android.content.Context;

import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.helper.Database;

public class PatientController {


    private Context context;

    public PatientController(Context ctx){
        context=ctx;
    }

    public void addProblem(Problem problem, String username){
        // Load patient, add problem, save patient
        Database database = new Database(context);
        Patient patient=database.loadPatient(username);
        patient.addProblem(problem);
        database.savePatient(patient);



    }

}
