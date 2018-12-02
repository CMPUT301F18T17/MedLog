package cs.ualberta.ca.medlog.entity;

/**
 * <p>
 *     Description: <br>
 *         Class for holding the string representation of an encrypted photo used in storage on the
 *         Elastic Search Server.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Thomas Roskewich
 * @version 1.0
 * @see Photo
 */
public class EncryptedPhoto {
    private String data;

    /**
     * Constructs the encrypted photo using the provided string data.
     * @param data The data.
     */
    public EncryptedPhoto(String data){
        this.data = data;
    }

    /**
     * Retrieves the encrypted photo data.
     * @return The encrypted photo data.
     */
    public String getData(){
        return data;
    }

}
