package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.view.View;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import static org.hamcrest.Matchers.not;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Timestamp;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UserSignupTest {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    private String USER1 = timestamp.toString();
    private String PWD1 = "111";
    private int SLEEP_TIME = 1000;

    @Rule
    public ActivityTestRule<LoginActivity> activityRule
            = new ActivityTestRule<>(LoginActivity.class);

//    @Test
//    public void useAppContext() {
//        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        assertEquals("edu.uiuc.cs427app", appContext.getPackageName());
//    }

    @Test
    public void NormalSignupTest() throws InterruptedException {
        // Normal Signup
        onView(withId(R.id.userName)).perform(click()).perform(typeText(USER1));
        onView(withId(R.id.userPassword)).perform(click()).perform(typeText(PWD1));
        Thread.sleep(SLEEP_TIME);

        onView(withId(R.id.buttonRegister)).perform(click());
        onView(withText("Account registered " + USER1))
                .inRoot(withDecorView(not(activityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

        Thread.sleep(SLEEP_TIME);
        onView(withId(R.id.buttonConfirm)).perform(click());
        Thread.sleep(SLEEP_TIME);

        // Signup same username twice
        onView(withId(R.id.userName)).perform(click()).perform(typeText(USER1));
        onView(withId(R.id.userPassword)).perform(click()).perform(typeText(PWD1));
        Thread.sleep(SLEEP_TIME);

        onView(withId(R.id.buttonRegister)).perform(click());
        onView(withText("Account already existed " + USER1))
                .inRoot(withDecorView(not(activityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

        Thread.sleep(SLEEP_TIME);
    }

    @Test
    public void InputErrorSignupTest1() throws InterruptedException {
        onView(withId(R.id.userName)).perform(click()).perform(typeText(USER1));
        onView(withId(R.id.buttonRegister)).perform(click());

        onView(withText("Username or password cannot be empty"))
                .inRoot(withDecorView(not(activityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

        Thread.sleep(SLEEP_TIME);
    }

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