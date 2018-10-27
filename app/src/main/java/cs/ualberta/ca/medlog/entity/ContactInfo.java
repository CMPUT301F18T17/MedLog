package cs.ualberta.ca.medlog.entity;

public class ContactInfo {

    private String email;
    private String phoneNumber;

    public ContactInfo(String phone, String email){
        this.phoneNumber = phone;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
