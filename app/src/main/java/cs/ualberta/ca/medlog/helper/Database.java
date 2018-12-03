package cs.ualberta.ca.medlog.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import cs.ualberta.ca.medlog.controller.ElasticSearchController;
import cs.ualberta.ca.medlog.entity.BodyLocation;
import cs.ualberta.ca.medlog.entity.MapLocation;
import cs.ualberta.ca.medlog.entity.Photo;
import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.Record;
import cs.ualberta.ca.medlog.entity.SearchResult;
import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.exception.UserNotFoundException;

/**
 * <p>
 *     Description: <br>
 *         The purpose of this class is to interact with the ElasticSearchController to store and
 *         retrieve data to be used within the application.
 *
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 * <p>
 *     References: <br>
 *         Android Developer Guide, Determine and monitor the connectivity status
 *         https://developer.android.com/training/monitoring-device-state/connectivity-monitoring#java
 *         Last updated 2018-05-08, accessed 2018-11-09
 * </p>
 *
 * @author Tem Tamre, Thomas Roskewich
 * @version 1.0
 * @see Cache
 */
public class Database {
    public Context context;
    public Cache cache;
    public Codes codes;

    /**
     * Initializes a database object using the provided application context.
     * @param c The application context.
     */
    public Database(Context c){
        this.context = c;
        this.cache = new Cache(context);
        this.codes = new Codes(context);
    }

    /* Getter methods */

    /**
     * Retrieves the context used for this object.
     * @return The context.
     */
    public Context getDatabaseContext() {
        return context;
    }

    /**
     * Retrieves the cache for this database.
     * @return The cache.
     */
    public Cache getDatabaseCache() {
        return cache;
    }

    /**
     * Retrieves the codes object for this database.
     * @return The codes.
     */
    public Codes getDatabaseCodes() {
        return codes;
    }

    /* Setter methods */

    /**
     * Sets the context used for the object to the provided one.
     * @param ctx The new context to use.
     */
    public void setDatabaseContext(Context ctx) {
        this.context = ctx;
    }

    /**
     * Sets the cache used for the object to the provided one.
     * @param che The new cache to use.
     */
    public void setDatabaseCache(Cache che) {
        this.cache = che;
    }

    /**
     * Sets the codes used for the object to the provided one.
     * @param cds The new codes to use.
     */
    public void setDatabaseCodes(Codes cds) {
        this.codes = cds;
    }

    /* Cache methods */

    /**
     * Calls for the database's cache to load a class from its data.
     * @param type The class of the object to be returned.
     * @param <T> The type parameters.
     * @return The object loaded from the cache.
     * @throws UserNotFoundException Thrown if the user can't be loaded.
     * @see Cache
     */
    public <T> T cacheLoad(Class<T> type) throws UserNotFoundException{
        return cache.load(type);
    }

    /**
     * Invocation of cache's save method
     * @see Cache
     */
    public void cacheSave() {
        cache.save();
    }

    /* Codes methods */

    /**
     * Call for the database's codes to retrieve its list of allowed codes.
     * @return ArrayList of allowed codes for the given device.
     * @see Codes
     */
    public ArrayList<String> getLoginCodes() {
        return codes.getCodes();
    }

    /**
     * Call for the database's codes to add the provided code to the allowed list.
     * @param code The code to be added.
     * @see Codes
     */
    public void addLoginCode(String code) {
        codes.addCode(code);
    }

    /**
     * Call for the database's codes to check if the provided code is in the allowed list.
     * @param code The code to be checked.
     * @return Boolean of whether the code exists in the list or not.
     */
    public boolean checkLoginCode(String code) {
        return codes.checkCode(code);
    }

    /* Database methods */

    /**
     * Retrieves a patient from the online database if a connection can be established, otherwise
     * loads the patient from disc.
     * @param username The username of the patient to be retrieved.
     * @return The patient object tied to the username.
     * @throws UserNotFoundException Thrown if the patient cannot be found.
     * @throws ConnectException Thrown if local loading fails and a connection to the database fails.
     */
    public Patient loadPatient(String username) throws UserNotFoundException, ConnectException{
        Patient patient;

        if(username.isEmpty()){
            throw new UserNotFoundException("Users cannot have an empty username.");
        }

        // Load patient from database if there is a connection, from local save if there isn't
        if (checkConnectivity()) {
            try {
                patient = new ElasticSearchController.LoadPatientTask().execute(username).get();
                cache.save();

                if (patient == null) {
                    throw new UserNotFoundException("Patient " + username + " was not found.");
                }

            } catch (Exception e) {  // There was an exception in the async execution
                throw new UserNotFoundException("Patient " + username + " failed to load.");
            }
        } else {
            // Offline mode, try and load the patient from local data
            try {
                patient = cache.load(Patient.class);
            } catch (UserNotFoundException e) {
                throw new ConnectException("Failed to connect to database and could not load the user locally.");
            }
        }
        return patient;
    }

