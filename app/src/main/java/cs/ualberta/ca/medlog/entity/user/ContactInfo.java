/*
 * Class file for the ContactInfo entity
 *
 * Authors: Thomas Roskewich, Tem Tamre
 * Contact: roskewic@ualberta.ca, ttamre@ualberta.ca
 * Created: October 27, 2018
 */

package cs.ualberta.ca.medlog.entity.user;

public class ContactInfo {

    private String email;
    private String phoneNumber;

    public ContactInfo(String phone, String emailAddress){
        phoneNumber = phone;
        email = emailAddress;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


    public void setEmail(String newEmail) {
        email = newEmail;
    }

    public void setPhoneNumber(String newPhoneNumber) {
        phoneNumber = newPhoneNumber;
    }
}
