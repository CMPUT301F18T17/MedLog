package cs.ualberta.ca.medlog.helper;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import cs.ualberta.ca.medlog.entity.user.Patient;
import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;

public class ElasticSearchController {

    private static String databaseAddress = "http://cmput301.softwareprocess.es:8080/";
    private static JestDroidClient client = null;
    private static final String INDEX_NAME = "cmput301f18t17";



    private static void setClient(){
        if(client == null){
            DroidClientConfig config = new DroidClientConfig.Builder(databaseAddress).build();
            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }

    /**
     * <h1>
     *     SavePatientTask
     * </h1>
     *
     *  <p>
     *     Description: <br>
     *         The purpose of this class is to allow for asynchronous patient saving and user
     *         control.
     *
     * </p>
     *
     * @author Thomas Roskewich
     * @contact roskewic@ualberta.ca
     * @see cs.ualberta.ca.medlog.helper.Database
     */
    public static class SavePatientTask extends AsyncTask<Patient, Void, Boolean> {
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
     * <h1>
     *     LoadPatientTask
     * </h1>
     *
     *  <p>
     *     Description: <br>
     *         The purpose of this class is to allow for asynchronous patent fetching and user
     *         control.
     *
     * </p>
     *
     * @author Thomas Roskewich
     * @contact roskewic@ualberta.ca
     * @see cs.ualberta.ca.medlog.helper.Database
     */
    public static class LoadPatientTask extends AsyncTask<String, Void, Patient>{
        @Override
        protected Patient doInBackground(String... params) {
            setClient();
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchQuery("username", params[0]));
            Search search = new Search.Builder(params[0])
                    .addIndex(INDEX_NAME)
                    .addType("patient")
                    .build();
            try{
                JestResult result = client.execute(search);
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
}
