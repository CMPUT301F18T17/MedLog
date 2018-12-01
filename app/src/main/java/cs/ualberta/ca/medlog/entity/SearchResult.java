package cs.ualberta.ca.medlog.entity;

import cs.ualberta.ca.medlog.entity.user.Patient;

/**
 * <p>
 *     Description: <br>
 *         This class represents the information required for producing search results for patients
 *         and care providers.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 *
 * @author Thomas Roskewich
 * @version 1.0
 * @see Patient
 * @see Problem
 * @see Record
 */
public class SearchResult {
    private Patient patient;
    private Record record;
    private Problem problem;

    /**
     * Constructs a new SearchResult with the given patient, problem and record objects.
     * @param p The patient.
     * @param pr the problem.
     * @param r The record.
     */
    public SearchResult(Patient p, Problem pr,  Record r){
        this.patient = p;
        this.record = r;
        this.problem = pr;
    }

    /**
     * Retrieves the search result's patient.
     * @return The patient.
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Retrieves the search result's problem.
     * @return The problem.
     */
    public Problem getProblem() {
        return problem;
    }

    /**
     * Retrieves the search result's record.
     * @return The record.
     */
    public Record getRecord() {
        return record;
    }
}
