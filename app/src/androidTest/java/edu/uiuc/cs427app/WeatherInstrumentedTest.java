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
public class WeatherInstrumentedTest {
    private String TEST_USER = "chris";
    private String BOSTON = "Boston";
    private String ORLANDO = "Orlando";
    private int SLEEP_TIME = 3000;

    private Intent initializeIntent(String city) {
        Context ctx = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent();
        intent.setClass(ctx, MainActivity.class);
        intent.putExtra("city", city);
        intent.putExtra("username", TEST_USER);
        return intent;
    }

    private void initializeUserProvider(String cityToAdd) {
        Context ctx = InstrumentationRegistry.getInstrumentation().getTargetContext();
        UserProvider provider = new UserProvider(ctx, TEST_USER);
        City testCity = new City(cityToAdd, cityToAdd, 0, 0);
        provider.addCity(testCity);
    }

    private void removeCityFromUserProvider(String city) {
        Context ctx = InstrumentationRegistry.getInstrumentation().getTargetContext();
        UserProvider provider = new UserProvider(ctx, TEST_USER);
        provider.removeCity(city);
    }

    private void mockOrlandoToBoston() {
        Context ctx = InstrumentationRegistry.getInstrumentation().getTargetContext();
        UserProvider provider = new UserProvider(ctx, TEST_USER);
        City mockCity = provider.getCityById(ORLANDO);
        // City ID remains ORLANDO but name is now BOSTON
        mockCity.setCityName(BOSTON);
        provider.removeCity(ORLANDO);
        provider.addCity(mockCity);
    }

    private void assertWeatherInformationIsPopulated() {
        onView(withId(R.id.cityInfo)).check(matches(withText(containsString("Weather:"))));
        onView(withId(R.id.cityInfo)).check(matches(withText(containsString("Temperature:"))));
        onView(withId(R.id.cityInfo)).check(matches(withText(containsString("Humidity:"))));
        onView(withId(R.id.cityInfo)).check(matches(withText(containsString("Wind Speed:"))));
    }

    private void assertWelcomeText(String city) {
        String textToMatch = "Welcome to " + city;
        onView(withId(R.id.welcomeText)).check(matches(withText(textToMatch)));
    }

    @Before
    public void cleanup() {
        removeCityFromUserProvider(BOSTON);
        removeCityFromUserProvider(ORLANDO);
    }

    @After
    public void finalCleanup() {
        cleanup();
    }

    @Test
    public void BostonWeatherTest() throws Exception {
        initializeUserProvider(BOSTON);
        Intent intent = initializeIntent(BOSTON);

        ActivityScenario.launch(intent);
        Thread.sleep(SLEEP_TIME);
        onView(withText("WEATHER")).perform(click());
        Thread.sleep(SLEEP_TIME);

        assertWelcomeText(BOSTON);
        assertWeatherInformationIsPopulated();
    }

    @Test
    public void OrlandoWeatherTest() throws Exception{
        initializeUserProvider(ORLANDO);
        Intent intent = initializeIntent(ORLANDO);

        ActivityScenario.launch(intent);
        Thread.sleep(SLEEP_TIME);
        onView(withText("WEATHER")).perform(click());
        Thread.sleep(SLEEP_TIME);

        assertWelcomeText(ORLANDO);
        assertWeatherInformationIsPopulated();
    }

    @Test
    public void MockOrlandoAsBostonWeatherTest() throws Exception{
        initializeUserProvider(ORLANDO);
        Intent intent = initializeIntent(ORLANDO);

        ActivityScenario.launch(intent);
        Thread.sleep(SLEEP_TIME);
        mockOrlandoToBoston();
        onView(withText("WEATHER")).perform(click());
        Thread.sleep(SLEEP_TIME);

        assertWelcomeText(BOSTON);
        assertWeatherInformationIsPopulated();
    }
}
