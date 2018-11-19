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

    /**
     * The controller method for the gui to request a problem's title be changed.
     * @param owner The patient who owns the given problem.
     * @param problem The problem to be changed.
     * @param newTitle The new problem title to be set.
     */
    public void setTitle(Patient owner, Problem problem, String newTitle) {
        try{
            problem.setTitle(newTitle);
            database.updatePatient(owner);
        }catch(Exception ignore){
        }
    }

    /**
     * The controller method for the gui to request a problem's date be changed.
     * @param owner The patient who owns the given problem.
     * @param problem The problem to be changed.
     * @param newDate The new problem date to be set.
     */
    public void setDate(Patient owner, Problem problem, Calendar newDate) {
        try {
            problem.setDate(newDate.getTime());
            database.updatePatient(owner);
        }catch(Exception ignore){
        }
    }

    /**
     * The controller method for the gui to request a problem's description be changed.
     * @param owner The patient who owns the given problem.
     * @param problem The problem to be changed.
     * @param newDesc The new problem description to be set.
     */
    public void setDesc(Patient owner, Problem problem, String newDesc) {
        try{
            problem.setDescription(newDesc);
            database.updatePatient(owner);
        }catch(Exception ignore){
        }
    }

    /**
     * The controller method for the gui to request that a record is added to a problem's records.
     * @param owner The patient who owns the given problem.
     * @param problem The problem to be changed.
     * @param newRecord The record to be added to the problem.
     */
    public void addRecord(Patient owner, Problem problem, Record newRecord) {
        try{
            problem.addRecord(newRecord);
            database.updatePatient(owner);
        }catch(Exception ignore){
        }
    }
}
