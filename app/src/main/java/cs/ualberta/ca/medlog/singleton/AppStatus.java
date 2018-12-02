package cs.ualberta.ca.medlog.singleton;

import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.Record;
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
 * @author Thomas Roskewich, Tyler Gobran
 * @version 1.2
 * @see User
 */
public class AppStatus {
    private static final AppStatus ourInstance = new AppStatus();

    private static User currentUser = null;
    private static Patient viewedPatient = null;
    private static Problem viewedProblem = null;
    private static Record viewedRecord = null;

    private AppStatus() {
    }

    /**
     * Retrieves an instance of the AppStatus object.
     * @return The AppStatus object.
     */
    public static AppStatus getInstance() {
        return ourInstance;
    }

    /**
     * Retrieves the current user of the application.
     * @return The user.
     */
    public User getCurrentUser(){
        return currentUser;
    }

    /**
     * Set the current user to the provided user.
     * @param user The new current user.
     */
    public void setCurrentUser(User user){
        currentUser = user;
    }

    /**
     * Retrieves the currently viewed patient.
     * @return The patient.
     */
    public Patient getViewedPatient(){
        return viewedPatient;
    }

    /**
     * Set the viewed patient to the provided patient.
     * @param patient The new viewed patient.
     */
    public void setViewedPatient(Patient patient) {
        viewedPatient = patient;
    }

    /**
     * Retrieves the currently viewed problem.
     * @return The problem.
     */
    public Problem getViewedProblem(){
        return viewedProblem;
    }

    /**
     * Set the viewed problem to the provided problem.
     * @param problem The new viewed problem.
     */
    public void setViewedProblem(Problem problem){
        viewedProblem = problem;
    }

    /**
     * Retrieves the currently viewed record.
     * @return The record.
     */
    public Record getViewedRecord(){
        return viewedRecord;
    }

    /**
     * Set the viewed record to the provided record.
     * @param record The new viewed record.
     */
    public void setViewedRecord(Record record){
        viewedRecord = record;
    }
}
