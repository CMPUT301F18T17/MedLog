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

import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.exception.UserNotFoundException;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


/**
 *
 * <h1>
 *     Local file saver
 * </h1>
 *
 *
 *  <p>
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
 *
 *
 * <p>
 *     Usage: <br>
 *         NOTE: This file should probably be interacting with the Controllers.
 *
 *         Whenever a change to a user is made, check for connectivity to the database.
 *
 *         If a connection cannot be established, save the changes locally and set a connection
 *         variable to a state that represents it awaiting for a connection to be established.
 *
 *         If a connection can be estabished, check for the existence of a cache file. If one exists,
 *         load that object into the user and commit the entire user profile. Then, delete the cache
 *         file.
 *
 * </p>
 *
 *
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
 *         Published 2013-01-07, edited 2016-08-10, accessed 2018-11-09
 * </p>
 *
 *
 * NEW OFFLINE MODEL
 *
 * LocalCache (saves and loads user data)
 * LocalLogin (stores all usernames logged in on device and allows for local login)
 *
 * @author Tem Tamre
 * @contact ttamre@ualberta.ca
 * @see cs.ualberta.ca.medlog.helper.Database
 * @see cs.ualberta.ca.medlog.controller.PatientController
 * @see cs.ualberta.ca.medlog.controller.ProblemController
 * @see cs.ualberta.ca.medlog.controller.ProviderController
 * @see cs.ualberta.ca.medlog.controller.SyncController
 */

public class LocalCache {
    private String filename;
    private Context context;

    /**
     * <p>Initialize a LocalCache instance for the current context with the default filename</p>
     * @param context current application context
     */
    public LocalCache(Context context) {
        this.context = context;
        this.filename = "cache.ser";
    }

    /**
     * <p>Initialize a LocalCache instance for the current context with the given filename</p>
     * @param context current application context
     * @param filename filename to be used
     */
    public LocalCache(Context context, String filename) {
        this.context = context;
        this.filename = filename;
    }


    /* Loading methods */

    /**
     * <p>Load the JSON file into a string, then deserialize it and return it as a patient object</p>
     * @return patient:Patient (the patient represented in the JSON data)
     */
    public Patient loadPatient() throws UserNotFoundException {
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
                context.deleteFile(filename);
                return gson.fromJson(json, Patient.class);
            }

        } catch (IOException e) {
            Log.d("LocalCache", "IOException thrown in LocalCache.loadPatient(patient)");
            throw new UserNotFoundException("Could not load user locally.");
        }

        return null;
    }

    /**
     * <p>Load the JSON file into a string, then deserialize it and return it as a CareProvider object</p>
     * @return CareProvider:CareProvider (the CareProvider represented in the JSON data)
     */
    public CareProvider loadCareProvider() throws UserNotFoundException{
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
                context.deleteFile(filename);
                return gson.fromJson(json, CareProvider.class);
            }

        } catch (IOException e) {
            Log.d("LocalCache", "IOException thrown in LocalCache.loadPatient(patient)");
            throw new UserNotFoundException("Could not load user locally.");
        }

        return null;
    }


    /* Saving methods */

    /**
     * <p>Serialize a patient's data and save it to disc</p>
     * @param patient patient to be saved to disc
     */
    public void savePatient(Patient patient) {
        Gson gson = new Gson();
        String json = gson.toJson(patient);

        try {
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            osw.write(json);
            osw.close();
        } catch (IOException e) {
            Log.d("LocalCache", "IOException thrown in LocalCache.savePatient(patient)");
            e.printStackTrace();
        }
    }

    /**
     * <p>Serialize a CareProvider's data ands save it to disc</p>
     * @param provider CareProvider to be saved to disc
     */
    public void saveCareProvider(CareProvider provider) {
        Gson gson = new Gson();
        String json = gson.toJson(provider);

        try {
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            osw.write(json);
            osw.close();
        } catch (IOException e) {
            Log.d("LocalCache", "IOException thrown in LocalCache.saveCareProvider(CareProvider)");
            e.printStackTrace();
        }
    }

}
