package cs.ualberta.ca.medlog;

import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

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

/**
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
 *         userM1433372, patrickf, Response to StackOverflow question "Can't get ApplicationContext in Espresso tests"
 *         https://stackoverflow.com/a/46582539
 *         https://stackoverflow.com/users/1433372/userm1433372
 *         https://stackoverflow.com/users/774398/patrickf
 *         Answered 2017-10-05, edited 2018-02-06, accessed 2018-12-01
 *
 * </p>
 *
 * @author Tem Tamre
 * @version 1.0
 * @see cs.ualberta.ca.medlog.activity.ProviderAddPatientActivity
 * @see CareProvider
 * @see Patient
 *
 */

public class ProviderAddPatientTest {
    private CareProvider provider;
    private Patient patientA;
    private Patient patientB;
    private Patient patientC;

    @Rule
    public ActivityTestRule<ProviderLoginActivity> providerRule = new ActivityTestRule<>(ProviderLoginActivity.class);


    /* Preparation for testing (this functionality should be tested in another test file) */
    @Before
    private void prepareUsers() {
        ContactInfo infoA = new ContactInfo("780-473-7373", "swoods@gmail.ca");
        ContactInfo infoB = new ContactInfo("7804343058", "garcia@hotmail.ca");
        ContactInfo infoC = new ContactInfo("587-352-0413", "ahrgustav@gmail.com");

        provider = new CareProvider("PAPTJohnSmith");
        patientA = new Patient(infoA, "PAPTWoodsSamuel");
        patientB = new Patient(infoB, "PAPTGGarcia");
        patientC = new Patient(infoC, "PAPTGustavAhr");

        Database db = new Database(InstrumentationRegistry.getInstrumentation().getTargetContext());
        db.saveProvider(provider);
        db.savePatient(patientA);
        db.savePatient(patientB);
        // Not saving patientC on purpose
    }

    @Before
    private void login() {
        Espresso.onView((withId(R.id.activityProviderLogin_UsernameEditText)))
                .perform(ViewActions.typeText(provider.getUsername()));
        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.activityProviderLogin_LoginButton)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.activityProviderLogin_LoginButton)).check(matches(withText(provider.getUsername())));
    }

    /**
     * Test adding a patient with standard formatting
     * @throws EncryptionException
     */
    @Test
    public void testProviderAddPatientA() throws EncryptionException {
        assertEquals(providerRule.getActivity().getClass(), ProviderLoginActivity.class);

        String usernameA = patientA.getUsername();
        String encryptionKey = Resources.getSystem().getString(R.string.EncryptionKey);
        String codeA = Encryption.encryptData(encryptionKey, usernameA.getBytes());

        prepareUsers();
        login();

        Espresso.onView((withId(R.id.activityProviderAddPatient_CodeEditText))).perform(ViewActions.typeText(codeA));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.activityProviderAddPatient_CodeEditText)).check(matches(withText(codeA)));
        Espresso.onView(withId(R.id.activityProviderAddPatient_AddButton)).perform(ViewActions.click());
    }

    /**
     * Test adding a patient with standard formatting
     * @throws EncryptionException
     */
    @Test
    public void testProviderAddPatientB() throws EncryptionException {
        assertEquals(providerRule.getActivity().getClass(), ProviderLoginActivity.class);

        String usernameB = patientB.getUsername();
        String encryptionKey = Resources.getSystem().getString(R.string.EncryptionKey);
        String codeB = Encryption.encryptData(encryptionKey, usernameB.getBytes());

        prepareUsers();
        login();

        Espresso.onView((withId(R.id.activityProviderAddPatient_CodeEditText))).perform(ViewActions.typeText(codeB));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.activityProviderAddPatient_CodeEditText)).check(matches(withText(codeB)));
        Espresso.onView(withId(R.id.activityProviderAddPatient_AddButton)).perform(ViewActions.click());
    }

    /**
     * Test adding a patient that was created but not pushed to the database
     * @throws EncryptionException
     */
    @Test
    public void testProviderAddPatientC() throws EncryptionException {
        assertEquals(providerRule.getActivity().getClass(), ProviderLoginActivity.class);

        String usernameC = patientC.getUsername();
        String encryptionKey = Resources.getSystem().getString(R.string.EncryptionKey);
        String codeC = Encryption.encryptData(encryptionKey, usernameC.getBytes());

        prepareUsers();
        login();

        Espresso.onView((withId(R.id.activityProviderAddPatient_CodeEditText))).perform(ViewActions.typeText(codeC));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.activityProviderAddPatient_CodeEditText)).check(matches(withText(codeC)));
        Espresso.onView(withId(R.id.activityProviderAddPatient_AddButton)).perform(ViewActions.click());
    }
}
