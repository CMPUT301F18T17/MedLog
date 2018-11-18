/**
 *
 * <h1>
        Database
 * </h1>
 *
 *  <p>
 *     Description: <br>
 *         The purpose of this class is to interact with the ElasticSearchController to store and
 *         retrieve data to be used within the application.
 *
 * </p>
 *
 * <p>
 *     References: <br>
 *
 *         Android Developer Guide, Determine and monitor the connectivity status
 *         https://developer.android.com/training/monitoring-device-state/connectivity-monitoring#java
 *         Last updated 2018-05-08, accessed 2018-11-09
 * </p>
 *
 * @author Tem Tamre, Thomas Roskewich
 * @contact ttamre@ualberta.ca, roskewic@ualberta.ca
 * @see cs.ualberta.ca.medlog.helper.FileSaver
 */

package cs.ualberta.ca.medlog.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;

import cs.ualberta.ca.medlog.entity.BodyLocation;
import cs.ualberta.ca.medlog.entity.MapLocation;
import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.Record;
import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.exception.UserNotFoundException;

public class Database {
    public Context context;

    public Database(Context c){
        this.context = c;
    }

    public Context getDatabaseContext() {
        return context;
    }


    /**
     * <p>Get a patient from the database if a connection can be established, load from disc otherwise</p>
     * @return patient (Patient that was retrieved or loaded)
     * @throws UserNotFoundException if the user cannot be found.
     * @throws ConnectException if we could not connect to the database and could not load the user locally.
     */
    public Patient loadPatient(String username) throws UserNotFoundException, ConnectException{
        Patient patient = null;
        if(username.isEmpty()){ throw new UserNotFoundException("Users cannot have an empty username."); }
        // Check if there is connectivity
        if (checkConnectivity()) {
            try {
                // If there is, try to load a patient. If it returns null, user was not found.
                patient = new ElasticSearchController.LoadPatientTask().execute(username).get();
                if(patient == null){
                    throw new UserNotFoundException("Patient " + username + " was not found.");
                }
            }catch(Exception e){
                // There was an exception in the async execution
                Log.d(Database.class.getName(), "Failed to load user: " + username);
                throw new UserNotFoundException("Patient " + username + " failed to load.");
            }
        } else {

            try {
                // Offline mode, try and load the patient from local data
                FileSaver saver = new FileSaver(context);
                patient = saver.loadPatient();
            }catch(UserNotFoundException e){
                throw new ConnectException("Failed to connect to database and could not load the user locally.");
            }
        }

        return patient;
    }


    /**
     * <p>Get a provider from the database if a connection can be established, load from disc otherwise</p>
     * @return provider (Provider that was retrieved or loaded)
     * @throws UserNotFoundException if the user cannot be found.
     * @throws ConnectException if we could not connect to the database and could not load the user locally.
     */
    public CareProvider loadProvider(String username) throws UserNotFoundException, ConnectException {
        CareProvider provider = null;
        if (checkConnectivity()) {
            try {
                provider = new ElasticSearchController.LoadCareProviderTask().execute(username).get();
                if(provider == null){
                    throw new UserNotFoundException("Care Povider " + username + " was not found.");
                }
            } catch (Exception e){
                throw new UserNotFoundException("Failed to load provider.");
            }
        } else {
            try {
                FileSaver saver = new FileSaver(context);
                provider = saver.loadCareProvider();
            }catch(UserNotFoundException e){
                throw new ConnectException("Failed to connect to database and could not load the user locally.");
            }
        }

        return provider;
    }

    /**
     * <p>Check if the username is available.</p>
     * @param username The username to check.
     * @return If the username is available to take.
     * @throws ConnectException If we cannot connect to the database.
     */
    public boolean patientUsernameAvailable(String username) throws ConnectException{
        boolean success = false;
        try{
            Patient p = loadPatient(username);
        }catch(UserNotFoundException e){
            success = true;
        }
        return success;
    }

