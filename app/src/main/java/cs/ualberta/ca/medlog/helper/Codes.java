package cs.ualberta.ca.medlog.helper;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import cs.ualberta.ca.medlog.exception.UserNotFoundException;

/**
 * <p>
 *     Description: <br>
 *         The purpose of this class is to assist in user login. If users have not logged in
 *         on the device, they must use a login code. Once logged in with the code, their account
 *         code will be saved to the device and they can then log back into the same device with a
 *         username instead of a code.
 *
 *         New device  ->  Login with code
 *         Used device ->  Login with username
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 * <p>
 *     User Guide: <br>
 *         Attempted code login: <br>
 *             <l1>Instantiate new Codes (ex. {@code Codes codes = new Codes(getContext()})</l1>
 *             <li>Check if login code has been saved (ex. {@code if (codes.checkCode(userCode) {}})</li>
 *             <li>If true, login</li>
 *             <li>If false, login and add user (ex. {@code codes.addCode(userCode)}</li>
 *
 *         Attempted username login: <br>
 *             <li><l1>Instantiate new Codes (ex. {@code Codes codes = new Codes(getContext()})</l1></li>
 *             <li>Get user's login code (ex. {@code String userCode = //getter method for user login code})</li>
 *             <li>Check if login code has been saved (ex. {@code if (codes.checkCode(userCode) {...} })</li>
 *             <li>If true, login</li>
 *             <li>If false, deny login and tell the user that they must login with their user code</li>
 * </p>
 * <p>
 *     References: <br>
 *         Alex, StackOverflow response to "Gson - convert from Json to a typed ArrayList<T>"
 *         https://stackoverflow.com/a/12384156
 *         https://stackoverflow.com/users/20634/alex
 *         Answered 2012-09-12, edited 2017-01-31, accessed 2018-11-27
 * </p>
 *
 * @author Tem Tamre
 * @version 1.0
 */
public class Codes {
    public Context context;
    private String filename;
    private ArrayList<String> codes = new ArrayList<>();

    /**
     * Initialize a Codes object with the given application context.
     * @param context The application context.
     */
    public Codes(Context context){
        this.filename = "codes.sav";
        this.context = context;
    }

    /* Getter methods */

    /**
     * Retrieves the context used for the object.
     * @return The context.
     */
    public Context getContext() {
        return context;
    }

    /**
     * Retrieves the filename used for storing the codes.
     * @return The filename.
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Retrieves all of the allowed login codes for the device.
     * @return ArrayList of the allowed login codes.
     */
    public ArrayList<String> getCodes() {
        return codes;
    }

    /* Setter methods */

    /**
     * Sets the context used for the object to the provided one.
     * @param context The new context to use.
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * Sets a new filename for where to store the list of codes using the provided one.
     * NOTE: Changing the filename means that the old list of codes will not be accessible
     * @param filename The new filename to use.
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Sets the list of allowed login codes for the device to the provided one.
     * NOTE: This will wipe {@code this.codes}
     * @param codes The new ArrayList of login codes to use.
     */
    public void setCodes(ArrayList<String> codes) {
        this.codes = codes;
        save();
    }

    /* Main functionality */

    /**
     * Adds a new login code to the allowed list.
     * @param code The new code to be added.
     */
    public void addCode(String code) {
        codes.add(code);
        save();
    }

    /**
     * Deletes a code from the allowed list.
     * @param code The code to be deleted.
     * @throws UserNotFoundException Is thrown if the code wasn't in the list.
     */
    public void deleteCode(String code) throws UserNotFoundException {
        if (codes.contains(code)) {
            codes.remove(code);
            save();
        } else {
            throw new UserNotFoundException("Code was not found on the device");
        }
    }

    /**
     * Checks if the given code is in the allowed list.
     * @param code The code to be checked.
     * @return Boolean of whether the code exists in the list or not.
     */
    public boolean checkCode(String code) {
        load();
        return codes.contains(code);
    }

    /**
     * Saves the current codes list to the device memory. This function is called within this class
     * whenever changes are made to the allowed code list.
     */
    private void save() {
        Gson gson = new Gson();
        String json = gson.toJson(codes);

        try {
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            osw.write(json);
            osw.close();
        } catch (IOException e) {
            Log.d("Codes", "IOException thrown in Codes.save()");
            e.printStackTrace();
        }
    }

    /**
     * Loads the allowed code list that is stored on the device into this object. This function is
     * called within this class whenever checking if a code is in the allowed ones is done.
     */
    private void load() {
        Gson gson = new Gson();
        String json;

        try {
            InputStream inStream = context.openFileInput(filename);

            if (inStream != null) {
                InputStreamReader isr = new InputStreamReader(inStream);
                BufferedReader bufferedReader = new BufferedReader(isr);
                String next;
                StringBuilder stringBuilder = new StringBuilder();

                while((next = bufferedReader.readLine()) != null) {
                    stringBuilder.append(next);
                }

                inStream.close();
                json = stringBuilder.toString();
                codes = gson.fromJson(json, new TypeToken<ArrayList<String>>(){}.getType());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
