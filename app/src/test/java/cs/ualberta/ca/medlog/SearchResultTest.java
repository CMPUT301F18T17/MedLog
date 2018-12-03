package cs.ualberta.ca.medlog;

import org.junit.Test;

import java.util.Date;

import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.Record;
import cs.ualberta.ca.medlog.entity.SearchResult;
import cs.ualberta.ca.medlog.entity.user.ContactInfo;
import cs.ualberta.ca.medlog.entity.user.Patient;

import static org.junit.Assert.*;


/**
 * <p>
 *     Description: <br>
 *         Test class for the SearchResult entity.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Calvin Chomyc
 * @version 1.0
 * @see SearchResult
 */
public class SearchResultTest {

    @Test
    public void testCreateInstance(){
        SearchResult testResult = new SearchResult(null, null, null);
        assertNotNull(testResult);
    }

    @Test
    public void testGetPatient(){
        Patient testPatient = new Patient(new ContactInfo("1234567894", "me@md.ca"), "super");
        SearchResult testResult = new SearchResult(testPatient, null, null);
        assertEquals(testPatient, testResult.getPatient());
    }

    @Test
    public void testGetProblem(){
        String title = "ProblemTitleString";
        Date date = new Date();
        String description = "This is a problem description.";
        Problem testProblem = new Problem(title, date, description);

        SearchResult testResult = new SearchResult(null, testProblem, null);
        assertEquals(testProblem, testResult.getProblem());
    }

    @Test
    public void testGetRecord(){
        Record testRecord = new Record("John Does");
        SearchResult testResult = new SearchResult(null, null, testRecord);
        assertEquals(testRecord, testResult.getRecord());
    }
}
