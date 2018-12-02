package cs.ualberta.ca.medlog.entity.user;

import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 *     Description: <br>
 *         An abstract class representing a user in the application. This contains the username for
 *         the user and also holds the time that the user was last updated from the servers.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Thomas Roskewich
 * @version 1.0
 */
public abstract class User {

    private String username;
    private Date lastUpdated;

    /**
     * Constructs a new User with the given username.
     * @param username The user username.
     */
    public User(String username){
        this.username = username;
    }

    /**
     * Retrieves the user's username.
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Updates the last updated time of the user.
     */
    public void setUpdated(){ lastUpdated = Calendar.getInstance().getTime(); }

    /**
     * Retrieves the last updated time for the user. This is used for server comparison.
     * @return The date the user was last updated.
     */
    public Date getLastUpdated() {return lastUpdated; }

    /**
     * Checks if two users are equal using each user's username.
     * @param obj The other User object.
     * @return Boolean identifying if they are equal.
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
