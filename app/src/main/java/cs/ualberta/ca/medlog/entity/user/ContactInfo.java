package cs.ualberta.ca.medlog.entity.user;

/**
 *
 * <h1>
 *     ContactInfo
 * </h1>
 *
 *  <p>
 *     Description: <br>
 *         This class represents a Patients contact information if needed.
 *
 * </p>
 *
 * @author Thomas Roskewich, Tem Tamre
 * @version 1.0
 * @see cs.ualberta.ca.medlog.entity.user.Patient
 */

public class ContactInfo {

    private String email;
    private String phoneNumber;

    public ContactInfo(String phone, String email) {
        this.phoneNumber = phone;
        this.email = email;

        // Temporary validation fixes, regex implementation should be used
        if (this.phoneNumber.length() < 7 || this.phoneNumber.length() > 11) { // Phone numbers are at least 10 digits long
            throw new RuntimeException();
        }
        if (!(this.email.contains("@"))) { // An email must include the "@" symbol
            throw new RuntimeException();
        }
    }

    /**
     * <p>Get the email.</p>
     * @return The email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * <p>Get the phone number.</p>
     * @return The phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }


    /**
     * <p>Set the email.</p>
     * @param newEmail The new email.
     */
    public void setEmail(String newEmail) {
        email = newEmail;
    }

    /**
     * <p>Set the phone number.</p>
     * @param newPhoneNumber The new phone number.
     */
    public void setPhoneNumber(String newPhoneNumber) {
        phoneNumber = newPhoneNumber;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ContactInfo) {
            ContactInfo other = (ContactInfo) obj;
            return (this.email.equals(other.email)) && (this.phoneNumber.equals(other.phoneNumber));
        }
        return false;
    }
}