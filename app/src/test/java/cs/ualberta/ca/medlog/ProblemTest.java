package cs.ualberta.ca.medlog;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.Record;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProblemTest {

    @Test
    public void testProblemTitle(){
        String title = "ProblemTitleString";
        Date date = new Date();
        String description = "ProblemDescriptionString";

        Problem testProblem = new Problem(title, date, description);
        assertEquals(testProblem.getTitle(), title);

        String title2 = "ProblemTitleString2";
        testProblem.setTitle(title2);
        assertEquals(testProblem.getTitle(), title2);
    }

    @Test
    public void testProblemDate(){
        String title = "ProblemTitleString";
        Date date = new Date();
        String description = "ProblemDescriptionString";

        Problem testProblem = new Problem(title, date, description);
        assertEquals(testProblem.getDate(), date);

        Date date2 = new Date(5);
        testProblem.setDate(date2);
        assertEquals(testProblem.getDate(), date2);
    }

    @Test
    public void testProblemDescription(){
        String title = "ProblemTitleString";
        Date date = new Date();
        String description = "ProblemDescriptionString";

        Problem testProblem = new Problem(title, date, description);
        assertEquals(testProblem.getDescription(), description);

        String description2 = "ProblemDescriptionString2";
        testProblem.setTitle(description2);
        assertEquals(testProblem.getTitle(), description2);
    }

    @Test
    public void testProblemRecords() {
        String title = "ProblemTitleString";
        Date date = new Date();
        String description = "ProblemDescriptionString";

        ArrayList<Record> recordList = new ArrayList<Record>();
        Problem testProblem = new Problem(title, date, description);
        assertTrue(testProblem.getRecords().isEmpty());

        for (int i = 0; i < 5; i++) {
            Record record = new Record("Record" + String.valueOf(i));
            testProblem.addRecord(record);
            recordList.add(record);

            assertEquals(testProblem.getRecords(), recordList);
            assertEquals(i + 1, testProblem.getRecords().size());
        }
    }
}