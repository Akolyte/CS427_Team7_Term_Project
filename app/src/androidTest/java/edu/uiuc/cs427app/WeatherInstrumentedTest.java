package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class WeatherInstrumentedTest {
    private Intent initializeIntent(String city, String username) {
        Context ctx = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent detailIntent = new Intent();
        detailIntent.setClass(ctx, DetailsActivity.class);
        detailIntent.putExtra("city", city);
        detailIntent.putExtra("username", username);
        return detailIntent;
    }

    private void initializeUserProvider(String cityToAdd, String username) {
        Context ctx = InstrumentationRegistry.getInstrumentation().getTargetContext();
        UserProvider provider = new UserProvider(ctx, username);
        City testCity = new City(cityToAdd, cityToAdd, 0, 0);
        provider.addCity(testCity);
    }

    @Test
    public void BostonWeatherTest() throws Exception {
       Intent detailIntent = initializeIntent("Boston", "Chris");
       initializeUserProvider("Boston", "Chris");
       ActivityScenario.launch(detailIntent);
       Thread.sleep(1000);

       onView(withId(R.id.welcomeText)).check(matches(withText("Welcome to Boston")));
    }

    @Test
    public void OrlandoWeatherTest() throws Exception{
        Intent detailIntent = initializeIntent("Orlando", "Chris");
        initializeUserProvider("Orlando", "Chris");
        ActivityScenario.launch(detailIntent);
        Thread.sleep(1000);

        onView(withId(R.id.welcomeText)).check(matches(withText("Welcome to Orlando")));
    }
}
