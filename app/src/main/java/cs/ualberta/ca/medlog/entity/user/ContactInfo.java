package cs.ualberta.ca.medlog.entity.user;

/**
 * <p>
 *     Description: <br>
 *         This class represents a patient's contact information in the application. This contains
 *         an email and a phone number.
 * </p>
 * <p>
 *     Issues: <br>
 *         Regex implementations instead of direct checks of contact information correctness should
 *         be done.
 * </p>
 *
 * @author Thomas Roskewich, Tem Tamre
 * @version 1.1
 * @see Patient
 */
public class ContactInfo {

    private String email;
    private String phoneNumber;

    /**
     * Constructs a new ContactInfo with the given phone number and email in string form.
     * @param phone The phone number.
     * @param email The email address.
     */
    public ContactInfo(String phone, String email) {
        this.phoneNumber = phone;
        this.email = email;

        // Temporary validation fixes, regex implementation should be used
        if (this.phoneNumber.length() < 7 || this.phoneNumber.length() > 11) {
            throw new RuntimeException();
        }
        if (!(this.email.contains("@"))) { // An email must include the "@" symbol
            throw new RuntimeException();
        }
    }

    /**
     * Retrieves the stored email.
     * @return The email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the stored email to the provided address.
     * @param newEmail The new email address.
     */
    public void setEmail(String newEmail) {

        email = newEmail;
    }

    /**
     * Retrieves the stored phone number.
     * @return The phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the stored phone number to the provided number.
     * @param newPhoneNumber The new phone number.
     */
    public void setPhoneNumber(String newPhoneNumber) {
        phoneNumber = newPhoneNumber;
    }

    /**
     * Checks if two ContactInfo objects are equal by comparing all of their field for equality.
     * @param obj The other ContactInfo object.
     * @return Boolean identifying if they are equal.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ContactInfo) {
            ContactInfo other = (ContactInfo) obj;
            return (this.email.equals(other.email)) && (this.phoneNumber.equals(other.phoneNumber));
        }
        return false;
    }
}