package cs.ualberta.ca.medlog.singleton;

import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.Record;
import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.entity.user.User;

/**
 * <p>
 *     Description: <br>
 *         This class stores the current status of the application in a globally accessible
 *         container (singleton). This includes the logged in user as well as the patient being
 *         viewed, problem being viewed and record being viewed if relevant.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Thomas Roskewich
 * @version 1.2
 * @see User
 */
public class AppStatus {
    private static final AppStatus ourInstance = new AppStatus();

    private static User currentUser = null;
    private static Patient viewedPatient = null;
    private static Problem viewedProblem = null;
    private static Record viewedRecord = null;

    public static AppStatus getInstance() {
        return ourInstance;
    }

    private AppStatus() {

    }

    /**
     * Set the current user to the provided user.
     * @param user The user to use.
     */
    public void setCurrentUser(User user){
        currentUser = user;
    }

    /**
     * Get the current user.
     * @return The user.
     */
    public User getCurrentUser(){
        return currentUser;
    }

    /**
     * Set the viewed patient to the provided patient.
     * @param patient The patient to use.
     */
    public void setViewedPatient(Patient patient) {
        viewedPatient = patient;
    }

    /**
     * Get the viewed patient.
     * @return The patient.
     */
    public Patient getViewedPatient(){
        return viewedPatient;
    }

    /**
     * Set the viewed problem to the provided problem.
     * @param problem The problem to use.
     */
    public void setViewedProblem(Problem problem){
        viewedProblem = problem;
    }

    /**
     * Get the viewed problem.
     * @return The problem.
     */
    public Problem getViewedProblem(){
        return viewedProblem;
    }

    /**
     * Set the viewed record to the provided record.
     * @param record The record to use.
     */
    public void setViewedRecord(Record record){
        viewedRecord = record;
    }

    /**
     * Get the viewed record.
     * @return The record.
     */
    public Record getViewedRecord(){
        return viewedRecord;
    }
}
