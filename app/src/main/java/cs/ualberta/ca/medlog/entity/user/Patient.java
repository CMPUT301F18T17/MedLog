package cs.ualberta.ca.medlog.entity.user;

import java.util.ArrayList;

import cs.ualberta.ca.medlog.entity.Photo;
import cs.ualberta.ca.medlog.entity.Problem;

/**
 * <p>
 *     Description: <br>
 *         This class represents a Patient in the application, patients possess problems, body
 *         photos and contact information and they have getters and setters for each.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Thomas Roskewich
 * @version 1.0
 * @see User
 */
public class Patient extends User {

    private ArrayList<Problem> problems = new ArrayList<>();
    private ArrayList<Photo> bodyPhotos = new ArrayList<>();
    private ContactInfo contactInfo;

    /**
     * Constructs a new Patient with the given contact information and username.
     * @param info The patient contact information.
     * @param username The patient username.
     */
    public Patient(ContactInfo info, String username){
        super(username);
        contactInfo = info;
    }

    /**
     * Retrieves all of the patients problems.
     * @return ArrayList of the patient's problems.
     */
    public ArrayList<Problem> getProblems() {
        return problems;
    }

    /**
     * Adds a problem to the patient's problems.
     * @param newProblem The problem to add.
     */
    public void addProblem(Problem newProblem){
        newProblem.setId(problems.size());
        problems.add(newProblem);
    }

    /**
     * Deletes the patient problem with the given id/index.
     * @param problemIndex The id/index of the problem to remove.
     */
    public void deleteProblem(int problemIndex) {
        for(int i = problemIndex; i < problems.size(); i++){
            problems.get(i).setId(problems.get(i).getId() - 1);
        }
        problems.remove(problemIndex);
    }

    /**
     * Retrieves all of the patients body photos.
     * @return ArrayList of the patient's body photos.
     */
    public ArrayList<Photo> getBodyPhotos(){
        return bodyPhotos;
    }

    /**
     * Sets new body photos for the patient.
     * @param newPhotos The new body photos.
     */
    public void setBodyPhotos(ArrayList<Photo> newPhotos) {
        bodyPhotos = newPhotos;
    }

    /**
     * Retrieves the patient contact information.
     * @return The contact information.
     */
    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    /**
     * Retrieves a string representation of the patient based on their email.
     * @return The string representation.
     */
    public String toString(){
        return contactInfo.getEmail();
    }
}
