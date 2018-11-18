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
import cs.ualberta.ca.medlog.singleton.CurrentUser;

public class PatientController {


    private Context context;
    private Database database;
    private Patient patient;

    public PatientController(Context ctx, Patient patient){
        context=ctx;
        database = new Database(context);
        this.patient = patient;
    }

    public void addProblem(Problem problem){
        patient.addProblem(problem);
        try{
            database.updatePatient(patient);
        }catch(Exception ignore){

        }
    }

    public ArrayList<Problem> getProblems() {
        return patient.getProblems();
    }

    public void setTitle(int problemIndex,String newText) {
        patient.setTitle(problemIndex, newText);
        try{
            database.updatePatient(patient);
        }catch(Exception ignore){

        }

    }
    public void setDesc(int problemIndex,String newText) {
        patient.setDescription(problemIndex, newText);
        try{
            database.updatePatient(patient);
        }catch(Exception ignore){

        }
    }

    public void setDate(int problemIndex, Calendar cal) {
        Date date;
        date = cal.getTime();
        patient.setDate(problemIndex, date);
        try{
            database.updatePatient(patient);
        }catch(Exception ignore){

        }
    }

    public void deleteProblem(int problemIndex){
        patient.deleteProblem(problemIndex);
        try{
            database.updatePatient(patient);
        }catch(Exception ignore){

        }
    }

}
