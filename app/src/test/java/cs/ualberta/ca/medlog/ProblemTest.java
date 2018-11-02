/*
 * Test class for the Problem entity
 *
 * Authors: Calvin Chomyc, Tem Tamre
 * Contact: chomyc1@ualberta.ca, ttamre@ualberta.ca
 * Created: November 2, 2018
 */

package cs.ualberta.ca.medlog;

import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.Record;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProblemTest {

    @Test
    public void testGetTitle() {
        String title = "ProblemTitleString";
        Date date = new Date();
        String description = "ProblemDescriptionString";

        Problem testProblem = new Problem(title, date, description);
        assertEquals(title, testProblem.getTitle());
    }

    @Test
    public void testGetDate() {
        String title = "ProblemTitleString";
        Date date = new Date();
        String description = "ProblemDescriptionString";

        Problem testProblem = new Problem(title, date, description);
        assertEquals(date, testProblem.getDate());
    }

    @Test
    public void testGetDescription() {
        String title = "ProblemTitleString";
        Date date = new Date();
        String description = "ProblemDescriptionString";

        Problem testProblem = new Problem(title, date, description);
        assertEquals(description, testProblem.getDescription());
    }

    @Test
    public void testGetRecords() {
        String title = "ProblemTitleString";
        Date date = new Date();
        String description = "ProblemDescriptionString";

        Problem testProblem = new Problem(title, date, description);
        assertEquals(0, testProblem.getRecords().size());


        ArrayList<Record> recordList = new ArrayList<>();
        Record record = new Record("Username");
        testProblem.addRecord(record);
        recordList.add(record);

        assertEquals(1, testProblem.getRecords().size());
        assertEquals(recordList, testProblem.getRecords());
    }

    @Test
    public void testSetTitle() {
        String title = "ProblemTitleString";
        Date date = new Date();
        String description = "ProblemDescriptionString";

        Problem testProblem = new Problem(title, date, description);
        assertEquals(title, testProblem.getTitle());

        String newTitle = "New Title";
        testProblem.setTitle(newTitle);
        assertEquals(newTitle, testProblem.getTitle());
    }

    @Test
    public void testSetDate() {
        String title = "ProblemTitleString";
        Date date = new Date();
        String description = "ProblemDescriptionString";

        Problem testProblem = new Problem(title, date, description);
        assertEquals(title, testProblem.getTitle());

        Date newDate = new Date();
        testProblem.setDate(newDate);
        assertEquals(newDate, testProblem.getDate());
    }

    @Test
    public void testSetDescription() {
        String title = "ProblemTitleString";
        Date date = new Date();
        String description = "ProblemDescriptionString";

        Problem testProblem = new Problem(title, date, description);
        assertEquals(title, testProblem.getTitle());

        String newDescription = "New Description";
        testProblem.setDescription(newDescription);
        assertEquals(newDescription, testProblem.getDescription());
    }

    @Test
    public void testAddRecords() {
        String title = "ProblemTitleString";
        Date date = new Date();
        String description = "ProblemDescriptionString";

        Problem testProblem = new Problem(title, date, description);
        assertTrue(testProblem.getRecords().isEmpty());

        for (int i = 0; i < 5; i++) {
            Record record = new Record("Username");
            testProblem.addRecord(record);

            assertEquals(record, testProblem.getRecords().get(i));
            assertEquals(i + 1, testProblem.getRecords().size());
        }
    }
}