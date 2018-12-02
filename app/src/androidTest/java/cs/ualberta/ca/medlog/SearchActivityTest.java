package cs.ualberta.ca.medlog;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.CoordinatesProvider;
import android.support.test.espresso.action.GeneralClickAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Tap;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.DisplayMetrics;
import android.view.View;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;

import cs.ualberta.ca.medlog.activity.PatientMenuActivity;
import cs.ualberta.ca.medlog.activity.ProviderMenuActivity;
import cs.ualberta.ca.medlog.controller.ElasticSearchController;
import cs.ualberta.ca.medlog.entity.BodyLocation;
import cs.ualberta.ca.medlog.entity.MapLocation;
import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.Record;
import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.entity.user.ContactInfo;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.mock.MockPhoto;
import cs.ualberta.ca.medlog.singleton.AppStatus;
import cs.ualberta.ca.medlog.entity.Photo;

import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.fail;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class SearchActivityTest {


    private static CareProvider provider;
    private static Patient patient;


    @Rule
    public ActivityTestRule<PatientMenuActivity> patientMenu = new ActivityTestRule<>(PatientMenuActivity.class);

    @Rule
    public ActivityTestRule<ProviderMenuActivity> providerMenu = new ActivityTestRule<>(ProviderMenuActivity.class);

    @BeforeClass
    public static void setup(){
        // Set the current provider to be a test provider.
        patient = new Patient(
                new ContactInfo("1234567890", "super@email")
                , "intent_2_user"
        );

        ArrayList<Photo> bodyPhotos = new ArrayList<>();
        bodyPhotos.add(new MockPhoto(720, 1080));
        patient.setBodyPhotos(bodyPhotos);

        // Setup problem and application information
        Problem problem = new Problem("Test Problem", new Date(), "The problem to test on.");
        Problem problem1 = new Problem("Record Problem", new Date(), "Record problem.");
        Problem problem3 = new Problem("Double Record Problem", new Date(), "A second record problem that should appear on search.");

        // Setup problem 1 with a Map Location Record
        Record r = new Record(patient.getUsername());
        r.setMapLocation(new MapLocation(53.52485748242336, -113.52633626770586));
        r.setTitleComment("Map Location", "Location");
        problem1.addRecord(r);

        // Setup problem 1 with a Body Location Record
        Record r2 = new Record(patient.getUsername());
        r2.setBodyLocation(new BodyLocation(new MockPhoto(300,300), 0.5f, 0.5f));
        problem1.addRecord(r2);

        // Add problems to patient
        patient.addProblem(problem);
        patient.addProblem(problem1);
        patient.addProblem(problem3);

        // Initialize care provider
        provider = new CareProvider("intent_2_care");
        provider.addPatient(patient);
        AppStatus.getInstance().setCurrentUser(patient);
        ElasticSearchController.savePatient(patient);
        ElasticSearchController.saveCareProvider(provider);

    }

    @After
    public void unsetup(){
        ElasticSearchController.deletePatient("intent_2_user");
        ElasticSearchController.deleteCareProvider("intent_2_care");
    }


    @Test
    public void testSearchPatientKeywords(){
        AppStatus.getInstance().setCurrentUser(patient);
        patientMenu.launchActivity(new Intent());
        Espresso.onView(withId(R.id.activityPatientMenu_SearchProblemsButton)).perform(click());


        // Try with one result
        Espresso.onView(withHint("Enter Keywords")).perform(typeText("test"));
        Espresso.onView(withId(R.id.activityPatientSearch_SearchButton)).perform(click());

        Espresso.onData(anything())
                .inAdapterView(withId(R.id.activityPatientSearch_ResultsListView))
                .atPosition(0)
                .check(matches(isDisplayed()))
                .perform(click());

        Espresso.onView(withId(R.id.activityPatientProblemView_Base)).check(matches(isDisplayed()));
        Espresso.pressBack();


        // Try with Two Results
        Espresso.onView(withId(R.id.activityPatientSearch_KeywordEditText)).perform(clearText());
        Espresso.onView(withHint("Enter Keywords")).perform(typeText("record"));
        Espresso.onView(withId(R.id.activityPatientSearch_SearchButton)).perform(click());

        Espresso.onData(anything())
                .inAdapterView(withId(R.id.activityPatientSearch_ResultsListView))
                .atPosition(1)
                .check(matches(isDisplayed()));

        Espresso.pressBack();

        // Try with many keywords
        Espresso.onView(withId(R.id.activityPatientSearch_KeywordEditText)).perform(clearText());
        Espresso.onView(withHint("Enter Keywords")).perform(typeText("double record problem"));
        Espresso.onView(withId(R.id.activityPatientSearch_SearchButton)).perform(click());

        Espresso.onData(anything())
                .inAdapterView(withId(R.id.activityPatientSearch_ResultsListView))
                .atPosition(0)
                .check(matches(isDisplayed()))
                .perform(click());
        Espresso.onView(withId(R.id.activityPatientProblemView_Base)).check(matches(isDisplayed()));
    }


    @Test
    public void testPatientBodySearch(){
        AppStatus.getInstance().setCurrentUser(patient);
        patientMenu.launchActivity(new Intent());
        Espresso.onView(withId(R.id.activityPatientMenu_SearchProblemsButton)).perform(click());

        // Search for a valid body location
        Espresso.onView(withId(R.id.activityPatientSearch_BodyLocationButton)).perform(click());
        DisplayMetrics dm = new DisplayMetrics();
        patientMenu.getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        Espresso.onView(withId(R.id.activityAddBodyLocation_Base)).perform(clickXY(dm.widthPixels / 2, dm.heightPixels / 2));
        Espresso.onView(withId(R.id.activityAddBodyLocation_SaveButton)).perform(click());
        Espresso.onView(withId(R.id.activityPatientSearch_SearchButton)).perform(click());

        Espresso.onData(anything())
                .inAdapterView(withId(R.id.activityPatientSearch_ResultsListView))
                .atPosition(0)
                .check(matches(isDisplayed()))
                .perform(click());

        Espresso.onView(withId(R.id.activityPatientProblemView_ProblemTitleView)).check(matches(withText("Record Problem")));
        Espresso.pressBack();

        // Search for an invalid valid body location
        Espresso.onView(withId(R.id.activityPatientSearch_BodyLocationButton)).perform(click());
        patientMenu.getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        Espresso.onView(withId(R.id.activityAddBodyLocation_Base)).perform(clickXY(200, 200));
        Espresso.onView(withId(R.id.activityAddBodyLocation_SaveButton)).perform(click());
        Espresso.onView(withId(R.id.activityPatientSearch_SearchButton)).perform(click());


        try {
            Espresso.onData(anything())
                    .inAdapterView(withId(R.id.activityPatientSearch_ResultsListView))
                    .atPosition(0)
                    .check(matches(not(isDisplayed())));
            fail("There shouldn't be any data in the result view list.");
        }catch(Exception ignored){}
    }


    @Test
    public void testPatientMapLocationSearch(){
        AppStatus.getInstance().setCurrentUser(patient);
        patientMenu.launchActivity(new Intent());
        Espresso.onView(withId(R.id.activityPatientMenu_SearchProblemsButton)).perform(click());

        // Search for a valid map location
        Espresso.onView(withId(R.id.activityPatientSearch_MapLocationButton)).perform(click());
        DisplayMetrics dm = new DisplayMetrics();
        patientMenu.getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        Espresso.onView(withId(R.id.activityAddMapLocation_MapViewBase)).perform(clickXY(dm.widthPixels / 2, dm.heightPixels / 2));
        Espresso.onView(withId(R.id.activityAddMapLocation_AddButton)).perform(click());
        Espresso.onView(withId(R.id.activityPatientSearch_SearchButton)).perform(click());
        Espresso.onData(anything())
                .inAdapterView(withId(R.id.activityPatientSearch_ResultsListView))
                .atPosition(0)
                .check(matches(isDisplayed()))
                .perform(click());
        Espresso.onView(withId(R.id.activityPatientProblemView_ProblemTitleView)).check(matches(withText("Record Problem")));
        Espresso.pressBack();

        // Try for an invalid map location
        Espresso.onView(withId(R.id.activityPatientSearch_MapLocationButton)).perform(click());
        Espresso.onView(withId(R.id.activityAddMapLocation_MapViewBase)).perform(clickXY(100, 100));
        Espresso.onView(withId(R.id.activityAddMapLocation_AddButton)).perform(click());
        Espresso.onView(withId(R.id.activityPatientSearch_SearchButton)).perform(click());

        try {
            Espresso.onData(anything())
                    .inAdapterView(withId(R.id.activityPatientSearch_ResultsListView))
                    .atPosition(0)
                    .check(matches(not(isDisplayed())));
            fail("There shouldn't be any data in the result view list.");
        }catch(Exception ignored){}

    }

    /**
     * Make Espresso click on a provided XY value for map testing and body location testing.
     * @param x The x value
     * @param y The y value
     * @return View Action
     */
    public static ViewAction clickXY(final int x, final int y){
        return new GeneralClickAction(
                Tap.SINGLE,
                new CoordinatesProvider() {
                    @Override
                    public float[] calculateCoordinates(View view) {

                        final int[] screenPos = new int[2];
                        view.getLocationOnScreen(screenPos);

                        final float screenX = screenPos[0] + x;
                        final float screenY = screenPos[1] + y;
                        float[] coordinates = {screenX, screenY};

                        return coordinates;
                    }
                },
                Press.FINGER);
    }

}
