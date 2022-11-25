package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.ext.truth.content.IntentSubject.assertThat;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.core.internal.deps.guava.collect.Iterables;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LogInInstrumentedTest {
    private String TEST_USER = "chris";
    private int SLEEP_TIME = 1000;

    private Intent initializeLogInIntent() {
        Context ctx = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent();
        intent.setClass(ctx, LoginActivity.class);
        return intent;
    }

//    @Test
//    public void BostonWeatherTest() throws Exception {
//        initializeUserProvider("Boston");
//        Intent intent = initializeIntent("Boston");
//
//        ActivityScenario.launch(intent);
//        Thread.sleep(SLEEP_TIME);
//        onView(withText("WEATHER")).perform(click());
//        Thread.sleep(SLEEP_TIME);
//
//        onView(withId(R.id.welcomeText)).check(matches(withText("Welcome to Boston")));
//        removeCityFromUserProvider("Boston");
//    }
//
//    @Test
//    public void OrlandoWeatherTest() throws Exception{
//        initializeUserProvider("Orlando");
//        Intent intent = initializeIntent("Orlando");
//
//        ActivityScenario.launch(intent);
//        Thread.sleep(SLEEP_TIME);
//        onView(withText("WEATHER")).perform(click());
//        Thread.sleep(SLEEP_TIME);
//
//        onView(withId(R.id.welcomeText)).check(matches(withText("Welcome to Orlando")));
//        removeCityFromUserProvider("Orlando");
//    }

    @Test
    public void LogInTest1() throws Exception{
        String test_name = "Adam";
        String test_password = "123456";
        Intent intent = initializeLogInIntent();
        ActivityScenario.launch(intent);
        Thread.sleep(SLEEP_TIME);
        onView(withId(R.id.userName)).perform(click()).perform(typeText(test_name));
        onView(withId(R.id.userName)).check(matches(withText(test_name)));
        Thread.sleep(SLEEP_TIME);
        onView(withId(R.id.userPassword)).perform(click()).perform(typeText(test_password));
        onView(withId(R.id.userPassword)).check(matches(withText(test_password)));
        Thread.sleep(SLEEP_TIME);
        onView(withId(R.id.buttonLogin)).perform(click());
    }
}