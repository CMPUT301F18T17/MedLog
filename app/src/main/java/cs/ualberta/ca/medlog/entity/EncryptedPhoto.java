package cs.ualberta.ca.medlog.entity;


/**
 * <h1>
 *     EncryptedPhoto
 * </h1>
 *
 * <p>
 *     Description: <br>
 *         Class for holding the string representation of an encrypted photo used in storage on the
 *         Elastic Search Server
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
    public EncryptedPhoto(String data){
        this.data = data;
    }

    /**
     * Get the encrypted photo data.
     * @return The encrypted photo data.
     */
    public String getData(){
        return data;
    }

}
