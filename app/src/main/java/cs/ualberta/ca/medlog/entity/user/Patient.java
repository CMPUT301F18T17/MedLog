package cs.ualberta.ca.medlog.entity.user;

import java.io.Serializable;
import java.util.ArrayList;

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

    public void addProblem(Problem newProblem){
        problems.add(newProblem);
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
}
