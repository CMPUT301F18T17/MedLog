package cs.ualberta.ca.medlog.entity.user;

public abstract class User {

    private String username;

    public User(String username){
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
