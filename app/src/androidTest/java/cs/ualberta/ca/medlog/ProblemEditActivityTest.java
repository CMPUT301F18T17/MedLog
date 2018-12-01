package cs.ualberta.ca.medlog;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import cs.ualberta.ca.medlog.activity.PatientMenuActivity;
import cs.ualberta.ca.medlog.activity.PatientProblemViewActivity;
import cs.ualberta.ca.medlog.controller.ElasticSearchController;
import cs.ualberta.ca.medlog.controller.PatientController;
import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.user.ContactInfo;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.singleton.AppStatus;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ProblemEditActivityTest {


    @Rule
    public ActivityTestRule<PatientProblemViewActivity> patientRule = new ActivityTestRule<>(PatientProblemViewActivity.class);

    @BeforeClass
    public static void setup(){
        // Set the current user to be a test user.
        Patient user = new Patient(
                new ContactInfo("1234567890", "super@email")
                , "intent_2_user"
        );

        // Setup problem and application information
        Problem problem = new Problem("Test Problem", new Date(), "The problem to test on.");
        AppStatus.getInstance().setViewedProblem(problem);
        user.addProblem(problem);
        AppStatus.getInstance().setCurrentUser(user);
        AppStatus.getInstance().setViewedPatient(user);
        System.out.println(AppStatus.getInstance().getViewedProblem().getTitle());

    }

    @After
    public void unsetup(){
        ElasticSearchController.deletePatient("intent_2_user");
    }

    @Test
    public void editTitle(){
        // Edit the title
        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());

        // Go through popup and modify title.
        Espresso.onView(withText("Edit Title")).perform(click());
        Espresso.onView(withText("OK")).check(matches(isDisplayed()));
        Espresso.onView(withText("Test Problem")).perform(ViewActions.typeText("Edit"));
        Espresso.onView(withText("OK")).perform(click());

        // Check the edits were added.
        Espresso.onView(withId(R.id.activityPatientProblemView_ProblemTitleView)).check(matches(withText("Test PEditroblem")));
    }

    @Test
    public void editDescription(){
        // Edit the description
        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());

        // Go through popup and modify title.
        Espresso.onView(withText("Edit Description")).perform(click());
        Espresso.onView(withText("OK")).check(matches(isDisplayed()));
        Espresso.onView(withText("The problem to test on.")).perform(ViewActions.typeText("Edit"));
        Espresso.onView(withText("OK")).perform(click());

        // Check the edits were added.
        Espresso.onView(withId(R.id.activityPatientProblemView_ProblemDescriptionView)).check(matches(withText("The problem to tesEditt on.")));
    }
}
