/*
https://developer.android.com/training/data-storage/files
 */

package cs.ualberta.ca.medlog.helper;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class LocalLogin {
    public Context context;
    public String filename;

    public LocalLogin(Context context) {
        this.context = context;
        this.filename = "logins.sav";
    }

    public LocalLogin(Context context, String filename) {
        this.context = context;
        this.filename = filename;
    }

    public void saveUserLogin(String username) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            osw.write(username);
            osw.close();
        } catch (IOException e) {
            Log.d("LocalLogin", "IOException thrown in LocalLogin.saveUserLogin(username)");
            e.printStackTrace();
        }
    }

    public boolean checkUserLogin(String username) {
        boolean valid = false;
        String offlineUsers;
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
                offlineUsers = stringBuilder.toString();
                System.out.print(offlineUsers);

                if (offlineUsers.contains(username)) {
                    System.out.println("Offline login successful");  // TODO remove
                    valid = true;
                } else {
                    System.out.println("Offline login unsuccessful");  // TODO remove
                }
            }

        } catch (IOException e) {
            Log.d("LocalLogin", "IOException thrown in LocalLogin.checkUserLogin(username");
            e.printStackTrace();
        }

        return valid;
    }

}
