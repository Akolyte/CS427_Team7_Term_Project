package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.view.View;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import static org.hamcrest.Matchers.not;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UserSignupTest {
    @Rule
    public ActivityTestRule<LoginActivity> activityRule
            = new ActivityTestRule<>(LoginActivity.class);

    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    AccountManager am = AccountManager.get(appContext);

    private final String USER1 = "ruhangm2";
    private final String PWD1 = "123456";
    Account account = new Account(USER1, "com.team7");
    private final int SLEEP_TIME = 1000;

    @Before
    public void Setup() {
        am.removeAccountExplicitly(account);
        Intents.init();
    }

    @After
    public void Teardown() {
        am.removeAccountExplicitly(account);
        Intents.release();
    }

    // Test if an account exist in type "com.team7" by username
    private boolean CheckUserExist(String username) {
        Account[] accounts = am.getAccountsByType("com.team7");
        boolean found = false;
        for(Account account: accounts) {
            if(account.name.equals(USER1)) {
                found = true;
            }
        }
        return found;
    }

    // Normal sign up
    @Test
    public void NormalSignupTest() throws InterruptedException {
        boolean exist = CheckUserExist(USER1);
        assertEquals(exist, false);

        onView(withId(R.id.userName)).perform(click()).perform(typeText(USER1));
        onView(withId(R.id.userPassword)).perform(click()).perform(typeText(PWD1));
        onView(withId(R.id.buttonRegister)).perform(click());
        Thread.sleep(SLEEP_TIME);

        // check if theme select activity is displayed
        onView(withId(R.id.buttonConfirm)).perform(click());
        intended(hasComponent(ThemeSelectActivity.class.getName()));

        exist = CheckUserExist(USER1);
        assertEquals(exist, true);
    }

    // Sign up the same username twice
    @Test
    public void ExistedSignupTest() throws InterruptedException {
        am.addAccountExplicitly(account, PWD1, null);

        onView(withId(R.id.userName)).perform(click()).perform(typeText(USER1));
        onView(withId(R.id.userPassword)).perform(click()).perform(typeText(PWD1));
        Thread.sleep(SLEEP_TIME);

        onView(withId(R.id.buttonRegister)).perform(click());
        onView(withText("Account already existed " + USER1))
                .inRoot(withDecorView(not(activityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

        Thread.sleep(SLEEP_TIME);
    }

    // Test if input is valid
    @Test
    public void InputErrorSignupTest1() throws InterruptedException {
        onView(withId(R.id.userName)).perform(click()).perform(typeText(USER1));
        onView(withId(R.id.buttonRegister)).perform(click());

        onView(withText("Username or password cannot be empty"))
                .inRoot(withDecorView(not(activityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

        Thread.sleep(SLEEP_TIME);
    }

    // Test if input is valid
    @Test
    public void InputErrorSignupTest2() throws InterruptedException {
        onView(withId(R.id.userPassword)).perform(click()).perform(typeText(PWD1));
        onView(withId(R.id.buttonRegister)).perform(click());

        onView(withText("Username or password cannot be empty"))
                .inRoot(withDecorView(not(activityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

        Thread.sleep(SLEEP_TIME);
    }

}