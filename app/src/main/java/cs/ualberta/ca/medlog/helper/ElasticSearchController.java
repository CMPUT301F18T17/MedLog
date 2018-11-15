/**
 *
 * <h1>
 *     Elastic Search Controller
 * </h1>
 *
 *  <p>
 *     Description: <br>
 *         The purpose of this class is to interact directly with the Elastic Search server and query information.
 *
 * </p>
 *
 * @author Thomas Roskewich
 * @contact roskewic@ualberta.ca
 * @see cs.ualberta.ca.medlog.helper.Database
 */

package cs.ualberta.ca.medlog.helper;
import android.os.AsyncTask;
import android.util.Log;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;
import java.io.IOException;
import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.entity.user.Patient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Update;

public class ElasticSearchController {

    public static String databaseAddress = "http://cmput301.softwareprocess.es:8080";
    private static JestDroidClient client = null;
    private static final String INDEX_NAME = "cmput301f18t17";


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
     *  Saves a patient to the Elastic Search Database
     */
    public static class SavePatientTask extends AsyncTask<Patient, Void, Boolean> {
        /**
         * Saves the patient provided asynchronously
         * @param params the patient
         * @return if the operation succeeded.
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
         * Loads the patient provided asynchronously
         * @param params the username of the patient
         * @return the patient from the database, or null if invalid
         */
        @Override
        protected Patient doInBackground(String... params) {
            return loadPatient(params[0]);
        }
    }

    /**
     *  Delete a patient from the Elastic Search database
     */
    public static class DeletePatientTask extends AsyncTask<String, Void, Boolean>{
        /**
         * Deletes the patient provided asynchronously
         * @param strings The username of the patient.
         * @return if the operation succeeded.
         */
        @Override
        protected Boolean doInBackground(String... strings) {
            return deletePatient(strings[0]);
        }
    }


    /**
     * <p>Loads a Patient from elastic search.</p>
     * @deprecated Use the Asynchronous Method in Production.
     * @param username  The username of the client to load.
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
     * <p>Saves the patient to the elastic search server.</p>
     * @deprecated Use the Asynchronous Method in Production.
     * @param patient The patient to save.
     * @return A boolean if the operation succeeded.
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
     * <p>Deletes a patient from the elastic search server.</p>
     * @deprecated Use the Asynchronous Method in Production.
     * @param username The patients username to delete.
     * @return A boolean if the operation succeeded.
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
         * Saves the Care Provider provided asynchronously
         * @param params the Care Provider
         * @return if the operation succeeded.
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
         * Loads the Care Provider provided asynchronously
         * @param params the username of the Care Provider
         * @return the Care Provider from the database, or null if invalid
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
         * Deletes theCare Provider provided asynchronously
         * @param strings The username of the Care Provider.
         * @return if the operation succeeded.
         */
        @Override
        protected Boolean doInBackground(String... strings) {
            return deleteCareProvider(strings[0]);
        }
    }


    /**
     * <p>Loads a CareProvider from elastic search.</p>
     * @deprecated Use the Asynchronous Method in Production.
     * @param username  The username of the client to load.
     * @return The Patients account information.
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
     * <p>Saves a CareProvider to the elastic search server.</p>
     * @deprecated Use the Asynchronous Method in Production.
     * @param careProvider The patient to save.
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
     * <p>Deletes a CareProvider from the elastic search server.</p>
     * @deprecated Use the Asynchronous Method in Production.
     * @param username The patients username to delete.
     * @return A boolean if the operation succeeded.
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
     *  Update a Patient from the Elastic Search database.
     */
    public static class UpdatePatientTask extends AsyncTask<Patient, Patient, Boolean>{
        /**
         * Updates the patient provided
         * @param patient the patient to update
         * @return if the operation succeeded.
         */
        @Override
        protected Boolean doInBackground(Patient... patient) {
            return updatePatient(patient[0].getUsername(), patient[0]);
        }
    }

    /**
     *  Update a Care Provider from the Elastic Search database.
     */
    public static class UpdateCareProviderTask extends AsyncTask<CareProvider, Patient, Boolean>{
        /**
         * Updates the provided Care Provider.
         * @param careProviders the care provider to update.
         * @return if the operation succeeded.
         */
        @Override
        protected Boolean doInBackground(CareProvider... careProviders) {
            return updateCareProvider(careProviders[0].getUsername(), careProviders[0]);
        }
    }


    /**
     * <p>Updates a CareProvider to the elastic search server.</p>
     * @deprecated Use the Asynchronous Method in Production.
     * @param username The patients username to delete.
     * @return A boolean if the operation succeeded.
     */
    public static Boolean updateCareProvider(String username, CareProvider provider){
        setClient();
        boolean success = false;
        Update update = new Update.Builder(provider)
                .index(INDEX_NAME)
                .type("careprovider")
                .id(username).build();
        try{
            DocumentResult result = client.execute(update);
            if(result.isSucceeded()){
                success = true;
            }else{
                System.out.println(result.getErrorMessage());
            }
        }catch (IOException e){
            e.printStackTrace();
            Log.d(ElasticSearchController.class.getName(), "Failed to update user with username: " + username);
        }
        return success;
    }

    /**
     * <p>Updates a Patient to the elastic search server.</p>
     * @deprecated Use the Asynchronous Method in Production.
     * @param username The patients username to delete.
     * @return A boolean if the operation succeeded.
     */
    public static Boolean updatePatient(String username, Patient patient){
        setClient();
        boolean success = false;
        Update update = new Update.Builder(patient)
                .index(INDEX_NAME)
                .type("patient")
                .id(username).build();
        try{
            DocumentResult result = client.execute(update);
            if(result.isSucceeded()){
                success = true;
            }else{
                System.out.println(result.getErrorMessage());
            }
        }catch (IOException e){
            e.printStackTrace();
            Log.d(ElasticSearchController.class.getName(), "Failed to update user with username: " + username);
        }
        return success;
    }

    /*
    public static ArrayList<Problem> searchProblems(String username, String query){
        setClient();
        boolean success = false;
        Search search = new Search.Builder(query).addIndex(INDEX_NAME).addType("patient").build();
        try{
            SearchResult result = client.execute(search);
        }catch(IOException e){
            e.printStackTrace();
            Log.d(ElasticSearchController.class.getName(), "Failed search: " + username);
        }
        return null;
    }
    */
}
