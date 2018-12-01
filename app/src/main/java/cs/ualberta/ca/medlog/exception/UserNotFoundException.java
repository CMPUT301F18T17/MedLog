package cs.ualberta.ca.medlog.exception;

/**
 * <p>
 *     Description: <br>
 *         The purpose of this class is to be thrown when there is an error in finding the user in
 *         the elastic search database.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Thomas Roskewich
 * @see cs.ualberta.ca.medlog.controller.ElasticSearchController
 * @see cs.ualberta.ca.medlog.helper.Database
 */
public class UserNotFoundException extends Exception{
    public UserNotFoundException(String s){
        super(s);
    }
}
