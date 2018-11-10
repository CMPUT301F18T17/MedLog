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
 * @author Tem Tamre
 * @contact ttamre@ualberta.ca
 * @see cs.ualberta.ca.medlog.helper.Database
 */

package cs.ualberta.ca.medlog.helper;

import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.entity.user.CareProvider;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class LocalSaver {
    private String filename;
    private Context context;

    /**
     * <p>Initialize a LocalSaver instance for the current context with the default filename</p>
     * @param c current application context
     */
    public LocalSaver(Context c) {
        this.context = c;
        this.filename = "data.ser";
    }

    /**
     * <p>Initialize a LocalSaver instance for the current context with the given filename</p>
     * @param c current application context
     * @param filename filename to be used
     */
    public LocalSaver(Context c, String filename) {
        this.context = c;
        this.filename = filename;
    }

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
            Log.d("LocalSaver", "IOException thrown in LocalSaver.savePatient(patient)");
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
            Log.d("LocalSaver", "IOException thrown in LocalSaver.saveCareProvider(CareProvider)");
            e.printStackTrace();
        }
    }


    /**
     * <p>Load the JSON file into a string, then deserialize it and return it as a patient object</p>
     * @return patient:Patient (the patient represented in the JSON data)
     */
    public Patient loadPatient() {
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
                return gson.fromJson(json, Patient.class);
            }

        } catch (IOException e) {
            Log.d("LocalSaver", "IOException thrown in LocalSaver.loadPatient(patient)");
            e.printStackTrace();
        }

        return null;
    }


    /**
     * <p>Load the JSON file into a string, then deserialize it and return it as a CareProvider object</p>
     * @return CareProvider:CareProvider (the CareProvider represented in the JSON data)
     */
    public CareProvider loadCareProvider() {
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
                return gson.fromJson(json, CareProvider.class);
            }

        } catch (IOException e) {
            Log.d("LocalSaver", "IOException thrown in LocalSaver.loadPatient(patient)");
            e.printStackTrace();
        }

        return null;
    }

}
