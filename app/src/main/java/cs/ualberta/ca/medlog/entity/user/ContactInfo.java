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

    public ContactInfo(String phone, String email){
        this.phoneNumber = phone;
        this.email = email;

        // Temporary validation fixes, regex implementation should be used
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