    /**
     * <p>Check if the username is available.</p>
     * @param username The username to check.
     * @return If the username is available to take.
     * @throws ConnectException If we cannot connect to the database.
     */
    public boolean providerUsernameAvailable(String username) throws ConnectException{
        boolean success = false;
        try{
            CareProvider p = loadProvider(username);
        }catch(UserNotFoundException e){
            success = true;
        }
        return success;
    }


    /**
     * <p>Push a patient to the database if a connection can be established, save to disc otherwise</p>
     * @param patient Patient to be saved
     * @return Boolean if the save operation succeeded.
     */
    public boolean savePatient(Patient patient){
        patient.setUpdated();
        if (checkConnectivity()) {
            try {
                return new ElasticSearchController.SavePatientTask().execute(patient).get();
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        } else {
            FileSaver saver = new FileSaver(context);
            saver.savePatient(patient);
            return true;
        }
    }


    /**
     * <p>Push a provider to the database if a connection can be established, save to disc otherwise</p>
     * @param provider Provider to be saved
     */
    public boolean saveProvider(CareProvider provider){
        provider.setUpdated();
        if (checkConnectivity()) {
            try {
                return new ElasticSearchController.SaveCareProviderTask().execute(provider).get();
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        } else {
            FileSaver saver = new FileSaver(context);
            saver.saveCareProvider(provider);
            return true;
        }
    }

    /**
     * <p>Deletes a patient from the database. If there is no connection, throws a ConnectException</p>
     * @param username The username of the patient to remove
     * @return Boolean whether the operation succeeded.
     * @throws ConnectException if we cannot connect to the database.
     */
    public Boolean deletePatient(String username) throws ConnectException{
        if(checkConnectivity()){
            try {
                return new ElasticSearchController.DeletePatientTask().execute(username).get();
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }else{
            throw new ConnectException("Failed to connect to the server for deletion.");
        }
    }

    /**
     * <p>Updates a patient form the database. If there is no connection, throws a connection exception</p>
     * @param patient The patient to update information for.
     * @return If the operation was a success.
     * @throws ConnectException if we cannot connect to the database.
     */
    public Boolean updatePatient(Patient patient) throws ConnectException{
        patient.setUpdated();
        if(checkConnectivity()){
            try {
                return new ElasticSearchController.SavePatientTask().execute(patient).get();
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }else{
            throw new ConnectException("Failed to connect to the server for patient updating.");
        }
    }

    /**
     * <p>Updates a CareProvider form the database. If there is no connection, throws a connection exception</p>
     * @param careProvider The patient to update information for.
     * @return If the operation was a success.
     * @throws ConnectException if we cannot connect to the database.
     */
    public Boolean updateCareProvider(CareProvider careProvider) throws ConnectException{
        careProvider.setUpdated();
        if(checkConnectivity()){
            try {
                return new ElasticSearchController.SaveCareProviderTask().execute(careProvider).get();
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }else{
            throw new ConnectException("Failed to connect to the server for care provider updating.");
        }
    }

    /**
     * <p>Deletes a care provider from the database. If there is no connection, throws a ConnectException</p>
     * @param username The username of the patient to remove
     * @return Boolean whether the operation succeeded.
     */
    public Boolean deleteProvider(String username) throws ConnectException{
        if(checkConnectivity()){
            try {
                return new ElasticSearchController.DeleteCareProviderTask().execute(username).get();
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }else{
            throw new ConnectException("Failed to connect to the server for deletion.");
        }
    }

    /**
     * <p>Search through patient problems and see if it matches any keywords.</p>
     * @param username The username of the patient to search.
     * @param keywords The keywords we are looking for. Can be null.
     * @param map The map location we are looking for. Can be null.
     * @param bl The body location we are looking for. Can be null.
     * @return An ArrayList of problems that match our search.
     * @throws UserNotFoundException if the patient cannot be found.3
     * @throws ConnectException if the database could not be loaded and the user is not found locally.
     */
    public ArrayList<Problem> searchPatient(String username, ArrayList<String> keywords, MapLocation map, BodyLocation bl) throws UserNotFoundException, ConnectException{
        Patient p = loadPatient(username);
        return searchPatient(p, keywords, map, bl);
    }

    /**
     * <p>Search through patient problems and see if it matches any keywords.</p>
     * @param patient The patient to search through.
     * @param keywords The keywords we are looking for. Can be null.
     * @param map The map location we are looking for. Can be null.
     * @param bl The body location we are looking for. Can be null.
     * @return An ArrayList of problems that match our search.
     */
    public ArrayList<Problem> searchPatient(Patient patient, ArrayList<String> keywords, MapLocation map, BodyLocation bl){
        ArrayList<Problem> output = new ArrayList<>();
        for(int i = 0; i < keywords.size(); i++){
            keywords.set(i, keywords.get(i).toLowerCase());
        }
        for(Problem p : patient.getProblems()){
            boolean added = false;
            ArrayList<String> strings = new ArrayList<>();
            // If the keywords match the title or the description, add it.
            strings.addAll(Arrays.asList(p.getDescription().toLowerCase().split(" ")));
            strings.addAll(Arrays.asList(p.getTitle().toLowerCase().split(" ")));

            // If the record contains the map location or the body description, add it and stop.
            for(Record r : p.getRecords()){
                strings.addAll(Arrays.asList(r.getComment().toLowerCase().toLowerCase().split(" ")));
                strings.addAll(Arrays.asList(r.getTitle().toLowerCase().toLowerCase().split(" ")));
                if(map != null && r.getMapLocation() != null){
                    if(r.getMapLocation().equals(map)){
                        output.add(p);
                        added = true;
                        break;
                    }
                }

                if(bl != null && r.getBodyLocation() != null){
                    if(r.getBodyLocation().equals(bl)){
                        output.add(p);
                        added = true;
                        break;
                    }
                }
            }

            // If we added it already, we can skip the rest of the code. Do not want duplicates!
            if(added){
                continue;
            }

            // If the strings contains all keywords, we add it to the list.
            if(keywords != null) {
                if (strings.containsAll(keywords)) {
                    output.add(p);
                }
            }
        }
        return output;
    }

    /**
     * <p>Search all care provider patients for all keywords, map, or body location.</p>
     * @param careProvider The care provider to search for
     * @param keywords  The keywords, can be null.
     * @param map The Map location, can be null.
     * @param bl The body location, can be null.
     * @return A list of problems that match all of the keywords, map, or body location.
     */
    public ArrayList<Problem> searchCareProvider(CareProvider careProvider, ArrayList<String> keywords, MapLocation map, BodyLocation bl){
        ArrayList<Problem> problems = new ArrayList<>();
        for(Patient p : careProvider.getPatients()){
            ArrayList<Problem> out = searchPatient(p, keywords, map, bl);
            problems.addAll(searchPatient(p, keywords, map, bl));
        }
        return problems;
    }

    /**
     * <p>Search all care provider patients for all keywords, map, or body location.</p>
     * @param username The username of the care provider.
     * @param keywords  The keywords, can be null.
     * @param map The Map location, can be null.
     * @param bl The body location, can be null.
     * @return A list of problems that match all of the keywords, map, or body location.
     * @throws UserNotFoundException if the care provider cannot be found.
     * @throws ConnectException if the database could not be loaded and the user is not found locally.
     */
    public ArrayList<Problem> searchCareProvider(String username, ArrayList<String> keywords, MapLocation map, BodyLocation bl) throws UserNotFoundException, ConnectException{
        CareProvider careProvider = loadProvider(username);
        return searchCareProvider(careProvider, keywords, map, bl);
    }

    /**
     * <p>Check if we can connect to the Elastic Search server</p>
     * @return True if a connection can be established, false otherwise
     */
    public boolean checkConnectivity() {
        try {
            return new ElasticSearchController.CheckConnectionTask().execute().get();
        }catch(Exception e){
            return false;
        }
    }
}
