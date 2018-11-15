/**
 *
 * <h1>
 *     UserNotFoundException
 * </h1>
 *
 *  <p>
 *     Description: <br>
 *         The purpose of this class is to be thrown when there is an error in finding the user in
 *         the elastic search database.
 *  </p>
 *
 * @author Thomas Roskewich
 * @contact roskewic@ualberta.ca
 * @see cs.ualberta.ca.medlog.helper.ElasticSearchController
 * @see cs.ualberta.ca.medlog.helper.Database
 */

package cs.ualberta.ca.medlog.exception;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String s){
        super(s);
    }
}
