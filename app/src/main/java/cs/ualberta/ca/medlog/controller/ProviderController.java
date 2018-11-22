package cs.ualberta.ca.medlog.controller;

import android.content.Context;
import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.helper.Database;

/**
 * <p>
 *     Description: <br>
 *         The controller for all changes made to a Care Provider. This is used by the gui to change
 *         Provider information in the model.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.0
 * @see CareProvider
 */
public class ProviderController {
    private Database database;

    public ProviderController(Context ctx){
        database = new Database(ctx);
    }

    /**
     * The controller method for the gui to request that a patient is added to a care provider's
     * assigned patient's.
     * @param provider The care provider who the patient is assigned to.
     * @param newPatient The patient who is assigned.
     */
    public void addPatient(CareProvider provider, Patient newPatient){
        try{
            provider.getPatients().add(newPatient);
            database.saveProvider(provider);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
