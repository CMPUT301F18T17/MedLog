package cs.ualberta.ca.medlog.helper;

import android.util.Base64;

import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import cs.ualberta.ca.medlog.singleton.CurrentUser;

public class Encryption {

    /*
    https://stackoverflow.com/questions/7360403/base-64-encode-and-decode-example-code
    https://stackoverflow.com/questions/3451670/java-aes-and-using-my-own-key
     */

    private static String algorithm = "AES";

    /**
     * <p>Encrypt the provided data using AES</p>
     */
    public static String encryptData(String username, String data){
        try {

            // Generate a key based off the username using SHA-1
            byte[] keyV = username.getBytes();
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            keyV = sha.digest(keyV);
            keyV = Arrays.copyOf(keyV, 16);

            // Convert it to a key object for the Chipher
            Key key = new SecretKeySpec(keyV, algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            // Get the encrypted string and return.
            byte[] encoded = cipher.doFinal(data.getBytes());
            return Base64.encodeToString(encoded, Base64.DEFAULT);
        }catch(Exception e){
            e.printStackTrace();
        }
        return data;
    }

    public static String decryptData(String username, String data){
        try{
            // Generate a key based off the username using SHA-1
            byte[] keyV = username.getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            keyV = sha.digest(keyV);
            keyV = Arrays.copyOf(keyV, 16);

            // Convert it to a key object for the Chipher
            Key key = new SecretKeySpec(keyV, algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] decodedVal = Base64.decode(data, Base64.DEFAULT);
            byte[] decVal = cipher.doFinal(decodedVal);
            return new String(decVal);

        }catch(Exception e){
            e.printStackTrace();
        }
        return data;
    }
}
