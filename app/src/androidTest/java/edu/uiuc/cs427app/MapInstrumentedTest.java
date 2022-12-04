package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.containsString;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MapInstrumentedTest {
    private String TEST_USER = "chris";
    private String BOSTON = "Boston";
    private String ORLANDO = "Orlando";
    private int SLEEP_TIME = 3000;
    private Context ctx = InstrumentationRegistry.getInstrumentation().getTargetContext();
    UserProvider provider = new UserProvider(ctx, TEST_USER);

    @Before@After
    public void cleanup() {
        provider.removeCity(BOSTON);
        provider.removeCity(ORLANDO);
    }

    @Test
    public void BostonMapTest() throws Exception {
        City testCity = new City(BOSTON, BOSTON, 42.3600825, -71.0588801);
        provider.addCity(testCity);
        Intent intent = new Intent();
        intent.setClass(ctx, MainActivity.class);
        intent.putExtra("city", BOSTON);
        intent.putExtra("username", TEST_USER);
        ActivityScenario.launch(intent);
        Thread.sleep(SLEEP_TIME);
        onView(withText("MAP")).perform(click());
        Thread.sleep(SLEEP_TIME);
        onView(withId(R.id.cityName)).check(matches(withText(BOSTON)));
        onView(withId(R.id.latlng)).check(matches(withText(containsString("lat/lng: (42.3600825,-71.0588801)"))));
    }

    @Test
    public void OrlandoMapTest() throws Exception {
        City testCity = new City(ORLANDO, ORLANDO, 28.5383832, -81.3789269);
        provider.addCity(testCity);
        Intent intent = new Intent();
        intent.setClass(ctx, MainActivity.class);
        intent.putExtra("city", ORLANDO);
        intent.putExtra("username", TEST_USER);
        ActivityScenario.launch(intent);
        Thread.sleep(SLEEP_TIME);
        onView(withText("MAP")).perform(click());
        Thread.sleep(SLEEP_TIME);
        onView(withId(R.id.cityName)).check(matches(withText(ORLANDO)));
        onView(withId(R.id.latlng)).check(matches(withText(containsString("lat/lng: (28.5383832,-81.3789269)"))));
    }

}

