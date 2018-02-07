package app.we.go.emojidraw.features.practice;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.we.go.emojidraw.R;
import app.we.go.emojidraw.ThisApplication;
import app.we.go.emojidraw.arch.di.TestApplicationComponent;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class PracticeActivityTest {

    @Rule
    public ActivityTestRule<PracticeActivity> rule = new ActivityTestRule<>(PracticeActivity.class);
    private int duration;


    @Before
    public void setUp() throws Exception {
        duration = ((TestApplicationComponent) ThisApplication.getComponent(rule.getActivity()))
                .plusPracticeComponent()
                .practiceDuration();
    }

    @Test
    public void whenTimeExpires_thenTimeoutPopupShown() throws Exception {

        onView(ViewMatchers.withText(R.string.draw_prompt)).check(matches(isDisplayed()));

        // Sleeping in an espresso test is not a recommended technique, but in our
        // case, this is exactly what we want to do, so that the timer expires
        Thread.sleep((duration + 1) * 1000);

        onView(withText(R.string.time_out)).check(matches(isDisplayed()));
    }



    @Test
    public void whenAllEmojisGuessed_thenWinPopupShown() throws Exception {
        onView(withText(R.string.draw_prompt)).check(matches(isDisplayed()));

        onView(withId(R.id.drawing_area)).perform(swipeRight());

        onView(withText(R.string.win)).check(matches(isDisplayed()));
    }

    @Test
    public void whenSkip_thenCheatedPopupShown() throws Exception {
        onView(withText(R.string.draw_prompt)).check(matches(isDisplayed()));

        onView(withId(R.id.skip_button)).perform(click());
        onView(withId(R.id.drawing_area)).perform(swipeRight());

        onView(withText(R.string.good_work)).check(matches(isDisplayed()));
    }



    @Test
    public void whenWinAndTimePasses_thenTimeoutPopupNotShown() throws Exception {
        onView(withText(R.string.draw_prompt)).check(matches(isDisplayed()));

        onView(withId(R.id.drawing_area)).perform(swipeRight());

        onView(withText(R.string.win)).check(matches(isDisplayed()));

        Thread.sleep((duration + 1) * 1000);

        onView(withText(R.string.time_out)).check(doesNotExist());
    }

}
