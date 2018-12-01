package cs.ualberta.ca.medlog.exception;

/**
 * <p>
 *     Description: <br>
 *         This class represents the exception that is thrown when an error occurs in encryption.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Thomas Roskewic
 * @see cs.ualberta.ca.medlog.helper.Encryption
 */
public class EncryptionException extends Exception {
    public EncryptionException(String message){
        super(message);
    }
}
