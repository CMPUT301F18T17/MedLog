package cs.ualberta.ca.medlog.entity.user;

import java.io.Serializable;

public abstract class User implements Serializable {

    private String username;

    public User(String username){
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
