package cs.ualberta.ca.medlog.controller;

import android.content.Context;

import java.util.ArrayList;

import cs.ualberta.ca.medlog.entity.Photo;
import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.user.Patient;
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
 * @author Thomas Roskewich
 * @version 1.1
 * @see Patient
 */
public class PatientController {
    private Database database;

    public PatientController(Context ctx) {
        database = new Database(ctx);
    }

    /**
     * The controller method for the gui to set Body Photos for the Patient.
     * @param patient The patient who is having body photos added.
     * @param newPhotos The new photos which are set.
     */
    public void setBodyPhotos(Patient patient, ArrayList<Photo> newPhotos) {
        try {
            patient.setBodyPhotos(newPhotos);
            database.updatePatient(patient);
        } catch (Exception ignore) {
        }
    }

    /**
     * The controller method for the gui to request that the given patient's email is changed.
     * @param patient The patient who's email is being changed.
     * @param newEmail The new patient email to be set.
     */
    public void setEmail(Patient patient, String newEmail) {
        try {
            patient.getContactInfo().setEmail(newEmail);
            database.updatePatient(patient);
        } catch (Exception ignore) {

        }
    }

    /**
     * The controller method for the gui to request that the given patient's phone number is changed.
     * @param patient The patient who's phone number is being changed.
     * @param newPhoneNumber The new patient phone number to be set.
     */
    public void setPhoneNumber(Patient patient, String newPhoneNumber) {
        try {
            patient.getContactInfo().setPhoneNumber(newPhoneNumber);
            database.updatePatient(patient);
        } catch (Exception ignore) {
        }
    }

    /**
     * The controller method for the gui to request a problem be added to the patient's recorded
     * problems.
     * @param patient The patient who is having another problem added.
     * @param newProblem The new problem which is added.
     */
    public void addProblem(Patient patient, Problem newProblem) {
        try {
            patient.addProblem(newProblem);
            database.updatePatient(patient);
        } catch (Exception ignore) {
        }
    }

    /**
     * The controller method for the gui to request that one of the Patient's problems is deleted.
     * @param patient The patient who is having a problem deleted.
     * @param problem The problem which is to be deleted.
     */
    public void deleteProblem(Patient patient, Problem problem) {
        try {
            patient.deleteProblem(problem.getId());
            database.updatePatient(patient);
        } catch (Exception ignore) {
        }
    }

    // Used for testing purposes
    public Database getDatabase(){return database;}
}
