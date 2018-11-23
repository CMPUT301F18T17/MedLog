package cs.ualberta.ca.medlog.entity;

import cs.ualberta.ca.medlog.entity.user.Patient;

/**
 * <h1>
 *     Search Result
 * </h1>
 *
 * <p>
 *     Description: <br>
 *         Class containing the information required from a search result.
 * </p>
 *
 * @author Thomas Roskewich
 * @contact roskewic@ualberta.ca
 * @version 1.0
 * @see cs.ualberta.ca.medlog.entity.user.Patient
 * @see Problem
 * @see Record
 */
public class SearchResult {
    private Patient patient;
    private Record record;
    private Problem problem;

    public SearchResult(Patient p, Record r, Problem pr){
        this.patient = p;
        this.record = r;
        this.problem = pr;
    }

    /**
     * <p>Get the search results patient.</p>
     * @return The search results patient.
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * <p>Get the record of the search result.</p>
     * @return The record of the search result
     */
    public Record getRecord() {
        return record;
    }

    /**
     * <p>Get the problem of the search result.</p>
     * @return The problem of the search result.
     */
    public Problem getProblem() {
        return problem;
    }
}
