package cs.ualberta.ca.medlog;

import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.ConnectException;

import cs.ualberta.ca.medlog.activity.PatientLoginActivity;
import cs.ualberta.ca.medlog.activity.ProviderLoginActivity;
import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.entity.user.ContactInfo;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.exception.EncryptionException;
import cs.ualberta.ca.medlog.helper.Database;
import cs.ualberta.ca.medlog.helper.Encryption;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * <p>
 *     Description: <br>
 *         Test class for login activities using the Espresso framework
 * </p>
 *
 * <p>
 *     References: <br>
 *         Android Developer Guide, Espresso Setup Instructions
 *         https://developer.android.com/training/testing/espresso/setup#java
 *         Last updated 2018-11-08, accessed 2018-11-28
 *
 *         Ajesh R, Android Testing Part 1: Espresso Basics
 *         https://medium.com/mindorks/android-testing-part-1-espresso-basics-7219b86c862b
 *         https://medium.com/@AjeshRPai
 *         Published 2018-01-31, accessed 2018-11-28
 *
 *         userM1433372, patrickf, Response to StackOverflow question "Can't get ApplicationContext in Espresso tests"
 *         https://stackoverflow.com/a/46582539
 *         https://stackoverflow.com/users/1433372/userm1433372
 *         https://stackoverflow.com/users/774398/patrickf
 *         Answered 2017-10-05, edited 2018-02-06, accessed 2018-12-01
 * </p>
 *
 * @author Tem Tamre
 * @version 1.0
 * @see PatientLoginActivity
 * @see ProviderLoginActivity
 * @see Patient
 * @see CareProvider
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {
    private Patient usedPatient;
    private Patient availablePatient;

    private CareProvider usedProvider;
    private CareProvider availableProvider;


    @Rule
    public ActivityTestRule<PatientLoginActivity> patientRule = new ActivityTestRule<>(PatientLoginActivity.class);

    @Rule
    public ActivityTestRule<ProviderLoginActivity> providerRule = new ActivityTestRule<>(ProviderLoginActivity.class);


    /* Preparation for tests */

    @Before
    private void createPatients() {
        String usedUsername = "usedPatientLAT";
        ContactInfo usedInfo = new ContactInfo("780-485-1958", "usedPatient@gmail.com");
        usedPatient = new Patient(usedInfo, usedUsername);

        String availableUsername = "availablePatientLAT";
        ContactInfo availableInfo = new ContactInfo("780-434-3958", "availablePatient@gmail.com");
        availablePatient = new Patient(availableInfo, availableUsername);
    }

    @Before
    private void createProviders() {
        String usedUsername = "usedProviderLAT";
        usedProvider = new CareProvider(usedUsername);

        String availableUsername = "availableProviderLAT";
        availableProvider = new CareProvider(availableUsername);
    }

    @Test
    public void testPrepareUsers() throws ConnectException {
        Database db = new Database(InstrumentationRegistry.getInstrumentation().getTargetContext());

        // Create users
        createPatients();
        createProviders();

        // Push users to the database
        db.savePatient(usedPatient);
        db.saveProvider(usedProvider);

        // Assert that these users exist in the database
        assertFalse(db.patientUsernameAvailable(usedPatient.getUsername()));
        assertFalse(db.providerUsernameAvailable(usedProvider.getUsername()));

        // Assert that these usernames are available to use
        assertTrue(db.patientUsernameAvailable(availablePatient.getUsername()));
        assertTrue(db.providerUsernameAvailable(availableProvider.getUsername()));
    }


    /* Instrumented Tests */

    @Test
    public void testPatientUsernameLogin() {
        assertEquals(patientRule.getActivity().getClass(), PatientLoginActivity.class);
        Log.e("AndroidTest", "Begin LoginActivityTest.patientUsernameLogin()");

        // Active username
        Espresso.onView((withId(R.id.activityPatientLogin_UsernameEditText)))
                .perform(ViewActions.typeText(usedPatient.getUsername()));
        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.activityPatientLogin_UsernameEditText)).check(matches(withText(usedPatient.getUsername())));
        Espresso.onView(withId(R.id.activityPatientLogin_LoginButton)).perform(ViewActions.click());
    }

    @Test
    public void testPatientCodeLogin() throws EncryptionException {
        assertEquals(patientRule.getActivity().getClass(), PatientLoginActivity.class);
        Log.e("AndroidTest", "Begin LoginActivityTest.patientUsernameLogin()");

        // Get code
        String username = usedPatient.getUsername();
        byte[] usernameBytes = username.getBytes();
        String encryptionKey = Resources.getSystem().getString(R.string.EncryptionKey);
        String code = Encryption.encryptData(encryptionKey, usernameBytes);

        // Active username
        Espresso.onView((withId(R.id.activityPatientEnterRegisterCode_CodeEditText)))
                .perform(ViewActions.typeText(code));

        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.activityPatientEnterRegisterCode_CodeEditText)).check(matches(withText(usedPatient.getUsername())));
        Espresso.onView(withId(R.id.activityPatientEnterRegisterCode_RegisterButton)).perform(ViewActions.click());
    }

    @Test
    public void testPatientSignup() {
        Log.e("AndroidTest", "Begin LoginActivityTest.patientRegister()");

        Espresso.onView((withId(R.id.activityPatientSignUp_UsernameEditText)))
                .perform(ViewActions.typeText(availablePatient.getUsername()));
        Espresso.closeSoftKeyboard();

        Espresso.onView((withId(R.id.activityPatientSignUp_EmailEditText)))
                .perform(ViewActions.typeText(availablePatient.getContactInfo().getEmail()));
        Espresso.closeSoftKeyboard();

        Espresso.onView((withId(R.id.activityPatientSignUp_PhoneEditText)))
                .perform(ViewActions.typeText(availablePatient.getContactInfo().getPhoneNumber()));
        Espresso.closeSoftKeyboard();

        // Check each EditText view to make sure the text matches the patient's values
        Espresso.onView(withId(R.id.activityPatientSignUp_UsernameEditText)).check(matches(withText(availablePatient.getUsername())));
        Espresso.onView(withId(R.id.activityPatientSignUp_EmailEditText)).check(matches(withText(availablePatient.getContactInfo().getEmail())));
        Espresso.onView(withId(R.id.activityPatientSignUp_PhoneEditText)).check(matches(withText(availablePatient.getContactInfo().getPhoneNumber())));

        Espresso.onView(withId(R.id.activityPatientSignUp_CompleteButton)).perform(ViewActions.click());
    }

    @Test
    public void testProviderUsernameLogin() {
        assertEquals(providerRule.getActivity().getClass(), ProviderLoginActivity.class);
        Log.e("AndroidTest", "Begin LoginActivityTest.patientUsernameLogin()");

        // Active username
        Espresso.onView((withId(R.id.activityProviderLogin_UsernameEditText)))
                .perform(ViewActions.typeText(usedProvider.getUsername()));
        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.activityProviderLogin_UsernameEditText)).check(matches(withText(usedProvider.getUsername())));
        Espresso.onView(withId(R.id.activityProviderLogin_LoginButton)).perform(ViewActions.click());

    }

    @Test
    public void testProviderSignup() {
        Log.e("AndroidTest", "Begin LoginActivityTest.patientRegister()");

        Espresso.onView((withId(R.id.activityProviderRegistration_UsernameEditText)))
                .perform(ViewActions.typeText(availableProvider.getUsername()));
        Espresso.closeSoftKeyboard();

        // Check each EditText view to make sure the text matches the patient's values
        Espresso.onView(withId(R.id.activityProviderRegistration_UsernameEditText)).check(matches(withText(availableProvider.getUsername())));

        Espresso.onView(withId(R.id.activityProviderRegistration_CompleteButton)).perform(ViewActions.click());
    }
}
