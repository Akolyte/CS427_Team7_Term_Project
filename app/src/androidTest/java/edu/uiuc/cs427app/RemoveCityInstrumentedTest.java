package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.Set;

@RunWith(AndroidJUnit4.class)
public class RemoveCityInstrumentedTest {

    private String TEST_USER = "HojinTest";
    private int SLEEP_TIME = 2000;

    private Intent initializeIntent(String city) {
        Context ctx = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent();
        intent.setClass(ctx, DetailsActivity.class);
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

    private Set getCitiesFromUserProvider() {
        Context ctx = InstrumentationRegistry.getInstrumentation().getTargetContext();
        UserProvider provider = new UserProvider(ctx, TEST_USER);
        return provider.getCities();
    }

    @Test
    public void isCityAdded() throws Exception {
        initializeUserProvider("Champaign");
        Intent intent = initializeIntent("Champaign");
        ActivityScenario.launch(intent);
        Thread.sleep(SLEEP_TIME);
        assertTrue(!getCitiesFromUserProvider().isEmpty());
        onView(withId(R.id.deleteLocationButton)).perform(click());
    }

    @Test
    public void isCityRemoved() throws Exception {
        initializeUserProvider("Champaign");
        Intent intent = initializeIntent("Champaign");
        ActivityScenario.launch(intent);
        Thread.sleep(SLEEP_TIME);
        onView(withId(R.id.deleteLocationButton)).perform(click());
        Thread.sleep(SLEEP_TIME);
        assertTrue(getCitiesFromUserProvider().isEmpty());
    }
}
