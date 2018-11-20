/**
 *
 * <h1>
 *     Patient
 * </h1>
 *
 *  <p>
 *     Description: <br>
 *         This class represents a Patient in the application.
 *
 * </p>
 *
 * @author Thomas Roskewich
 * @contact roskewic@ualberta.ca
 * @see cs.ualberta.ca.medlog.entity.user.User
 */

package cs.ualberta.ca.medlog.entity.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import cs.ualberta.ca.medlog.entity.Photo;
import cs.ualberta.ca.medlog.entity.Problem;

public class Patient extends User implements Serializable {

    private ArrayList<Problem> problems = new ArrayList<Problem>();
    private ArrayList<Photo> bodyPhotos = new ArrayList<Photo>();
    private ContactInfo contactInfo;

    public Patient(ContactInfo info, String username){
        super(username);
        contactInfo = info;
    }


    /**
     * <p>Get all the users problems.</p>
     * @return Arraylist of problems.
     */
    public ArrayList<Problem> getProblems() {
        return problems;
    }

    /**
     * <p>Delete a problem at the given id/index</p>
     * @param problemIndex The id/index of the problem to remove.
     */
    public void deleteProblem(int problemIndex) {
        for(int i = problemIndex; i < problems.size(); i++){
            problems.get(i).setId(problems.get(i).getId() - 1);
        }
        problems.remove(problemIndex);
    }

    /**
     * <p>Adds a problem to the current patients problems.</p>
     * @param newProblem The problem to add.
     */
    public void addProblem(Problem newProblem){
        newProblem.setId(problems.size());
        problems.add(newProblem);
    }

    /**
     * <p>Get all body photos of the current patient.</p>
     * @return An array of body photos.
     */
    public ArrayList<Photo> getBodyPhotos(){
        return bodyPhotos;
    }

    /**
     * <p>Add a body photo to the patient.</p>
     * @param newPhoto The photo to add.
     */
    public void addBodyPhoto(Photo newPhoto){
        bodyPhotos.add(newPhoto);
    }

    /**
     * <p>Get the users current contact information.</p>
     * @return The contact info.
     */
    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public String toString(){
        return contactInfo.getEmail();
    }
}