    /**
     * Retrieves the care provider from the online database if a connection can be established,
     * otherwise loads the provider from disc.
     * @param username The username of the provider to be retrieved.
     * @return The care provider object tied to the username.
     * @throws UserNotFoundException Thrown if the provider cannot be found.
     * @throws ConnectException Thrown if local loading fails and a connection to the database fails.
     */
    public CareProvider loadProvider(String username) throws UserNotFoundException, ConnectException {
        CareProvider provider;

        // Load provider from database if there is a connection, from local save if there isn't
        if (checkConnectivity()) {
            try {
                provider = new ElasticSearchController.LoadCareProviderTask().execute(username).get();
                cache.save();

                if (provider == null) {
                    throw new UserNotFoundException("Care Povider " + username + " was not found.");
                }
            } catch (Exception e){
                throw new UserNotFoundException("Failed to load provider.");
            }

        } else {
            try {
                provider = cache.load(CareProvider.class);

            } catch(UserNotFoundException e) {
                throw new ConnectException("Failed to connect to database and could not load the user locally.");
            }
        }

        return provider;
    }

    /**
     * Checks if the given username is used by any patients.
     * @param username The username to check.
     * @return Boolean of whether the username is available or not.
     * @throws ConnectException Thrown if a connection to the database fails.
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
     * Checks if the given username is used by any care providers.
     * @param username The username to check.
     * @return Boolean of whether the username is available or not.
     * @throws ConnectException Thrown if a connection to the database fails.
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
     * Checks if the given username is used by any users.
     * @param username The username to check.
     * @return Boolean of whether the username is available or not.
     * @throws ConnectException Thrown if a connection to the database fails.
     */
    public boolean usernameAvailable(String username) throws ConnectException{
        boolean success = false;

        // Try and load a patient with that username
        try{
            Patient p = loadPatient(username);
        }catch(UserNotFoundException e){
            success = true;
        }

        // Try and load a careprovider with that username
        try{
            CareProvider p = loadProvider(username);
            success = false;
        }catch(UserNotFoundException ignore){ }
        return success;
    }

