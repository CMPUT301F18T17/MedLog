package cs.ualberta.ca.medlog.controller;

import android.content.Context;
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
 * @author ?
 * @version 1.0
 * @see Patient
 */
public class PatientController {
    private Database database;

    public PatientController(Context ctx) {
        database = new Database(ctx);
    }

    public void setEmail(Patient patient, String newEmail) {
        try {
            patient.getContactInfo().setPhoneNumber(newEmail);
            database.updatePatient(patient);
        } catch (Exception ignore) {
        }
    }

    public void setPhoneNumber(Patient patient, String newPhoneNumber) {
        try {
            patient.getContactInfo().setPhoneNumber(newPhoneNumber);
            database.updatePatient(patient);
        } catch (Exception ignore) {
        }
    }

    public void addProblem(Patient patient, Problem newProblem) {
        try {
            patient.addProblem(newProblem);
            database.updatePatient(patient);
        } catch (Exception ignore) {
        }
    }

    public void deleteProblem(Patient patient, Problem problem) {
        try {
            patient.getProblems().remove(problem);
            database.updatePatient(patient);
        } catch (Exception ignore) {
        }
    }
}
