/**
 *
 * <h1>
 *     CurrentUser
 * </h1>
 *
 *  <p>
 *     Description: <br>
 *         The purpose of this class is to store the currently logged in user in a globally
 *         accessible container (singleton).
 *
 * </p>
 *
 *
 * @author Thomas Roskewich
 * @contact roskewic@ualberta.ca
 * @see cs.ualberta.ca.medlog.entity.user.User
 */

package cs.ualberta.ca.medlog.singleton;

import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.entity.user.User;

public class CurrentUser {
    private static final CurrentUser ourInstance = new CurrentUser();
    private static User usr;

    public static CurrentUser getInstance() {
        return ourInstance;
    }

    private CurrentUser() {

    }

    /**
     * Set the current user to the provided user.
     * @param user The user to use.
     */
    public void set(User user){
        usr = user;
    }

    /**
     * Get the user as a user.
     * @return The user.
     */
    public User get(){
        return usr;
    }

    /**
     * Get the singleton user as a patient
     * @return The user as a patient.
     */
    public Patient getAsPatient(){
        return (Patient) usr;
    }

    /**
     * Get the singleton user as a care provider.
     * @return The user as a care provider.
     */
    public CareProvider getAsProvider(){
        return (CareProvider) usr;
    }
}