    /**
     * Saves the given photo to the elastic search database.
     * @param photo The photo to be saved.
     * @return The id of the photo.
     * @throws IllegalArgumentException Thrown if the photo doesn't have a file path.
     * @throws InterruptedException Thrown if the saving is interrupted.
     * @throws IllegalStateException Thrown if the saving cannot be done at the time.
     */
    public String savePhoto(Photo photo) throws IllegalArgumentException, InterruptedException, IllegalStateException{
        File file = new File(photo.getPath());
        if(file.exists()){
            try {
                // Load the file, convert to bitmap, compress as JPEG, and then save the photo on ES
                Bitmap bitmap = photo.getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 35,baos);
                String id =  new ElasticSearchController.SavePhotoTask().execute(baos.toByteArray()).get();
                photo.setId(id);
                return id;
            } catch (ExecutionException e) {
                e.printStackTrace();
                throw new IllegalStateException("File was not found yet initial checks found it!");
            }
        }else{
            throw new IllegalArgumentException("The provided photo does not have a path!");
        }
    }

    /**
     * Loads a given patients photo onto the devices disk.
     * @param username The patients username.
     * @param photo The photo to be loaded.
     * @return The path of the photo on the device.
     * @throws IOException Thrown if the photo could not be loaded.
     */
    public String downloadPhoto(String username, Photo photo) throws IOException{
        File file = new File(photo.getPath());
        if(file.exists()){
            return photo.getPath();
        }else{
            if(checkConnectivity()) {
                try {
                    byte[] data = new ElasticSearchController.LoadPhotoTask().execute(username, photo.getId()).get();
                    if(data != null) {
                        FileOutputStream fos = new FileOutputStream(file.getPath());
                        fos.write(data);
                        fos.close();
                    }else{
                        Log.d(this.getClass().getName(), "Failed to download photo.");
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e){
                    Log.d(this.getClass().getName(), "FileNotFound: This error should not be happening!");
                    e.printStackTrace();
                }
            }else{
                throw new ConnectException("Failed to connect to the database.");
            }
        }
        return null;
    }

    /**
     * Deletes a photo locally and on the elastic search database.
     * @param photo The photo to be deleted.
     * @return Boolean of whether the deletion succeeded.
     * @throws ConnectException Thrown if the database cannot be connected to.
     */
    public boolean deletePhoto(Photo photo) throws ConnectException{
        if(checkConnectivity()){
            try {
                File f = new File(photo.getPath());
                f.delete();
                return new ElasticSearchController.DeletePhotoTask().execute(photo.getId()).get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }else{
            throw new ConnectException("Failed to connect to ES.");
        }
    }

    /**
     * Saves the patient locally and to the elastic search database.
     * @param patient The patient to be saved.
     * @return Boolean of whether the save operation was successful.
     */
    public boolean savePatient(Patient patient){
        cache.save();
        patient.setUpdated();
        if (checkConnectivity()) {
            try {
                return new ElasticSearchController.SavePatientTask().execute(patient).get();
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * Saves the given care provider locally and to the elastic search database.
     * @param provider The provider to be saved.
     * @return Boolean of whether the save operation was successful.
     */
    public boolean saveProvider(CareProvider provider){
        cache.save();
        provider.setUpdated();

        if (checkConnectivity()) {
            try {
                return new ElasticSearchController.SaveCareProviderTask().execute(provider).get();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * Deletes a patient from the elastic search database.
     * @param username The username of the patient to be deleted.
     * @return Boolean of whether the deletion was successful.
     * @throws ConnectException Thrown if no connection to the database could be made.
     */
    public Boolean deletePatient(String username) throws ConnectException{
        if (checkConnectivity()) {
            try {
                return new ElasticSearchController.DeletePatientTask().execute(username).get();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }else{
            throw new ConnectException("Failed to connect to the server for deletion.");
        }
    }

    /**
     * Deletes a care provider from the elastic search database.
     * @param username The username of the provider to be deleted.
     * @return Boolean of whether the deletion was successful.
     * @throws ConnectException Thrown if no connection to the database could be made.
     */
    public Boolean deleteProvider(String username) throws ConnectException{
        if (checkConnectivity()) {
            try {
                return new ElasticSearchController.DeleteCareProviderTask().execute(username).get();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            throw new ConnectException("Failed to connect to the server for deletion.");
        }
    }

    /**
     * Updates a given patient to match the data from the elastic search database.
     * @param patient The patient to be updated.
     * @return Boolean of whether the update was successful.
     * @throws ConnectException Thrown if no connection to the database could be made.
     */
    public Boolean updatePatient(Patient patient) throws ConnectException{
        cache.save();
        patient.setUpdated();
        if (checkConnectivity()) {
            try {
                return new ElasticSearchController.SavePatientTask().execute(patient).get();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }else{
            throw new ConnectException("Failed to connect to the server for patient updating.");
        }
    }

    /**
     * Updates a given care provider to match the data from the elastic search database.
     * @param careProvider The provider to update information for.
     * @return Boolean of whether the operation was successful.
     * @throws ConnectException Thrown if no connection to the database could be made.
     */
    public Boolean updateCareProvider(CareProvider careProvider) throws ConnectException{
        cache.save();
        careProvider.setUpdated();

        if (checkConnectivity()) {
            try {
                return new ElasticSearchController.SaveCareProviderTask().execute(careProvider).get();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            throw new ConnectException("Failed to connect to the server for care provider updating.");
        }
    }

    /**
     * Search through patient problems and see if it matches any keywords.
     * @param username The username of the patient to search.
     * @param keywords The keywords we are looking for. Can be null.
     * @param map The map location we are looking for. Can be null.
     * @param bl The body location we are looking for. Can be null.
     * @return An ArrayList of search results that match our search.
     * @throws UserNotFoundException Thrown if the patient can't be found.
     * @throws ConnectException Thrown if the patient can't be found locally and can't be loaded.
     */
    public ArrayList<SearchResult> searchPatient(String username, ArrayList<String> keywords, MapLocation map, BodyLocation bl) throws UserNotFoundException, ConnectException{
        Patient p = loadPatient(username);
        return searchPatient(p, keywords, map, bl);
    }

    /**
     * Search through patient problems and see if it matches any keywords.
     * @param patient The patient to search through.
     * @param keywords The keywords we are looking for. Can be null.
     * @param map The map location we are looking for. Can be null.
     * @param bl The body location we are looking for. Can be null.
     * @return An ArrayList of search results that match our search.
     */
    public ArrayList<SearchResult> searchPatient(Patient patient, ArrayList<String> keywords, MapLocation map, BodyLocation bl){
        ArrayList<SearchResult> output = new ArrayList<>();

        if(keywords != null) {
            for (int i = 0; i < keywords.size(); i++) {
                keywords.set(i, keywords.get(i).toLowerCase());
            }
        }

        for(Problem p : patient.getProblems()){
            ArrayList<String> problemStrings = new ArrayList<>();
            // If the keywords match the title or the description, add it.
            problemStrings.addAll(Arrays.asList(p.getDescription().toLowerCase().split(" ")));
            problemStrings.addAll(Arrays.asList(p.getTitle().toLowerCase().split(" ")));

            // If the problem strings contains all keywords, we add it to the list.
            if(keywords != null) {
                if (problemStrings.containsAll(keywords)) {
                    output.add(new SearchResult(patient, p, null));
                }
            }

            // If the record contains the map location or the body description, add it and stop.
            for(Record r : p.getRecords()){
                ArrayList<String> recordStrings = new ArrayList<>();
                if(r.getTitle() != null) {
                    recordStrings.addAll(Arrays.asList(r.getTitle().toLowerCase().toLowerCase().split(" ")));
                }

                if(r.getComment() != null) {
                    recordStrings.addAll(Arrays.asList(r.getComment().toLowerCase().toLowerCase().split(" ")));
                }

                // If the record strings contain all the keywords.
                if(keywords != null) {
                    if (!recordStrings.containsAll(keywords)) {
                        continue;
                    }
                }

                // If there is a map we are searching for and the record has a map location
                if(map != null) {
                    if(r.getMapLocation() != null) {
                        if(!r.getMapLocation().isNear(map)) {
                            continue;
                        }
                    }
                    else {
                        continue;
                    }
                }

                // If there is a body location we are searching for and a record has a body location
                if(bl != null) {
                    if (r.getBodyLocation() != null) {
                        if(!r.getBodyLocation().isNear(bl)) {
                            continue;
                        }
                    }
                    else {
                        continue;
                    }
                }

                output.add(new SearchResult(patient,p,r));
            }
        }
        return output;
    }

    /**
     * Search all care provider patients for all keywords, map, or body location.
     * @param username The username of the care provider.
     * @param keywords  The keywords, can be null.
     * @param map The Map location, can be null.
     * @param bl The body location, can be null.
     * @return A list of search results that match all of the keywords, map, or body location.
     * @throws UserNotFoundException Thrown if the provider can't be found.
     * @throws ConnectException Thrown if the provider can't be found locally and can't be loaded.
     */
    public ArrayList<SearchResult> searchCareProvider(String username, ArrayList<String> keywords, MapLocation map, BodyLocation bl) throws UserNotFoundException, ConnectException{
        CareProvider careProvider = loadProvider(username);
        return searchCareProvider(careProvider, keywords, map, bl);
    }

    /**
     * Search all care provider patients for all keywords, map, or body location.
     * @param careProvider The care provider to search for
     * @param keywords  The keywords, can be null.
     * @param map The Map location, can be null.
     * @param bl The body location, can be null.
     * @return A list of search results that match all of the keywords, map, or body location.
     */
    public ArrayList<SearchResult> searchCareProvider(CareProvider careProvider, ArrayList<String> keywords, MapLocation map, BodyLocation bl){
        ArrayList<SearchResult> problems = new ArrayList<>();
        for(Patient p : careProvider.getPatients()){
            problems.addAll(searchPatient(p, keywords, map, bl));
        }
        return problems;
    }

    /**
     * Checks if a connection to the elastic search database is possible.
     * @return Boolean of whether the connection can be established.
     */
    public boolean checkConnectivity() {
        try {
            return new ElasticSearchController.CheckConnectionTask().execute().get();
        }catch(Exception e){
            return false;
        }
    }
}
