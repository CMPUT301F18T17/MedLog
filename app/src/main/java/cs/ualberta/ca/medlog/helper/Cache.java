/*
   Copyright 2018 Google

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package cs.ualberta.ca.medlog.helper;

import cs.ualberta.ca.medlog.entity.user.User;
import cs.ualberta.ca.medlog.exception.UserNotFoundException;

import android.content.Context;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * <p>
 *     Description: <br>
 *         The purpose of this class is to locally save any changes made to the user's data
 *         if internet connectivity is ever lost. Upon regaining connectivity, the changes will
 *         then be pushed to remote storage in the Elastisearch database. On a successful push to
 *         the database, the local file will be safely deleted from storage.
 *
 *         Data is saved locally by GSON serialization and a IOStreamWriter. If the user loses
 *         connectivity, or the application crashes, and data is lost from memory, it can be
 *         deserialized and loaded.
 *
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 * <p>
 *     References: <br>
 *
 *         Google, GSON User Guide
 *         https://github.com/google/gson/blob/master/UserGuide.md#TOC-Gson-With-Gradle
 *         Published 2014-11-24, edited 2018-10-29, accessed 2018-11-09
 *
 *         R9J, VSN, Response to StackOverflow question "How to read/write a string from a file in android"
 *         https://stackoverflow.com/a/14377185
 *         https://stackoverflow.com/users/1080355/vsb
 *         https://stackoverflow.com/users/1912085/r9j
 *         Answered 2013-01-07, edited 2016-08-10, accessed 2018-11-09
 *
 *         Jakob Jenkov, Java Generics - Class Objects as Type Literals
 *         http://tutorials.jenkov.com/java-generics/class-objects-as-type-literals.html
 *         Last updated 2014-06-23, acccessed 2018-11-28
 * </p>
 *
 * @author Tem Tamre
 * @version 1.1
 * @see cs.ualberta.ca.medlog.helper.Database
 */
public class Cache {
    private String filename;
    private Context context;

    /**
     * Initialize a Cache instance for the current context with the default filename
     * @param context The current application context.
     */
    public Cache(Context context) {
        this.context = context;
        this.filename = "cache.ser";
    }


    /**
     * Load the JSON file into a string, then deserialize it and return it as an objectClass object.
     * <p>
     *      Usage: <br>
     *          {@code Cache cache = new Cache(getContext());}
     *          {@code Patient p = cache.load(Patient.class);}
     * </p>
     * @param objectClass The class that should be returned.
     * @return The object of that class.
     * @throws UserNotFoundException Thrown if the user can't be loaded.
     */
    public <T> T load(Class<T> objectClass) throws UserNotFoundException {
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
                return gson.fromJson(json, objectClass);
            }

        } catch (IOException e) {
            throw new UserNotFoundException("Could not load user locally.");
        }

        return null;
    }


    /**
     * Serialize an object and save it to disc.
     * <p>
     *     Usage: <br>
     *         {@code Patient p;}
     *         {@code Cache cache = new Cache(getContext());}
     *         {@code cache.save(p);}
     * </p>
     * @param user The object to be serialized and saved.
     */
    public void save(User user) {
        Gson gson = new Gson();
        String json = gson.toJson(user);

        try {
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            osw.write(json);
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
