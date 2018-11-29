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
import java.security.Provider;
import java.util.ArrayList;

import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.entity.user.User;
import cs.ualberta.ca.medlog.exception.UserNotFoundException;

/**
 * <h1>
 *     Local login code functionality
 * </h1>
 *
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
 *
 *
 * <p>
 *     User Guide: <br>
 *         Attempted code login: <br>
 *             <l1>Instantiate new LoginCodes (ex. {@code LoginCodes codes = new LoginCodes(getContext()})</l1>
 *             <li>Check if login code has been saved (ex. {@code if (codes.checkCode(userCode) {}})</li>
 *             <li>If true, login</li>
 *             <li>If false, login and add user (ex. {@code codes.addCode(userCode)}</li>
 *
 *         Attempted username login: <br>
 *             <li><l1>Instantiate new LoginCodes (ex. {@code LoginCodes codes = new LoginCodes(getContext()})</l1></li>
 *             <li>Get user's login code (ex. {@code String userCode = //getter method for user login code})</li>
 *             <li>Check if login code has been saved (ex. {@code if (codes.checkCode(userCode) {...} })</li>
 *             <li>If true, login</li>
 *             <li>If false, deny login and tell the user that they must login with their user code</li>
 * </p>
 *
 * <p>
 *     References: <br>
 *         Alex, StackOverflow response to "Gson - convert from Json to a typed ArrayList<T>"
 *         https://stackoverflow.com/a/12384156
 *         https://stackoverflow.com/users/20634/alex
 *         Answered 2012-09-12, edited 2017-01-31, accessed 2018-11-27
 * </p>
 *
 * @author Tem Tamre
 * @contact ttamre@ualberta.ca
 */
public class LoginCodes {
    public Context context;
    private String filename;
    private ArrayList<String> codes = new ArrayList<>();

    /**
     * Initialize a LoginCodes object
     * @param context
     */
    public LoginCodes(Context context){
        this.filename = "codes.sav";
        this.context = context;
    }


    /* Getter methods */

    /**
     * Getter method for the context in use
     * @return context
     */
    public Context getContext() {
        return context;
    }

    /**
     * Getter method for the filename in use
     * @return filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Getter method for the list of codes that are allowed on the device
     * @return codes
     */
    public ArrayList<String> getCodes() {
        return codes;
    }


    /* Setter methods */

    /**
     * Setter method for the context
     * @param context
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * Setter method for the filename
     * NOTE: Changing the filename means that the old list of codes will not be accessible
     * @param filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Setter method for the code array
     * NOTE: This will wipe {@code this.codes}
     * @param codes
     */
    public void setCodes(ArrayList<String> codes) {
        this.codes = codes;
        save();
    }


    /* Main functionality */

    /**
     * Add a code to the ArrayList
     * @param code the code to be added
     */
    public void addCode(String code) {
        codes.add(code);
        save();
    }

    /**
     * Delete a code from the ArrayList
     * @param code the code to be added
     * @throws UserNotFoundException if the code being deleted isn't in the ArrayList
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
     * Save the current code ArrayList to the device
     * This function is private because it is already called within this class whenever a change is
     *  made to the codes ArrayList
     */
    private void save() {
        Gson gson = new Gson();
        String json = gson.toJson(codes);

        try {
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            osw.write(json);
            osw.close();
        } catch (IOException e) {
            Log.d("LoginCodes", "IOException thrown in LoginCodes.save()");
            e.printStackTrace();
        }
    }


    /**
     * Loads the saved code list into the codes ArrayList
     * This function is private because it is already called within this class whenever a comparison
     *  is made with the codes ArrayList
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

    /**
     * Checks if the given code is in the codes ArrayList
     * @param code the code to be checked
     * @return True if the code exists in the ArrayList, false otherwise
     */
    public boolean checkCode(String code) {
        load();
        return codes.contains(code);
    }
}
