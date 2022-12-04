
package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AddCityTest {
    private String TEST_USER = "abrielle";
    private String CHICAGO = "Chicago";
    private String CHAMPAIGN = "Champaign";
    private int SLEEP_TIME = 3000;

    private Intent initializeIntent() {
        Context ctx = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent();
        intent.setClass(ctx, MainActivity.class);
        intent.putExtra("username", TEST_USER);
        return intent;
    }

    @Test
    public void addChicago() throws Exception {
        Intent intent = initializeIntent();

        ActivityScenario.launch(intent);
        Thread.sleep(SLEEP_TIME);
        onView(withId(R.id.buttonAddLocation)).perform(click());
        Thread.sleep(SLEEP_TIME);
        onView(withId(R.id.locationInput)).perform(replaceText(CHICAGO)).perform(click());
        Thread.sleep(SLEEP_TIME);
        ViewInteraction textView = onView(
                allOf(withText("Chicago"),
                        withParent(withParent(withId(R.id.citiesLayout))),
                        isDisplayed()));
        textView.check(matches(withText("Chicago")));
    }

    @Test
    public void addChampaign() throws Exception {
        Intent intent = initializeIntent();
        ActivityScenario.launch(intent);
        Thread.sleep(SLEEP_TIME);
        onView(withId(R.id.buttonAddLocation)).perform(click());
        Thread.sleep(SLEEP_TIME);
        onView(withId(R.id.locationInput)).perform(replaceText(CHAMPAIGN)).perform(click());
        Thread.sleep(SLEEP_TIME);
        ViewInteraction textView = onView(
                allOf(withText("Champaign"),
                        withParent(withParent(withId(R.id.citiesLayout))),
                        isDisplayed()));
        textView.check(matches(withText("Champaign")));
    }

}

