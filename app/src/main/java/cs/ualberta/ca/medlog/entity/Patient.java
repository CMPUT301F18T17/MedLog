package cs.ualberta.ca.medlog.entity;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Patient extends User{

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
