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
     * @param patient The patient.
     * @param problem the problem.
     * @param record The record.
     */
    public SearchResult(Patient patient, Problem problem,  Record record){
        this.patient = patient;
        this.record = record;
        this.problem = problem;
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
