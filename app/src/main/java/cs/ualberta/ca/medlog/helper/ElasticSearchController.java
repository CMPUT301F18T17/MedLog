package cs.ualberta.ca.medlog.helper;
import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import org.w3c.dom.Document;

import java.io.IOException;
import cs.ualberta.ca.medlog.entity.user.Patient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;

public class ElasticSearchController {

    private static String databaseAddress = "http://cmput301.softwareprocess.es:8080";
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
            setClient();
            boolean success = false;
            Patient patient = params[0];
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
            setClient();
            Get get = new Get.Builder(INDEX_NAME, params[0]).type("patient").build();
            try{
                JestResult result = client.execute(get);
                if(result.isSucceeded()){
                    Patient patient = result.getSourceAsObject(Patient.class);
                    return patient;
                }
            }catch(IOException e){
                Log.d(cs.ualberta.ca.medlog.helper.Database.class.toString(),
                        String.format("Failed to Load Patient: Username: %s", params[0]));
                e.printStackTrace();
            }
            return null;
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
            setClient();
            boolean success = false;
            Delete delete = new Delete.Builder(strings[0]).index(INDEX_NAME).type("patient").build();
            try{
                DocumentResult result = client.execute(delete);
                if(result.isSucceeded()){
                    success = true;
                }
            }catch (IOException e){
                e.printStackTrace();
                Log.d(ElasticSearchController.class.getName(), "Failed to delete user with username: " + strings[0]);
            }
            return success;
        }
    }
}
