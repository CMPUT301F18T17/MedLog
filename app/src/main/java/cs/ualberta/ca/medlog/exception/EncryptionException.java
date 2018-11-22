package cs.ualberta.ca.medlog.exception;

/**
 *
 * <h1>
 *     EncryptionException
 * </h1>
 *
 *
 *  <p>
 *     Description: <br>
 *         The purpose of this class is to be thrown whenever an exception occurs during Encryption.
 *
 * </p>
 *
 * @author Thomas Roskewic
 * @contact roskewic@ualberta.ca
 * @see cs.ualberta.ca.medlog.helper.Encryption
 */
public class EncryptionException extends Exception {

    public EncryptionException(String message){
        super(message);
    }
}
