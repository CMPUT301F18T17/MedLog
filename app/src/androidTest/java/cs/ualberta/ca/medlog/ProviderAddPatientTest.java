package cs.ualberta.ca.medlog;

import android.content.Context;
import android.content.res.Resources;
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
import cs.ualberta.ca.medlog.activity.ProviderAddPatientActivity;
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
 * <h1>
 *     Provider Add Patient Instrumented Test
 * </h1>
 *
 * <p>
 *     Description: <br>
 *         Instrumented test for adding patients to a care provider's profile using the Espresso
 *         framework
 * </p>
 *
 * <p>
 *     Resources: <br>
 *         Robertas Uldukis, Response to StackOverflow question "How to read value from string.xml in android?"
 *         https://stackoverflow.com/a/20075784
 *         https://stackoverflow.com/users/2343261/robertas-uldukis
 *         Answered 2013-11-09, accessed 2018-11-30
 *
 * </p>
 *
 * @author Tem Tamre
 * @version 1.0
 * @see cs.ualberta.ca.medlog.activity.ProviderAddPatientActivity
 * @see CareProvider
 * @see Patient
 *
 * TODO replace "CODE" with a value in R.string
 */

public class ProviderAddPatientTest {
    private Database database;
    private CareProvider provider;
    private Patient patientA;
    private Patient patientB;
    private Patient patientC;

    @Rule
    public ActivityTestRule<ProviderAddPatientActivity> providerAddPatient = new ActivityTestRule<>(ProviderAddPatientActivity.class);


    /* Preparation for testing (this functionality should be tested in another test file) */
    private void prepareUsers() {
        ContactInfo infoA = new ContactInfo("780-473-7373", "swoods@gmail.ca");
        ContactInfo infoB = new ContactInfo("7804343058", "garcia@hotmail.ca");
        ContactInfo infoC = new ContactInfo("587-352-0413", "ahrgustav@gmail.com");

        provider = new CareProvider("PAPTLeadProvider");
        patientA = new Patient(infoA, "PAPTWoodsSamuel");
        patientB = new Patient(infoB, "PAPTGGarcia");
        patientC = new Patient(infoC, "PAPTGustavAhr");

        database.saveProvider(provider);
        database.savePatient(patientA);
        database.savePatient(patientB);
        database.savePatient(patientC);
    }

    /**
     * Test adding a patient with standard formatting
     * @throws EncryptionException
     */
    @Test
    public void testProviderAddPatientA() throws EncryptionException {
        assertEquals(providerAddPatient.getActivity().getClass(), ProviderAddPatientActivity.class);

        String usernameA = patientA.getUsername();
        String codeA = Encryption.encryptData("CODE", usernameA.getBytes());


        Espresso.onView((withId(R.id.activityProviderAddPatient_CodeEditText))).perform(ViewActions.typeText(codeA));
        Espresso.onView(withId(R.id.activityProviderAddPatient_CodeEditText)).check(matches(withText(codeA)));
        Espresso.onView(withId(R.id.activityProviderAddPatient_AddButton)).perform(ViewActions.click());
    }

    /**
     * Test adding a patient with standard formatting
     * @throws EncryptionException
     */
    @Test
    public void testProviderAddPatientB() throws EncryptionException {
        assertEquals(providerAddPatient.getActivity().getClass(), ProviderAddPatientActivity.class);

        String usernameB = patientB.getUsername();
        String encryptionKey = Resources.getSystem().getString(R.string.EncryptionKey);
        String codeB = Encryption.encryptData(encryptionKey, usernameB.getBytes());


        Espresso.onView((withId(R.id.activityProviderAddPatient_CodeEditText))).perform(ViewActions.typeText(codeB));
        Espresso.onView(withId(R.id.activityProviderAddPatient_CodeEditText)).check(matches(withText(codeB)));
        Espresso.onView(withId(R.id.activityProviderAddPatient_AddButton)).perform(ViewActions.click());
    }

    /**
     * Test adding a patient with an un-hyphenated phone number in contact info
     * @throws EncryptionException
     */
    @Test
    public void testProviderAddPatientC() throws EncryptionException {
        assertEquals(providerAddPatient.getActivity().getClass(), ProviderAddPatientActivity.class);

        String usernameC = patientC.getUsername();
        String codeC = Encryption.encryptData("CODE", usernameC.getBytes());


        Espresso.onView((withId(R.id.activityProviderAddPatient_CodeEditText))).perform(ViewActions.typeText(codeC));
        Espresso.onView(withId(R.id.activityProviderAddPatient_CodeEditText)).check(matches(withText(codeC)));
        Espresso.onView(withId(R.id.activityProviderAddPatient_AddButton)).perform(ViewActions.click());
    }
}
