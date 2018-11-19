package cs.ualberta.ca.medlog.controller;

import android.content.Context;
import java.util.Calendar;
import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.Record;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.helper.Database;

/**
 * <p>
 *     Description: <br>
 *         The controller for all changes made to a Problem. This is used by the gui to change
 *         Patient problem details.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.0
 * @see Problem
 */
public class ProblemController {
    private Database database;

    public ProblemController(Context ctx){
        database = new Database(ctx);
    }

    public void setTitle(Patient owner, Problem problem, String newTitle) {
        try{
            problem.setTitle(newTitle);
            database.updatePatient(owner);
        }catch(Exception ignore){
        }
    }

    public void setDate(Patient owner, Problem problem, Calendar newDate) {
        try {
            problem.setDate(newDate.getTime());
            database.updatePatient(owner);
        }catch(Exception ignore){
        }
    }

    public void setDesc(Patient owner, Problem problem, String newDesc) {
        try{
            problem.setDescription(newDesc);
            database.updatePatient(owner);
        }catch(Exception ignore){
        }
    }

    public void addRecord(Patient owner, Problem problem, Record newRecord) {
        try{
            problem.addRecord(newRecord);
            database.updatePatient(owner);
        }catch(Exception ignore){
        }
    }
}
