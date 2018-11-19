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


    public ArrayList<Problem> getProblems() {
        return problems;
    }

    public void deleteProblem(int problemIndex) {
        for(int i = problemIndex; i < problems.size(); i++){
            problems.get(i).setId(problems.get(i).getId() - 1);
        }
        problems.remove(problemIndex);
    }
    public void addProblem(Problem newProblem){
        newProblem.setId(problems.size());
        problems.add(newProblem);
    }

    public void setTitle(int problemIndex, String newTitle) {
        Problem problem = problems.get(problemIndex);
        problem.setTitle(newTitle);
        problems.set(problemIndex,problem);
    }
    public void setDescription(int problemIndex, String newDescription) {
        Problem problem = problems.get(problemIndex);
        problem.setTitle(newDescription);
        problems.set(problemIndex,problem);
    }
    public void setDate(int problemIndex, Date newDate) {
        Problem problem = problems.get(problemIndex);
        problem.setDate(newDate);
        problems.set(problemIndex,problem);
    }

    public ArrayList<Photo> getBodyPhotos(){
        return bodyPhotos;
    }

    public void addBodyPhoto(Photo newPhoto){
        bodyPhotos.add(newPhoto);
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public String toString(){
        return contactInfo.getEmail();
    }
}
