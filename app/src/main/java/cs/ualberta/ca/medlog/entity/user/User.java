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

    public String getUsername() {
        return username;
    }
    public void setUpdated(){ lastUpdated = Calendar.getInstance().getTime(); }
    public Date getLastUpdated() {return lastUpdated; }
}
