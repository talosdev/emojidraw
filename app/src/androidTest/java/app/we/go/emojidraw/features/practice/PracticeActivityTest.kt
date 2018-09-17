package app.we.go.emojidraw.features.practice

import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import app.we.go.emojidraw.R
import app.we.go.emojidraw.ThisApplication
import app.we.go.emojidraw.arch.di.TestApplicationComponent

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.swipeRight
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText

@RunWith(AndroidJUnit4::class)
class PracticeActivityTest {

    @Rule
    @JvmField
    var rule = ActivityTestRule(PracticeActivity::class.java)

    private var duration: Int = 0


    @Before
    fun setUp() {
        duration = (ThisApplication.getComponent(rule.activity) as TestApplicationComponent)
            .plusPracticeComponent()
            .practiceDuration()!!
    }

    @Test
    fun whenTimeExpires_thenTimeoutPopupShown() {

        onView(ViewMatchers.withText(R.string.draw_prompt)).check(matches(isDisplayed()))

        // Sleeping in an espresso test is not a recommended technique, but in our
        // case, this is exactly what we want to do, so that the timer expires
        Thread.sleep(((duration + 1) * 1000).toLong())

        onView(withText(R.string.time_out)).check(matches(isDisplayed()))
    }


    @Test
    fun whenAllEmojisGuessed_thenWinPopupShown() {
        onView(withText(R.string.draw_prompt)).check(matches(isDisplayed()))

        onView(withId(R.id.drawingArea)).perform(swipeRight())

        onView(withText(R.string.win)).check(matches(isDisplayed()))
    }

    @Test
    fun whenSkip_thenCheatedPopupShown() {
        onView(withText(R.string.draw_prompt)).check(matches(isDisplayed()))

        onView(withId(R.id.skipButton)).perform(click())
        onView(withId(R.id.drawingArea)).perform(swipeRight())

        onView(withText(R.string.good_work)).check(matches(isDisplayed()))
    }


    @Test
    fun whenWinAndTimePasses_thenTimeoutPopupNotShown() {
        onView(withText(R.string.draw_prompt)).check(matches(isDisplayed()))

        onView(withId(R.id.drawingArea)).perform(swipeRight())

        onView(withText(R.string.win)).check(matches(isDisplayed()))

        Thread.sleep(((duration + 1) * 1000).toLong())

        onView(withText(R.string.time_out)).check(doesNotExist())
    }

}
