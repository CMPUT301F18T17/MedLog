package cs.ualberta.ca.medlog.entity.user;

public class ContactInfo {

    private String email;
    private String phoneNumber;

    public ContactInfo(String phone, String email){
        this.phoneNumber = phone;
        this.email = email;

        if (this.phoneNumber.length() < 7) { // Phone numbers are at least 10 digits long
            throw new RuntimeException();
        }
        if (!this.email.contains("@")) { // An email must include the "@" symbol
            throw new RuntimeException();
        }
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