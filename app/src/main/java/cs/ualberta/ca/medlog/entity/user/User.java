package cs.ualberta.ca.medlog.entity.user;

import java.util.Calendar;
import java.util.Date;

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
public abstract class User {

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

    /**
     * Checks if two users are equal.
     * @param obj The other user object
     * @return True if they are equal, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User){
            User otherUser = (User) obj;
            return this.getUsername().equals(otherUser.getUsername());
        }
        return super.equals(obj);
    }
}
