package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.ext.truth.content.IntentSubject.assertThat;

import static org.hamcrest.Matchers.not;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.core.internal.deps.guava.collect.Iterables;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import android.accounts.Account;
import android.accounts.AccountManager;

import org.junit.Before;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LogInInstrumentedTest {
    private String TEST_USER = "Adam";
    private String TEST_PASSWORD = "123456";
    private int SLEEP_TIME = 1000;

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule =
            new ActivityTestRule<>(LoginActivity.class);
    @Before
    public void CreateTestAccount() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        AccountManager am = AccountManager.get(appContext);
        Account account = new Account(TEST_USER, "com.team7");
        am.addAccountExplicitly(account, TEST_PASSWORD, null);
    }


    @Test
    public void LogInTest1() throws Exception{
        onView(withId(R.id.userName)).perform(click()).perform(typeText(TEST_USER));
        onView(withId(R.id.userName)).check(matches(withText(TEST_USER)));
        Thread.sleep(SLEEP_TIME);
        onView(withId(R.id.userPassword)).perform(click()).perform(typeText(TEST_PASSWORD));
        onView(withId(R.id.userPassword)).check(matches(withText(TEST_PASSWORD)));
        Thread.sleep(SLEEP_TIME);
        onView(withId(R.id.buttonLogin)).perform(click());
        onView(withText("Welcome back " + TEST_USER))
                .inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        Thread.sleep(SLEEP_TIME);
        onView(withId(R.id.textView4)).check(matches(withText("List of Locations")));  //Check if it is at main activity
        onView(withId(R.id.buttonSignOff)).perform(click());  // click sign off
        Thread.sleep(SLEEP_TIME);
        onView(withId(R.id.buttonLogin)).check(matches(withText("Login"))); // Check if it returns to login activity
    }

    @Test
    public void LogInErrorTest1() throws Exception{
        onView(withId(R.id.userName)).perform(click()).perform(typeText(TEST_USER));
        onView(withId(R.id.userName)).check(matches(withText(TEST_USER)));
        Thread.sleep(SLEEP_TIME);
        onView(withId(R.id.userPassword)).perform(click()).perform(typeText(TEST_PASSWORD+"1"));
        onView(withId(R.id.userPassword)).check(matches(withText(TEST_PASSWORD+"1")));
        Thread.sleep(SLEEP_TIME);
        onView(withId(R.id.buttonLogin)).perform(click());
        onView(withText(R.string.login_error))
                .inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void LogInErrorTest2() throws Exception{
        onView(withId(R.id.userName)).perform(click()).perform(typeText(TEST_USER+"1"));
        onView(withId(R.id.userName)).check(matches(withText(TEST_USER+"1")));
        Thread.sleep(SLEEP_TIME);
        onView(withId(R.id.userPassword)).perform(click()).perform(typeText(TEST_PASSWORD));
        onView(withId(R.id.userPassword)).check(matches(withText(TEST_PASSWORD)));
        Thread.sleep(SLEEP_TIME);
        onView(withId(R.id.buttonLogin)).perform(click());
        onView(withText(R.string.login_error))
                .inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }
}