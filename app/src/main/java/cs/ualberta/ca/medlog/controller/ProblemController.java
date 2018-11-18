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
            owner = database.loadPatient(owner.getUsername());
            problem = getUpdatedProblem(owner,problem);
            problem.setTitle(newTitle);
            database.savePatient(owner);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setDate(Patient owner, Problem problem, Calendar newDate) {
        try {
            owner = database.loadPatient(owner.getUsername());
            problem = getUpdatedProblem(owner,problem);
            problem.setDate(newDate.getTime());
            database.savePatient(owner);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setDesc(Patient owner, Problem problem, String newDesc) {
        try{
            owner = database.loadPatient(owner.getUsername());
            problem = getUpdatedProblem(owner,problem);
            problem.setDescription(newDesc);
            database.savePatient(owner);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addRecord(Patient owner, Problem problem, Record newRecord) {
        try{
            owner = database.loadPatient(owner.getUsername());
            problem = getUpdatedProblem(owner,problem);
            problem.addRecord(newRecord);
            database.savePatient(owner);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private Problem getUpdatedProblem(Patient owner,Problem oldVersion) {
        int problemIndex = 0;
        while (problemIndex < owner.getProblems().size()) {
            if (owner.getProblems().get(problemIndex) == oldVersion) {
                return owner.getProblems().get(problemIndex);
            }
            problemIndex++;
        }
        return oldVersion;
    }
}
