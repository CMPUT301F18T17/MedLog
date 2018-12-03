package cs.ualberta.ca.medlog;

import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.Record;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * <p>
 *     Description: <br>
 *         Test class for the Problem entity.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Calvin Chomyc, Tem Tamre
 * @version 1.0
 * @see Problem
 */
public class ProblemTest {

    /**
     * Tests if a Problem's id is successfully set and returned.
     */
    @Test
    public void testId() {
        String title = "ProblemTitleString";
        Date date = new Date();
        String description = "This is a problem description.";

        Problem testProblem = new Problem(title, date, description);
        assertEquals(-1, testProblem.getId());
        testProblem.setId(5);
        assertEquals(5, testProblem.getId());
    }

    /**
     * Tests if a Problem's title is successfully returned.
     */
    @Test
    public void testGetTitle() {
        String title = "ProblemTitleString";
        Date date = new Date();
        String description = "This is a problem description.";

        Problem testProblem = new Problem(title, date, description);
        assertEquals(title, testProblem.getTitle());
    }

    /**
     * Tests if a Problem's date is successfully returned.
     */
    @Test
    public void testGetDate() {
        String title = "ProblemTitleString";
        Date date = new Date();
        String description = "This is a problem description.";

        Problem testProblem = new Problem(title, date, description);
        assertEquals(date, testProblem.getDate());
    }

    /**
     * Tests if a Problem's description is successfully returned.
     */
    @Test
    public void testGetDescription() {
        String title = "ProblemTitleString";
        Date date = new Date();
        String description = "This is a problem description.";

        Problem testProblem = new Problem(title, date, description);
        assertEquals(description, testProblem.getDescription());
    }

    /**
     * Tests if a single record is successfully added to and returned from a Problem.
     */
    @Test
    public void testRecord() {
        String title = "ProblemTitleString";
        Date date = new Date();
        String description = "This is a problem description.";

        Problem testProblem = new Problem(title, date, description);
        assertEquals(0, testProblem.getRecords().size());


        ArrayList<Record> recordList = new ArrayList<>();
        Record record = new Record("Username");
        testProblem.addRecord(record);
        recordList.add(record);

        assertEquals(1, testProblem.getRecords().size());
        assertEquals(recordList, testProblem.getRecords());
    }

    /**
     * Tests if a Problem's title is successfully set and returned.
     */
    @Test
    public void testSetTitle() {
        String title = "ProblemTitleString";
        Date date = new Date();
        String description = "This is a problem description.";

        Problem testProblem = new Problem(title, date, description);
        assertEquals(title, testProblem.getTitle());

        String newTitle = "New Title";
        testProblem.setTitle(newTitle);
        assertEquals(newTitle, testProblem.getTitle());
    }

    /**
     * Tests if a Problem's date is successfully set and returned.
     */
    @Test
    public void testSetDate() {
        String title = "ProblemTitleString";
        Date date = new Date();
        String description = "This is a problem description.";

        Problem testProblem = new Problem(title, date, description);
        assertEquals(title, testProblem.getTitle());

        Date newDate = new Date();
        testProblem.setDate(newDate);
        assertEquals(newDate, testProblem.getDate());
    }

    /**
     * Tests if a Problem's description is successfully set and returned.
     */
    @Test
    public void testSetDescription() {
        String title = "ProblemTitleString";
        Date date = new Date();
        String description = "This is a problem description.";

        Problem testProblem = new Problem(title, date, description);
        assertEquals(title, testProblem.getTitle());

        String newDescription = "New Description";
        testProblem.setDescription(newDescription);
        assertEquals(newDescription, testProblem.getDescription());
    }

    /**
     * Tests if multiple records are successfully added and returned from a Problem.
     */
    @Test
    public void testMultipleRecords() {
        String title = "ProblemTitleString";
        Date date = new Date();
        String description = "This is a problem description.";

        Problem testProblem = new Problem(title, date, description);
        assertTrue(testProblem.getRecords().isEmpty());

        for (int i = 0; i < 5; i++) {
            Record record = new Record("Username");
            testProblem.addRecord(record);

            assertEquals(record, testProblem.getRecords().get(i));
            assertEquals(i + 1, testProblem.getRecords().size());
        }
    }

    /**
     * Tests if records are successfully removed from a Problem.
     */
    @Test
    public void testRemoveRecord() {
        String title = "ProblemTitleString";
        Date date = new Date();
        String description = "This is a problem description.";

        Problem testProblem = new Problem(title, date, description);
        assertEquals(0, testProblem.getRecords().size());

        ArrayList<Record> recordList = new ArrayList<>();
        Record record1 = new Record("Username1");
        testProblem.addRecord(record1);
        recordList.add(record1);
        Record record2 = new Record("Username2");
        testProblem.addRecord(record2);
        recordList.add(record2);
        Record record3 = new Record("Username3");
        testProblem.addRecord(record3);
        recordList.add(record3);


        assertEquals(3, testProblem.getRecords().size());
        testProblem.removeRecord(1);

        assertEquals(2, testProblem.getRecords().size());
        assertEquals(recordList.get(2), testProblem.getRecords().get(1)); // Index is different because we didn't remove the record from recordList
        testProblem.removeRecord(1);

        assertEquals(1, testProblem.getRecords().size());
        assertEquals(recordList.get(0), testProblem.getRecords().get(0));
        testProblem.removeRecord(0);

        assertEquals(0, testProblem.getRecords().size());
    }
}