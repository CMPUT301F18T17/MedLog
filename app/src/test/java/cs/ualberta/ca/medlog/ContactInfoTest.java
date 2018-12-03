package cs.ualberta.ca.medlog;

import org.junit.Test;

import cs.ualberta.ca.medlog.entity.user.ContactInfo;

import static org.junit.Assert.assertEquals;

/**
 * <p>
 *     Description: <br>
 *         Test class for the ContactInfo entity.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Thomas Roskewich, Tem Tamre, Calvin Chomyc
 * @version 1.1
 * @see ContactInfo
 */
public class ContactInfoTest {

    /**
     * Tests if a ContactInfo object's email is successfully returned.
     */
    @Test
    public void testGetEmail(){
        ContactInfo ct = new ContactInfo("1234567890", "test@test.com");
        assertEquals(ct.getEmail(), "test@test.com");
    }

    /**
     * Tests if a ContactInfo object's email is successfully changed.
     */
    @Test
    public void testSetEmail() {
        ContactInfo ct = new ContactInfo("1234567890", "test@test.com");
        assertEquals(ct.getEmail(), "test@test.com");

        ct.setEmail("superTest@test.com");
        assertEquals(ct.getEmail(), "superTest@test.com");
    }

    /**
     * Tests if an exception is returned when an invalid email is used.
     */
    @Test
    public void testInvalidEmail(){
        ContactInfo ct;
        try {
            ct = new ContactInfo("1234567890", "test");
        } catch(Exception e) {
            ct = new ContactInfo("1234567890", "test@test.com");
        }
        assertEquals(ct.getEmail(), "test@test.com");
    }

    /**
     * Tests if a ContactInfo object's phone number is successfully returned.
     */
    @Test
    public void testGetPhoneNumber(){
        ContactInfo ct = new ContactInfo("1234567890", "test@test.com");
        assertEquals(ct.getPhoneNumber(), "1234567890");
    }

    /**
     * Tests if a ContactInfo object's phone number is successfully changed.
     */
    @Test
    public void testSetPhoneNumber() {
        ContactInfo ct = new ContactInfo("1234567890", "test@test.com");
        assertEquals(ct.getPhoneNumber(), "1234567890");

        ct.setPhoneNumber("9876543210");
        assertEquals(ct.getPhoneNumber(), "9876543210");
    }

    /**
     * Tests if an exception is returned when an invalid phone number is used.
     */
    @Test
    public void testInvalidPhoneNumber(){
        ContactInfo ct;

        try {
            ct = new ContactInfo("1234", "test");
        } catch(Exception e) {
            ct = new ContactInfo("1234567890", "test@test.com");
        }
        assertEquals("1234567890", ct.getPhoneNumber());

        try {
            ct = new ContactInfo("wow", "test");
        } catch(Exception e) {
            ct = new ContactInfo("1234567890", "test@test.com");
        }
        assertEquals("1234567890", ct.getPhoneNumber());
    }

    /**
     * Tests if a ContactInfo object is equal to another with the same phone number and email.
     */
    @Test
    public void testEquals(){
        ContactInfo ct1 = new ContactInfo("1234567890", "test@test.com");
        ContactInfo ct2 = new ContactInfo("1234567890", "test@test.com");
        assertEquals(ct1, ct2);
    }
}