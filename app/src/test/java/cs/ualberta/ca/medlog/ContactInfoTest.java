package cs.ualberta.ca.medlog;

import org.junit.Test;

import cs.ualberta.ca.medlog.entity.user.ContactInfo;

import static org.junit.Assert.assertEquals;

public class ContactInfoTest {

    @Test
    public void emailTests(){
        ContactInfo ct = new ContactInfo("1234567890", "sam@gmail.com");
        assertEquals(ct.getEmail(), "sam@gmail.com");
        ct.setEmail("john@shaw.ca");
        assertEquals(ct.getEmail(), "john@shaw.ca");
    }

    @Test
    public void phoneTests(){
        ContactInfo ct = new ContactInfo("1234567890", "sam@gmail.com");
        assertEquals(ct.getPhoneNumber(), "1234567890");
        ct.setPhoneNumber("9876543210");
        assertEquals(ct.getPhoneNumber(), "9876543210");
    }

    @Test
    public void invalidEmailTest(){
        ContactInfo ct;
        try{
            ct = new ContactInfo("1234567890", "sam");
        }catch(RuntimeException e){
            ct = new ContactInfo("1234567890", "sam@gmail.com");
        }
        assertEquals(ct.getEmail(), "sam@gmail.com");
    }

    @Test
    public void invalidPhoneTest(){
        ContactInfo ct;

        try{
            ct = new ContactInfo("1234", "sam@gmail.com");
        }catch(RuntimeException e){
            ct = new ContactInfo("1234567890", "sam@gmail.com");
        }
        assertEquals(ct.getPhoneNumber(), "1234567890");
    }


}
