package cs.ualberta.ca.medlog;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cs.ualberta.ca.medlog.activity.PatientMenuActivity;
import cs.ualberta.ca.medlog.controller.ElasticSearchController;
import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.entity.user.ContactInfo;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.singleton.AppStatus;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

/**
 * <p>
 *     Description: <br>
 *         Instrumented test for adding and viewing problems
 * </p>
 *
 *
 * @author Thomas Roskewich
 * @version 1.0
 * @see cs.ualberta.ca.medlog.activity.PatientMenuActivity
 * @see Patient
 *
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ProblemActivityTest {

    private Patient user;


    @Rule
    public ActivityTestRule<PatientMenuActivity> patientRule = new ActivityTestRule<>(PatientMenuActivity.class);

    @Before
    public void setup(){

        // Set the current user to be a test user.
        user = new Patient(
                new ContactInfo("1234567890", "super@email")
                , "intent_2_user"
        );
        AppStatus.getInstance().setCurrentUser(user);
    }

    @After
    public void desetup(){
        ElasticSearchController.deletePatient(user.getUsername());
    }


    @Test
    public void viewProblem(){

        Espresso.onView((withId(R.id.activityPatientMenu_ViewProblemsButton))).perform(click());
        try {
            Espresso.onData(anything())
                    .inAdapterView(withId(R.id.activityPatientViewProblems_ProblemsListView))
                    .atPosition(0)
                    .check(doesNotExist());
            TestCase.fail("There should not be any problems recorded yet.");
        }catch(Exception ignore){ }
    }

    @Test
    public void addProblem(){
        // Add problem button
        Espresso.onView(withId(R.id.activityPatientMenu_AddProblemButton)).perform(click());

        // Add title and description
        Espresso.onView(withId(R.id.activityPatientAddProblem_TitleEditText))
                .perform(ViewActions.typeText("Super Test Problem"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.activityPatientAddProblem_DescriptionEditText))
                .perform(ViewActions.typeText("Super test description of a longer length that should probably be separated."));
        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.activityPatientAddProblem_AddButton)).perform(click());

        // Click on save
        Espresso.onView((withId(R.id.fragmentAddProblemNavigation_ViewButton))).perform(click());

        // Check Values
        Espresso.onView(withId(R.id.activityPatientProblemView_Base)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.activityPatientProblemView_ProblemTitleView)).check(matches(withText("Super Test Problem")));
        Espresso.onView(withId(R.id.activityPatientProblemView_ProblemDescriptionView))
                .check(matches(withText("Super test description of a longer length that should probably be separated.")));

        // Return to the main menu.
        Espresso.pressBack();
        Espresso.pressBack();
        Espresso.pressBack();
        Espresso.onView(withId(R.id.activityPatientMenu_Base)).check(matches(isDisplayed()));

        // View Problems
        Espresso.onView(withId(R.id.activityPatientMenu_ViewProblemsButton)).perform(click());
        Espresso.onData(anything())
                .inAdapterView(withId(R.id.activityPatientViewProblems_ProblemsListView))
                .atPosition(0)
                .check(matches(isDisplayed()));

        Espresso.onData(anything())
                .inAdapterView(withId(R.id.activityPatientViewProblems_ProblemsListView))
                .atPosition(0)
                .perform(click());

        // Check matches again
        Espresso.onView(withId(R.id.activityPatientProblemView_Base)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.activityPatientProblemView_ProblemTitleView)).check(matches(withText("Super Test Problem")));
        Espresso.onView(withId(R.id.activityPatientProblemView_ProblemDescriptionView))
                .check(matches(withText("Super test description of a longer length that should probably be separated.")));
    }

    @Test
    public void deleteProblem(){
        // Add problem button
        Espresso.onView(withId(R.id.activityPatientMenu_AddProblemButton)).perform(click());

        // Add title and description
        Espresso.onView(withId(R.id.activityPatientAddProblem_TitleEditText))
                .perform(ViewActions.typeText("Super Test Problem"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.activityPatientAddProblem_DescriptionEditText))
                .perform(ViewActions.typeText("Super test description of a longer length that should probably be separated."));
        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.activityPatientAddProblem_AddButton)).perform(click());

        // Click on save
        Espresso.onView((withId(R.id.fragmentAddProblemNavigation_ViewButton))).perform(click());

        // Check Values
        Espresso.onView(withId(R.id.activityPatientProblemView_Base)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.activityPatientProblemView_ProblemTitleView)).check(matches(withText("Super Test Problem")));
        Espresso.onView(withId(R.id.activityPatientProblemView_ProblemDescriptionView))
                .check(matches(withText("Super test description of a longer length that should probably be separated.")));

        // Return to the main menu.
        Espresso.pressBack();
        Espresso.pressBack();
        Espresso.pressBack();
        Espresso.onView(withId(R.id.activityPatientMenu_Base)).check(matches(isDisplayed()));

        // View Problems
        Espresso.onView(withId(R.id.activityPatientMenu_ViewProblemsButton)).perform(click());
        Espresso.onData(anything())
                .inAdapterView(withId(R.id.activityPatientViewProblems_ProblemsListView))
                .atPosition(0)
                .check(matches(isDisplayed()));

        Espresso.onData(anything())
                .inAdapterView(withId(R.id.activityPatientViewProblems_ProblemsListView))
                .atPosition(0)
                .perform(click());

        // Check matches again
        Espresso.onView(withId(R.id.activityPatientProblemView_Base)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.activityPatientProblemView_ProblemTitleView)).check(matches(withText("Super Test Problem")));
        Espresso.onView(withId(R.id.activityPatientProblemView_ProblemDescriptionView))
                .check(matches(withText("Super test description of a longer length that should probably be separated.")));

        // Delete the problem
        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        Espresso.onView(withText("Delete Problem")).perform(click());

        // Return to title menu
        Espresso.pressBack();
        Espresso.onView(withId(R.id.activityPatientMenu_Base)).check(matches(isDisplayed()));

        // Check if the view is empty.
        Espresso.onView(withId(R.id.activityPatientMenu_ViewProblemsButton)).perform(click());

        try {
            Espresso.onData(anything())
                    .inAdapterView(withId(R.id.activityPatientViewProblems_ProblemsListView))
                    .atPosition(0)
                    .check(doesNotExist());
            TestCase.fail("There should not be any problems recorded yet.");
        }catch(Exception ignore){ }
    }

}
