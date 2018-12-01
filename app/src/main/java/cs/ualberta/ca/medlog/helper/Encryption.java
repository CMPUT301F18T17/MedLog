package cs.ualberta.ca.medlog.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.nio.ByteBuffer;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import cs.ualberta.ca.medlog.exception.EncryptionException;

/**
 * <p>
 *     Description: <br>
 *         The purpose of this class is to allow for encryption of byte arrays based off the
 *         users provided username(key).
 *
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 * <p>
 *     References: <br>
 *         https://stackoverflow.com/questions/7360403/base-64-encode-and-decode-example-code
 *         https://stackoverflow.com/questions/3451670/java-aes-and-using-my-own-key
 *         https://stackoverflow.com/questions/24910620/encode-and-decode-bitmap-to-byte-array-without-compress
 *         Accessed Nov 20, 2018
 * </p>
 *
 * @author Thomas Roskewic
 * @version 1.0
 */
public class Encryption {

    private static String algorithm = "AES";

    /**
     * Encrypts the provided data using AES.
     * @param key The key.
     * @param data The data to be encrypted as a byte array.
     * @return The data as an encrypted string.
     * @throws EncryptionException Thrown if the encryption fails.
     */
    public static String encryptData(String key, byte[] data) throws EncryptionException {
        try {

            // Generate a key based off the username using SHA-1
            byte[] keyV = key.getBytes();
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            keyV = sha.digest(keyV);
            keyV = Arrays.copyOf(keyV, 16);

            // Convert it to a key object for the Chipher
            Key secretKeySpec = new SecretKeySpec(keyV, algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            // Get the encrypted string and return.
            byte[] encoded = cipher.doFinal(data);
            return Base64.encodeToString(encoded, Base64.DEFAULT);
        }catch(Exception e){
            e.printStackTrace();
            throw new EncryptionException("Failed to encrypt data: " + e.getMessage() + ".");
        }
    }

    /**
     * Decrypts the provided data using AES.
     * @param key The key.
     * @param data The data to decrypt as a string.
     * @return The byte array of the data decrypted.
     * @throws EncryptionException Thrown if the decryption fails.
     */
    public static byte[] decryptData(String key, String data) throws EncryptionException{
        try{
            // Generate a key based off the username using SHA-1
            byte[] keyV = key.getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            keyV = sha.digest(keyV);
            keyV = Arrays.copyOf(keyV, 16);

            // Convert it to a key object for the Chipher
            Key secretKeySpec = new SecretKeySpec(keyV, algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            byte[] decodedVal = Base64.decode(data, Base64.DEFAULT);
            byte[] decVal = cipher.doFinal(decodedVal);
            return decVal;

        }catch(Exception e){
            e.printStackTrace();
            throw new EncryptionException("Failed to encrypt data: " + e.getMessage() + ".");
        }
    }

    /**
     * Converts a bitmap to its respective byte array.
     * @param b The bitmap.
     * @return The byte array for the bitmap.
     */
    public static byte[] bitMaptoBytes(Bitmap b){
        ByteBuffer byteBuffer = ByteBuffer.allocate(b.getByteCount());
        b.copyPixelsToBuffer(byteBuffer);
        byteBuffer.rewind();
        return byteBuffer.array();
    }

    /**
     * Converts a byte array back into a bitmap.
     * @param b The byte array.
     * @return The bitmap for the byte array.
     */
    public static Bitmap byteArrayToBitmap(byte[] b){
        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    /**
     * Converts a byte array to its respective string.
     * @param b The byte array.
     * @return The string for the byte array.
     */
    public static String byteArrayToString(byte[] b){
        return new String(b);
    }
}
