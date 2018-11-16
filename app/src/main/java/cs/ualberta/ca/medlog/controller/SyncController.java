/**
 *
 * <h1>
 *     SyncController
 * </h1>
 *
 *  <p>
 *     Description: <br>
 *         The purpose of this class is to sync changes between offline and online.
 *  </p>
 *
 * @author Thomas Roskewich
 * @contact roskewic@ualberta.ca
 */

//TODO: FINISH THE TWO FUNCTIONS!
package cs.ualberta.ca.medlog.controller;

import android.content.Context;
import android.util.Log;

import java.net.ConnectException;

import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.entity.user.User;
import cs.ualberta.ca.medlog.exception.UserNotFoundException;
import cs.ualberta.ca.medlog.helper.Database;
import cs.ualberta.ca.medlog.helper.FileSaver;

public class SyncController {

    private Context ctx;
    private Database dbs;

    public SyncController(Context ctx){
        this.ctx = ctx;
        this.dbs = new Database(ctx);
    }

    /**
     * Sync a patient between the local and remote versions.
     * @param username The username of the patient
     * @throws ConnectException If unable to connect to the server
     * @throws IllegalStateException If we have a local copy but a remote one does not exist
     */
    public void syncPatient(String username) throws ConnectException, IllegalStateException{
        // Check if we are connected.
        if(dbs.checkConnectivity()){
            FileSaver fs = new FileSaver(ctx);

            // Try and load the user locally
            Patient local, remote;
            try {
                local = fs.loadPatient();
                if(local == null) { return; }
                if(!local.getUsername().equals(username)){
                    // There is some trickery going on.
                    // TODO: FIX THE TRICKERY
                    // So, if the username doesn't match, we have changing accounts.
                    return;
                }
            }catch(UserNotFoundException e){
                // User does not have a cached copy locally. We can ignore the sync!
                return;
            }

            try{
                // Try load the patient from the dbs
                remote = dbs.loadPatient(username);
            }catch(UserNotFoundException e){
                throw new IllegalStateException("Have a local user on file, but the remote database does not know of it (offline registration?)");
            }

            // Check for conflict resolution
            if(remote.equals(local)){ return; }

            // There is a conflict, most likely the local client is most up to date.
            // Update remote patient data.
            if(determineIfUpdateRequired(local, remote)) {
                if (dbs.updatePatient(local)) {
                    Log.d(SyncController.class.getName(), String.format("The patient %s was updated successfully!", local.getUsername()));
                } else {
                    Log.w(SyncController.class.getName(), String.format("Failed to update user %s!", username));
                }
            }
        }else {
            throw new ConnectException("Could not connect to the database!");
        }
    }


    /**
     * Sync a provider between the local and remote versions.
     * @param username The username of the care provider
     * @throws ConnectException If unable to connect to the server
     * @throws IllegalStateException If we have a local copy but a remote one does not exist
     */
    public void syncCareProvider(String username) throws ConnectException, IllegalStateException{
        // Check if we are connected.
        if(dbs.checkConnectivity()){
            FileSaver fs = new FileSaver(ctx);

            // Try and load the user locally
            CareProvider local, remote;
            try {
                local = fs.loadCareProvider();
                if(local == null) { return; }
                if(!local.getUsername().equals(username)){
                    // There is some trickery going on.
                    // TODO: FIX THE TRICKERY
                    // So, if the username doesn't match, we have changing accounts.
                    return;
                }
            }catch(UserNotFoundException e){
                // User does not have a cached copy locally. We can ignore the sync!
                return;
            }
            try{
                // Try load the patient from the dbs
                remote = dbs.loadProvider(username);
            }catch(UserNotFoundException e){
                throw new IllegalStateException("Have a local user on file, but the remote database does not know of it (offline registration?)");
            }

            // Check for conflict resolution
            if(remote.equals(local)){ return; }

            // There is a conflict, most likely the local client is most up to date.
            // Update remote patient data.
            if(determineIfUpdateRequired(local, remote)) {
                if (dbs.updateCareProvider(local)) {
                    Log.d(SyncController.class.getName(), String.format("The care provider %s was updated successfully!", local.getUsername()));
                } else {
                    Log.w(SyncController.class.getName(), String.format("Failed to update user %s!", username));
                }
            }
        }else {
            throw new ConnectException("Could not connect to the database!");
        }
    }

    /**
     * Determine if the server needs to update based off the local client.
     * @param local The local file user.
     * @param remote The remote file user.
     * @return If the server needs to update.
     */
    private Boolean determineIfUpdateRequired(User local, User remote){
        if(local.getLastUpdated().after(remote.getLastUpdated())){
            return true;
        }
        return false;
    }

}
