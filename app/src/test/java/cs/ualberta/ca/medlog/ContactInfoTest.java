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
 * @author Thomas Roskewich, Tem Tamre
 * @version 1.0
 * @see ContactInfo
 */
public class ContactInfoTest {

    @Test
    public void testGetEmail(){
        ContactInfo ct = new ContactInfo("1234567890", "test@test.com");
        assertEquals(ct.getEmail(), "test@test.com");
    }

    @Test
    public void testSetEmail() {
        ContactInfo ct = new ContactInfo("1234567890", "test@test.com");
        assertEquals(ct.getEmail(), "test@test.com");

        ct.setEmail("superTest@test.com");
        assertEquals(ct.getEmail(), "superTest@test.com");
    }

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

    @Test
    public void testGetPhoneNumber(){
        ContactInfo ct = new ContactInfo("1234567890", "test@test.com");
        assertEquals(ct.getPhoneNumber(), "1234567890");
    }

    @Test
    public void testSetPhoneNumber() {
        ContactInfo ct = new ContactInfo("1234567890", "test@test.com");
        assertEquals(ct.getPhoneNumber(), "1234567890");

        ct.setPhoneNumber("9876543210");
        assertEquals(ct.getPhoneNumber(), "9876543210");
    }

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


}