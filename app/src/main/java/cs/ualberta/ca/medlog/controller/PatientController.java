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
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.exception.UserNotFoundException;
import cs.ualberta.ca.medlog.helper.Database;

public class PatientController {


    private Context context;
    private Database database;

    public PatientController(Context ctx){
        context=ctx;
        database = new Database(context);
    }

    public void addProblem(Problem problem, String username){
        // Load patient, add problem, save patient
        Patient patient=database.loadPatient(username);
        patient.addProblem(problem);
        database.savePatient(patient);
    }

    public ArrayList<Problem> getProblems(String username) {
        Patient patient=database.loadPatient(username);
        return patient.getProblems();

    }

    public void setTitle(String username,int problemIndex,String newText) {
        Patient patient=database.loadPatient(username);
        patient.setTitle(problemIndex,newText);
        database.savePatient(patient);

    }
    public void setDesc(String username,int problemIndex,String newText) {
        Patient patient=database.loadPatient(username);
        patient.setDescription(problemIndex,newText);
        database.savePatient(patient);
    }

    public void setDate(String username, int problemIndex, Calendar cal) {
        Patient patient=database.loadPatient(username);
        Date date;
        date=cal.getTime();
        patient.setDate(problemIndex,date);
        database.savePatient(patient);
    }

    public void deleteProblem(String username, int problemIndex){
        Patient patient=database.loadPatient(username);
        patient.deleteProblem(problemIndex);
        database.savePatient(patient);
    }

}
