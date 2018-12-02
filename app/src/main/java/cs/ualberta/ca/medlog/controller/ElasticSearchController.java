package cs.ualberta.ca.medlog.controller;

import android.os.AsyncTask;
import android.util.Log;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cs.ualberta.ca.medlog.entity.EncryptedPhoto;
import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.exception.EncryptionException;
import cs.ualberta.ca.medlog.helper.Encryption;
import cs.ualberta.ca.medlog.singleton.AppStatus;
import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;

/**
 *  <p>
 *     Description: <br>
 *         This controllers purpose is to interact directly with the Elastic Search server for the
 *         application and query it for information.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Thomas Roskewich
 * @version 1.0
 * @see cs.ualberta.ca.medlog.helper.Database
 */
public class ElasticSearchController {
    public static String databaseAddress = "http://cmput301.softwareprocess.es:8080";
    private static JestDroidClient client = null;
    private static final String INDEX_NAME = "cmput301f18t17";
    private static final int timeout = 500;


    /**
     * Sets the ElasticSearch client if it has not been already set.
     */
    private static void setClient(){
        if(client == null){
            DroidClientConfig config = new DroidClientConfig.Builder(databaseAddress).build();
            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }

    /**
     * Saves a patient to the Elastic Search Database.
     */
    public static class SavePatientTask extends AsyncTask<Patient, Void, Boolean> {
        /**
         * Saves the patient provided to the database asynchronously.
         * @param params The patient to be saved.
         * @return Boolean of whether the save succeeded.
         */
        @Override
        protected Boolean doInBackground(Patient... params) {
            return savePatient(params[0]);
        }
    }

    /**
     *  Load a patient from the Elastic Search database.
     */
    public static class LoadPatientTask extends AsyncTask<String, Void, Patient>{
        /**
         * Loads the patient identified by the username asynchronously.
         * @param params The patient username.
         * @return The patient from the database or null if no such patient exists.
         */
        @Override
        protected Patient doInBackground(String... params) {
            return loadPatient(params[0]);
        }
    }

    /**
     *  Delete a patient from the Elastic Search database.
     */
    public static class DeletePatientTask extends AsyncTask<String, Void, Boolean>{
        /**
         * Deletes the patient identified by the username from the database asynchronously.
         * @param strings The patient username.
         * @return Boolean of whether the deletion succeeded.
         */
        @Override
        protected Boolean doInBackground(String... strings) {
            return deletePatient(strings[0]);
        }
    }


    /**
     * Loads a Patient from the Elastic Search database.
     * @deprecated Use the Asynchronous Method in Production.
     * @param username The username of the client to load.
     * @return The Patients account information.
     */
    public static Patient loadPatient(String username){
        setClient();
        Get get = new Get.Builder(INDEX_NAME, username).type("patient").build();
        try{
            JestResult result = client.execute(get);
            if(result.isSucceeded()){
                Patient patient = result.getSourceAsObject(Patient.class);
                return patient;
            }
        }catch(IOException e){
            Log.d(cs.ualberta.ca.medlog.helper.Database.class.toString(),
                    String.format("Failed to Load Patient: Username: %s", username));
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Saves the patient to the Elastic Search Database.
     * @deprecated Use the Asynchronous Method in Production.
     * @param patient The patient to save.
     * @return Boolean of whether the save was successful.
     */
    public static Boolean savePatient(Patient patient){
        setClient();
        boolean success = false;
        Index index = new Index.Builder(patient)
                .index(INDEX_NAME)
                .type("patient")
                .id(patient.getUsername())
                .build();

        try{
            DocumentResult result = client.execute(index);
            if(result.isSucceeded()){
                success = true;
                Log.d(cs.ualberta.ca.medlog.helper.Database.class.toString(),
                        String.format("Saved Patient: Username: %s, Email: %s",
                                patient.getUsername(),
                                patient.getContactInfo().getEmail()));
            }else{
                System.out.println(result.getErrorMessage());
            }
        }catch (IOException e){
            e.printStackTrace();
            Log.d(cs.ualberta.ca.medlog.helper.Database.class.toString(),
                    String.format("Failed to Save Patient: Username: %s, Email: %s",
                            patient.getUsername(),
                            patient.getContactInfo().getEmail()));
        }
        return success;
    }

    /**
     * Deletes a patient from the Elastic Search Database.
     * @deprecated Use the Asynchronous Method in Production.
     * @param username The patient's username.
     * @return Boolean of whether the deletion was successful.
     */
    public static Boolean deletePatient(String username){
        setClient();
        boolean success = false;
        Delete delete = new Delete.Builder(username).index(INDEX_NAME).type("patient").build();
        try{
            DocumentResult result = client.execute(delete);
            if(result.isSucceeded()){
                success = true;
            }else{
                System.out.println(result.getErrorMessage());
            }
        }catch (IOException e){
            e.printStackTrace();
            Log.d(ElasticSearchController.class.getName(), "Failed to delete user with username: " + username);
        }
        return success;
    }


    /**
     *  Saves a Care Provider to the Elastic Search Database
     */
    public static class SaveCareProviderTask extends AsyncTask<CareProvider, Void, Boolean> {
        /**
         * Saves the Care Provider provided to the database asynchronously.
         * @param params The provider.
         * @return Boolean of whether the save was successful.
         */
        @Override
        protected Boolean doInBackground(CareProvider... params) {
            return saveCareProvider(params[0]);
        }
    }

    /**
     *  Load a Care Provider from the Elastic Search database.
     */
    public static class LoadCareProviderTask extends AsyncTask<String, Void, CareProvider>{
        /**
         * Loads the Care Provider identified by the username from the database asynchronously.
         * @param params The provider username.
         * @return The provider from the database, or null if no such provider exists.
         */
        @Override
        protected CareProvider doInBackground(String... params) {
            return loadCareProvider(params[0]);
        }
    }

    /**
     *  Delete a Care Provider from the Elastic Search database
     */
    public static class DeleteCareProviderTask extends AsyncTask<String, Void, Boolean>{
        /**
         * Deletes the Care Provider identified by the username from the database asynchronously.
         * @param strings The provider username.
         * @return Boolean of whether the deletion was successful.
         */
        @Override
        protected Boolean doInBackground(String... strings) {
            return deleteCareProvider(strings[0]);
        }
    }


    /**
     * Loads a CareProvider from the Elastic Search database.
     * @deprecated Use the Asynchronous Method in Production.
     * @param username  The username of the client to load.
     * @return The CareProvider account information.
     */
    public static CareProvider loadCareProvider(String username){
        setClient();
        Get get = new Get.Builder(INDEX_NAME, username).type("careprovider").build();
        try{
            JestResult result = client.execute(get);
            if(result.isSucceeded()){
                CareProvider careProvider = result.getSourceAsObject(CareProvider.class);
                return careProvider;
            }
        }catch(IOException e){
            Log.d(cs.ualberta.ca.medlog.helper.Database.class.toString(),
                    String.format("Failed to Load Patient: Username: %s", username));
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Saves a CareProvider to the Elastic Search database.
     * @deprecated Use the Asynchronous Method in Production.
     * @param careProvider The provider to save.
     * @return A boolean if the operation succeeded.
     */
    public static Boolean saveCareProvider(CareProvider careProvider){
        setClient();
        boolean success = false;
        Index index = new Index.Builder(careProvider)
                .index(INDEX_NAME)
                .type("careprovider")
                .id(careProvider.getUsername())
                .build();

        try{
            DocumentResult result = client.execute(index);
            if(result.isSucceeded()){
                success = true;
                Log.d(cs.ualberta.ca.medlog.helper.Database.class.toString(),
                        String.format("Saved Patient: CareProvider: %s",
                                careProvider.getUsername()));
            }else{
                System.out.println(result.getErrorMessage());
            }
        }catch (IOException e){
            e.printStackTrace();
            Log.d(cs.ualberta.ca.medlog.helper.Database.class.toString(),
                    String.format("Failed to Save CareProvider: Username: %s",
                            careProvider.getUsername()));
        }
        return success;
    }

    /**
     * Deletes a CareProvider from the Elastic Search database.
     * @deprecated Use the Asynchronous Method in Production.
     * @param username The provider's username.
     * @return Boolean of whether the deletion was successful.
     */
    public static Boolean deleteCareProvider(String username){
        setClient();
        boolean success = false;
        Delete delete = new Delete.Builder(username).index(INDEX_NAME).type("careprovider").build();
        try{
            DocumentResult result = client.execute(delete);
            if(result.isSucceeded()){
                success = true;
            }else{
                System.out.println(result.getErrorMessage());
            }
        }catch (IOException e){
            e.printStackTrace();
            Log.d(ElasticSearchController.class.getName(), "Failed to delete user with username: " + username);
        }
        return success;
    }

    /**
     *  Checks the connection to the Elastic Search database.
     */
    public static class CheckConnectionTask extends AsyncTask<Void, Void, Boolean>{
        /**
         * Checks the database connection asynchronously.
         * @param voids Ignored completely.
         * @return Boolean of whether the connection was successful.
         */
        @Override
        protected Boolean doInBackground(Void... voids) {
            return checkConnectivity(databaseAddress);
        }
    }


    /**
     * Saves photo data to the Elastic Search database.
     * @deprecated Use the Asynchronous Method in Production.
     * @param photoData The photo byte data.
     * @return The id of the photo in the database, null if it failed.
     */
    public static String savePhotoData(byte[] photoData) {
        setClient();
        try {
            // Try and encrypt the photo before uploading to the server.
            String output = Encryption.encryptData(AppStatus.getInstance().getCurrentUser().getUsername(), photoData);
            System.out.println(output);
            System.out.println(output.length());
            EncryptedPhoto ep = new EncryptedPhoto(output);
            // Get the index and execute the put.
            Index index = new Index.Builder(ep)
                    .index(INDEX_NAME)
                    .type("photo")
                    .build();

            DocumentResult result = client.execute(index);
            if (result.isSucceeded()) {
                Log.d(cs.ualberta.ca.medlog.helper.Database.class.toString(),
                        String.format("Photo has been saved!"));
                return result.getId();
            } else {
                System.out.println("PHOTO DATA ERROR: " + result.getErrorMessage());
            }
        } catch (EncryptionException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Loads photo bytes from the Elastic Search database.
     * @deprecated Use the Asynchronous Method in Production.
     * @param id The photo ID to load.
     * @return The bytes of the photo.
     */
    public static byte[] loadPhoto(String username, String id){
        setClient();
        Get get = new Get.Builder(INDEX_NAME, id).type("photo").build();
        try{
            JestResult result = client.execute(get);
            if(result.isSucceeded()){
                EncryptedPhoto enc = result.getSourceAsObject(EncryptedPhoto.class);
                return Encryption.decryptData(username, enc.getData());
            }
        }catch(EncryptionException | IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Deletes a photo from the Elastic Search database.
     * @deprecated Use the Asynchronous Method in Production.
     * @param id The photo id.
     * @return Boolean of whether the deletion was successful.
     */
    public static Boolean deletePhoto(String id){
        setClient();
        boolean success = false;
        Delete delete = new Delete.Builder(id).index(INDEX_NAME).type("photo").build();
        try{
            DocumentResult result = client.execute(delete);
            if(result.isSucceeded()){
                success = true;
            }else{
                System.out.println(result.getErrorMessage());
            }
        }catch (IOException e){
            e.printStackTrace();
            Log.d(ElasticSearchController.class.getName(), "Failed to delete the photo with ID: " + id);
        }
        return success;
    }


    /**
     *  Saves a Photo to the Elastic Search Database.
     */
    public static class SavePhotoTask extends AsyncTask<byte[], Void, String> {

        /**
         * Saves a Photo to the database asynchronously.
         * @param data The photo byte array.
         * @return The id of the photo on the database.
         */
        @Override
        protected String doInBackground(byte[]... data) {
            return savePhotoData(data[0]);
        }
    }

    /**
     *  Load a Photo from the Elastic Search database.
     */
    public static class LoadPhotoTask extends AsyncTask<String, Void, byte[]>{
        /**
         * Loads a Photo identified by a patient username and id from the database asynchronously.
         * @param params The USERNAME and ID of the photo. MUST SUPPLY BOTH!
         * @return The byte data of the photo from the database.
         * @throws IllegalArgumentException Thrown if the username or id are missing.
         */
        @Override
        protected byte[] doInBackground(String... params) {
            if(params.length != 2){
                throw new IllegalArgumentException("Load Photo requires a username and id!");
            }
            return loadPhoto(params[0], params[1]);
        }
    }

    /**
     *  Delete a Photo from the Elastic Search database.
     */
    public static class DeletePhotoTask extends AsyncTask<String, Void, Boolean>{
        /**
         * Deletes the Photo identified by the id from the database asynchronously.
         * @param strings The ID of the photo.
         * @return Boolean of whether the deletion was successful.
         */
        @Override
        protected Boolean doInBackground(String... strings) {
            return deletePhoto(strings[0]);
        }
    }


    /**
     * Check if connection can be made to the Elastic Search database.
     * @return Boolean of whether connection is successful.
     * @deprecated Use Asynchronous Task Instead
     */
    public static boolean checkConnectivity(String url) {
        try {
            URL urlServer = new URL(url);
            HttpURLConnection urlConn = (HttpURLConnection) urlServer.openConnection();
            urlConn.setConnectTimeout(timeout);
            urlConn.connect();
            if (urlConn.getResponseCode() == 200) {
                return true;
            } else {
                return false;
            }
        } catch (MalformedURLException e1) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }
}
