package cs.ualberta.ca.medlog;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.CoordinatesProvider;
import android.support.test.espresso.action.GeneralClickAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Tap;
import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;

import cs.ualberta.ca.medlog.activity.PatientProblemViewActivity;
import cs.ualberta.ca.medlog.controller.ElasticSearchController;
import cs.ualberta.ca.medlog.entity.Photo;
import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.user.ContactInfo;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.mock.MockPhoto;
import cs.ualberta.ca.medlog.singleton.AppStatus;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ProblemViewActivityTest {


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
        ArrayList<Photo> photos = new ArrayList<>();
        photos.add(new MockPhoto(720, 1080));
        user.setBodyPhotos(photos);
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

    @Test
    public void addRecord(){
        Espresso.onView(withId(R.id.activityPatientProblemView_AddRecordButton)).perform(click());

        // Add Title and Comment
        Espresso.onView(withId(R.id.activityPatientAddRecord_TitleCommentButton)).perform(click());
        Espresso.onView(withHint("Enter a Title")).perform(typeText("Test Record to Add"));
        Espresso.onView(withText("OK")).perform(click());
        Espresso.onView(withHint("Enter a Comment")).perform(typeText("A test comment on what this record does."));
        Espresso.onView(withText("OK")).perform(click());

        // Add Map Location
        Espresso.onView(withId(R.id.activityPatientAddRecord_MapLocationButton)).perform(click());
        Espresso.onView(withId(R.id.activityAddMapLocation_MapView)).perform(clickXY(150, 150));
        Espresso.onView(withId(R.id.activityAddMapLocation_AddButton)).perform(click());
        Espresso.onView(withId(R.id.activityPatientAddRecord_Base)).check(matches(isDisplayed()));

        // Add Body Location
        Espresso.onView(withId(R.id.activityPatientAddRecord_BodyLocationButton)).perform(click());
        Espresso.onView(withId(R.id.activityAddBodyLocation_Base)).perform(clickXY(501, 872));
        Espresso.onView(withId(R.id.activityAddBodyLocation_SaveButton)).perform(click());
        Espresso.onView(withId(R.id.activityPatientAddRecord_Base)).check(matches(isDisplayed()));


        // Add Photo -> Photo selector doesn't really work with testing so I have commented this out
        //Espresso.onView(withId(R.id.activityPatientAddRecord_PhotosButton)).perform(click());
        //Espresso.onView(withId(R.id.activityPhotoSelector_TakeNewButton)).perform(click());
        // Take photo and then submit it.
        //Espresso.onView(withId(R.id.activityPhotoSelector_SaveButton)).perform(click());
        //Espresso.onView(withId(R.id.activityPatientAddRecord_Base)).check(matches(isDisplayed()));

        // View Records
        Espresso.onView(withId(R.id.activityPatientAddRecord_SaveButton)).perform(click());
        Espresso.onView(withId(R.id.activityPatientProblemView_Base)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.activityPatientProblemView_ViewRecordsButton)).perform(click());

        Espresso.onData(anything())
                .inAdapterView(withId(R.id.activityPatientViewRecords_RecordsListView))
                .atPosition(0)
                .perform(click());

        // Check Values on Page
        Espresso.onView(withId(R.id.activityPatientRecordView_CreatorView)).check(matches(withText("intent_2_user")));
        Espresso.onView(withId(R.id.activityPatientRecordView_ProblemTitleView)).check(matches(withText("Test Problem")));
        Espresso.onView(withId(R.id.activityPatientRecordView_MapLocationButton)).perform(click());
        Espresso.onView(withId(R.id.activityViewMapLocation_Base)).check(matches(isDisplayed()));
        Espresso.pressBack();
        Espresso.onView(withId(R.id.activityPatientRecordView_TitleCommentButton)).perform(click());
        Espresso.onView(withText("Test Record to Add")).check(matches(isDisplayed()));
        Espresso.pressBack();
        Espresso.onView(withId(R.id.activityPatientRecordView_BodyLocationButton)).perform(click());
        Espresso.onView(withId(R.id.activityViewBodyLocation_Base)).check(matches(isDisplayed()));


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
