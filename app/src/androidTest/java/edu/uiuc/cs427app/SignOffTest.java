package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;


import org.junit.After;
import org.junit.Before;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SignOffTest {
    private String TEST_USER = "Adam";
    private String TEST_PASSWORD = "123456";
    private int SLEEP_TIME = 1000;
    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    AccountManager am = AccountManager.get(appContext);
    Account account = new Account(TEST_USER, "com.team7");
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule =
            new ActivityTestRule<>(LoginActivity.class);
    @Before
    public void CreateTestAccount() {
        am.addAccountExplicitly(account, TEST_PASSWORD, null);
    }
    @After
    public void CleanUserAfter() {
        am.removeAccountExplicitly(account);
    }


    @Test
    public void LogOffTest() throws Exception{
        onView(withId(R.id.userName)).perform(click()).perform(typeText(TEST_USER));
        Thread.sleep(SLEEP_TIME);
        onView(withId(R.id.userPassword)).perform(click()).perform(typeText(TEST_PASSWORD));
        Thread.sleep(SLEEP_TIME);
        onView(withId(R.id.buttonLogin)).perform(click());
        Thread.sleep(SLEEP_TIME);
        onView(withId(R.id.textView4)).check(matches(withText("List of Locations")));  //Check if it is at main activity
        onView(withId(R.id.buttonSignOff)).perform(click());  // click sign off
        Thread.sleep(SLEEP_TIME);
        onView(withId(R.id.buttonLogin)).check(matches(withText("Login"))); // Check if it returns to login activity
    }


}