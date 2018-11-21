package cs.ualberta.ca.medlog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import cs.ualberta.ca.medlog.helper.Encryption;

import static junit.framework.TestCase.*;

@RunWith(RobolectricTestRunner.class)
public class EncryptionTest {


    @Test
    public void testEncrypt(){
        String enc = Encryption.encryptData("roskewic", "TEST MESSAGE");
        System.out.println(enc);
        String dec = Encryption.decryptData("roskewic", enc);
        assertFalse("Messages are equal after encryption.", enc.equals("TEST MESSAGE"));
        assertNotSame("The encrypted and decrypted strings are the same!", enc, dec);
        assertEquals("Decrypted message does not equal initial message.", "TEST MESSAGE", dec);
    }
}
