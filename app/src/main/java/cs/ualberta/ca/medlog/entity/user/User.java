/**
 *
 * <h1>
 *     User
 * </h1>
 *
 *  <p>
 *     Description: <br>
 *         This class represents the user.
 *
 * </p>
 *
 * @author Thomas Roskewich
 * @contact roskewic@ualberta.ca
 */
package cs.ualberta.ca.medlog.entity.user;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public abstract class User implements Serializable {

    private String username;
    private Date lastUpdated;

    public User(String username){
        this.username = username;
    }

    /**
     * <p>Return the user's username.</p>
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * <p>Update the last updated time for server comparison.</p>
     */
    public void setUpdated(){ lastUpdated = Calendar.getInstance().getTime(); }

    /**
     * Get the time of last updated.
     * @return The date the user was last updated.
     */
    public Date getLastUpdated() {return lastUpdated; }
}
