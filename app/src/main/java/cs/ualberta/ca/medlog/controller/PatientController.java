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

/**
 * <p>
 *     Description: <br>
 *         The controller for all changes made to a Patient. This is used by the gui to change
 *         Patient information in the model.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author ?
 * @version 1.0
 * @see Patient
 */
public class PatientController {
    private Database database;

    public PatientController(Context ctx){
        database = new Database(ctx);
    }

    public void setEmail(Patient patient, String newEmail){
        try{
            patient=database.loadPatient(patient.getUsername());
            patient.getContactInfo().setPhoneNumber(newEmail);
            database.savePatient(patient);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setPhoneNumber(Patient patient, String newPhoneNumber){
        try{
            patient=database.loadPatient(patient.getUsername());
            patient.getContactInfo().setPhoneNumber(newPhoneNumber);
            database.savePatient(patient);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addProblem(Patient patient, Problem newProblem){
        try{
            patient=database.loadPatient(patient.getUsername());
            patient.addProblem(newProblem);
            database.savePatient(patient);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void deleteProblem(Patient patient, Problem problem){
        try{
            patient=database.loadPatient(patient.getUsername());
            patient.getProblems().remove(problem);
            database.savePatient(patient);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
