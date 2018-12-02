package cs.ualberta.ca.medlog;

import org.junit.Test;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Date;

import cs.ualberta.ca.medlog.controller.PatientController;
import cs.ualberta.ca.medlog.entity.Photo;
import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.user.ContactInfo;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.exception.UserNotFoundException;
import cs.ualberta.ca.medlog.helper.Database;

import static junit.framework.TestCase.assertEquals;

/*
 * Test class for the Patient Controller
 *
 * Author: Kyle Androschuk
 * Contact: kandrosc@ualberta.ca
 * Created: November 28, 2018
 */

// Wihtout context, these tests will always fail :/
public class PatientControllerTest {

    @Test
    public void testsetEmail(){
        PatientController controller = new PatientController(null);
        ContactInfo info = new ContactInfo("0000000000", "email@email.ca");
        Patient patient= new Patient(info,"Test Patient");
        String newEmail="newemail@email.ca";
        controller.setEmail(patient,newEmail);
        Database database=controller.getDatabase();
        try {
            patient=database.loadPatient("Test Patient");
        }catch(UserNotFoundException | ConnectException e){}
        info=patient.getContactInfo();
        assertEquals(newEmail,info.getEmail());
    }

    @ Test
    public void testsetPhoneNumber(){
        PatientController controller = new PatientController(null);
        ContactInfo info = new ContactInfo("0000000000", "email@email.ca");
        Patient patient= new Patient(info,"Test Patient");
        String newPhoneNumber="1111111111";
        controller.setPhoneNumber(patient,newPhoneNumber);
        Database database=controller.getDatabase();
        try {
            patient=database.loadPatient("Test Patient");
        }catch(UserNotFoundException | ConnectException e){}
        info=patient.getContactInfo();
        assertEquals(newPhoneNumber,info.getPhoneNumber());
    }
    @Test
    public void testaddProblem(){
        PatientController controller = new PatientController(null);
        ContactInfo info = new ContactInfo("0000000000", "email@email.ca");
        Patient patient= new Patient(info,"Test Patient");
        Problem problem = new Problem("Test Problem", new Date(), "description");
        controller.addProblem(patient,problem);
        Database database=controller.getDatabase();
        try {
            patient=database.loadPatient("Test Patient");
        }catch(UserNotFoundException | ConnectException e){}

        assertEquals(problem,patient.getProblems().get(0));
    }

    @Test
    public void testdeleteProblem(){
        PatientController controller = new PatientController(null);
        ContactInfo info = new ContactInfo("0000000000", "email@email.ca");
        Patient patient= new Patient(info,"Test Patient");
        Problem problem1 = new Problem("Test Problem1", new Date(), "description");
        Problem problem2 = new Problem("Test Problem2", new Date(), "description");
        controller.addProblem(patient,problem1);
        controller.addProblem(patient,problem2);
        controller.deleteProblem(patient,problem1);
        Database database=controller.getDatabase();
        try {
            patient=database.loadPatient("Test Patient");
        }catch(UserNotFoundException | ConnectException e){}

        assertEquals(problem2,patient.getProblems().get(0));
    }

    @Test
    public void testsetBodyPhotos(){
        PatientController controller = new PatientController(null);
        ContactInfo info = new ContactInfo("0000000000", "email@email.ca");
        Patient patient= new Patient(info,"Test Patient");
        ArrayList<Photo> photos=new ArrayList<Photo>();
        for (int i = 0; i < 5; i++) {
            Photo photo = new Photo(null);
            photos.set(i,photo);
        }
        controller.setBodyPhotos(patient,photos);
        Database database=controller.getDatabase();
        try {
            patient=database.loadPatient("Test Patient");
        }catch(UserNotFoundException | ConnectException e){}

        assertEquals(photos,patient.getBodyPhotos());
    }


}
