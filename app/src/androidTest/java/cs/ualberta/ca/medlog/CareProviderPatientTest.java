package cs.ualberta.ca.medlog;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import cs.ualberta.ca.medlog.activity.PatientMenuActivity;
import cs.ualberta.ca.medlog.activity.ProviderMenuActivity;
import cs.ualberta.ca.medlog.controller.ElasticSearchController;
import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.entity.user.ContactInfo;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.exception.EncryptionException;
import cs.ualberta.ca.medlog.helper.Encryption;
import cs.ualberta.ca.medlog.singleton.AppStatus;

import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static junit.framework.TestCase.fail;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CareProviderPatientTest {

    private static CareProvider user;
    private static Patient patient;


    @Rule
    public ActivityTestRule<PatientMenuActivity> patientMenuRule = new ActivityTestRule<>(PatientMenuActivity.class);

    @Rule
    public ActivityTestRule<ProviderMenuActivity> patientRule = new ActivityTestRule<>(ProviderMenuActivity.class);

    @BeforeClass
    public static void setup(){
        // Set the current user to be a test user.
        patient = new Patient(
                new ContactInfo("1234567890", "super@email")
                , "intent_2_user"
        );

        // Setup problem and application information
        Problem problem = new Problem("Test Problem", new Date(), "The problem to test on.");
        patient.addProblem(problem);

        user = new CareProvider("intent_2_care");
        AppStatus.getInstance().setCurrentUser(user);
        ElasticSearchController.savePatient(patient);

    }

    @After
    public void unsetup(){
        ElasticSearchController.deletePatient("intent_2_user");
        ElasticSearchController.deleteCareProvider("intent_2_care");
    }


    @Test
    public void addPatient(){
        patientRule.launchActivity(new Intent());
        // Test adding a patient.
        Espresso.onView(withId(R.id.activityProviderMenu_AddPatientButton)).perform(click());

        String loginCode = null;
        try {
            loginCode = Encryption.encryptData("CODE", patient.getUsername().getBytes());
            loginCode = loginCode.substring(0, loginCode.length() - 3);
        } catch (EncryptionException e) {
            e.printStackTrace();
            fail("Encryption exception on username.");
        }
        Espresso.onView(withHint("Enter Patient Register Code")).perform(typeText(loginCode));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.activityProviderAddPatient_AddButton)).perform(click());
        Espresso.onView(withText("VIEW PATIENT")).perform(click());

        // Validate information
        Espresso.onView(withId(R.id.activityProviderPatientProfile_UsernameView)).check(matches(withText("intent_2_user")));
        Espresso.onView(withId(R.id.activityProviderPatientProfile_EmailView)).check(matches(withText(patient.getContactInfo().getEmail())));
        Espresso.onView(withId(R.id.activityProviderPatientProfile_PhoneNumberView)).check(matches(withText(patient.getContactInfo().getPhoneNumber())));
    }

    @Test
    public void addCommentRecord(){
        patientRule.launchActivity(new Intent());
        // Test adding a patient.
        Espresso.onView(withId(R.id.activityProviderMenu_AddPatientButton)).perform(click());

        String loginCode = null;
        try {
            loginCode = Encryption.encryptData("CODE", patient.getUsername().getBytes());
            loginCode = loginCode.substring(0, loginCode.length() - 3);
        } catch (EncryptionException e) {
            e.printStackTrace();
            fail("Encryption exception on username.");
        }
        Espresso.onView(withHint("Enter Patient Register Code")).perform(typeText(loginCode));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.activityProviderAddPatient_AddButton)).perform(click());
        Espresso.onView(withText("VIEW PATIENT")).perform(click());
        Espresso.onView(withText("VIEW PROBLEMS")).perform(click());

        Espresso.onData(anything())
                .inAdapterView(withId(R.id.activityProviderPatientViewProblems_ProblemsListView))
                .atPosition(0)
                .check(matches(isDisplayed()))
                .perform(click());

        // Check information on the problem
        Espresso.onView(withId(R.id.activityProviderProblemView_ProblemTitleView)).check(matches(withText("Test Problem")));
        Espresso.onView(withId(R.id.activityProviderProblemView_ProblemDescriptionView)).check(matches(withText("The problem to test on.")));

        // Add Comment Record
        Espresso.onView(withId(R.id.activityProviderProblemView_AddRecordButton)).perform(click());
        Espresso.onView(withHint("Enter a Comment")).perform(typeText("Wow this is a cool problem!"));
        Espresso.onView(withText("OK")).perform(click());
        Espresso.onView(withId(R.id.activityProviderProblemView_ViewRecordsButton)).perform(click());

        Espresso.onData(anything())
                .inAdapterView(withId(R.id.activityProviderPatientViewRecords_RecordsListView))
                .atPosition(0)
                .check(matches(isDisplayed()))
                .perform(click());

        // Check information to make sure its saved
        Espresso.onView(withId(R.id.activityProviderRecordView_CreatorView)).check(matches(withText(user.getUsername())));
        Espresso.onView(withId(R.id.activityProviderRecordView_TitleCommentButton)).perform(click());
        Espresso.onView(withText("Care Provider Comment")).check(matches(isDisplayed()));
        Espresso.onView(withText("Wow this is a cool problem!")).check(matches(isDisplayed()));

        // Swap to patient view and see if the comment saved
        patient = ElasticSearchController.loadPatient(patient.getUsername());
        AppStatus.getInstance().setCurrentUser(patient);
        patientMenuRule.launchActivity(new Intent());
        Espresso.onView(withId(R.id.activityPatientMenu_ViewProblemsButton)).perform(click());

        // Navigate to the record
        Espresso.onData(anything())
                .inAdapterView(withId(R.id.activityPatientViewProblems_ProblemsListView))
                .atPosition(0)
                .perform(click());

        Espresso.onView(withId(R.id.activityPatientProblemView_ViewRecordsButton)).perform(click());

        Espresso.onData(anything())
                .inAdapterView(withId(R.id.activityPatientViewRecords_RecordsListView))
                .atPosition(0)
                .perform(click());

        // Verify the information is there.
        Espresso.onView(withId(R.id.activityPatientRecordView_TitleCommentButton)).perform(click());
        Espresso.onView(withText("Care Provider Comment")).check(matches(isDisplayed()));
        Espresso.onView(withText("Wow this is a cool problem!")).check(matches(isDisplayed()));
    }
}
