package com.ayoprez.timetracking.view

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.ayoprez.timetracking.R
import org.hamcrest.Matchers.not
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep


@RunWith(AndroidJUnit4::class)
class MainActivityUITest {

    private lateinit var mDevice: UiDevice

    @Before
    fun setUp() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        mDevice.pressHome()

        mDevice.wait(Until.hasObject(By.pkg(getLauncherPackageName()).depth(0)), 1000)

        ActivityScenario.launch(MainActivity::class.java)

        sleep(1000)
    }

    @Test
    fun shouldDisplayToolbar() {
        onView(withId(R.id.toolbar))
            .check(matches(isDisplayed()))
    }

    @Test
    fun shouldHaveListButtonInToolbar() {
        onView(withId(R.id.action_list))
            .check(matches(isDisplayed()))
    }

    @Test
    fun shouldHaveRightTitleInToolbar() {
        onView(withText(R.string.app_name))
            .check(matches(withParent(withId(R.id.toolbar))))
    }

    @Test
    fun shouldDisplayWhiteTextInToolbar() {
        onView(withText(R.string.app_name))
            .check(matches(hasTextColor(R.color.white)))
    }

    @Test
    fun shouldHaveClickableButtonInToolbar() {
        onView(withId(R.id.action_list))
            .check(matches(isClickable()))
    }

    @Test
    fun shouldDisplayEmptyCounter() {
        onView(withId(R.id.counter))
            .check(matches(isDisplayed()))
    }

    @Test
    fun shouldGoToListActivity() {
        onView(withId(R.id.action_list)).perform(click())
        sleep(3000)
        onView(withId(R.id.action_add)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldStartCounter() {
        mDevice.click(250, 250)
        sleep(3000)
        onView(withId(R.id.counter)).check(matches(withText("00:00:03")))
    }

    @Test
    fun shouldDisplayButtons() {
        mDevice.click(250, 250)
        onView(withId(R.id.pauseButton)).check(matches(isDisplayed()))
        onView(withId(R.id.stopButton)).check(matches(isDisplayed()))
        onView(withId(R.id.resumeButton)).check(matches(not(isDisplayed())))
    }

    @Test
    fun shouldDisplayResumeButton() {
        mDevice.click(250, 250)

        onView(withId(R.id.pauseButton)).perform(click())

        onView(withId(R.id.pauseButton)).check(matches(not(isDisplayed())))
        onView(withId(R.id.stopButton)).check(matches(isDisplayed()))
        onView(withId(R.id.resumeButton)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayPauseButton() {
        mDevice.click(250, 250)

        onView(withId(R.id.pauseButton)).perform(click())

        sleep(1000)

        onView(withId(R.id.resumeButton)).perform(click())

        sleep(1000)

        onView(withId(R.id.pauseButton)).check(matches(isDisplayed()))
        onView(withId(R.id.stopButton)).check(matches(isDisplayed()))
        onView(withId(R.id.resumeButton)).check(matches(not(isDisplayed())))
    }

    @Test
    fun shouldStopCounter() {
        mDevice.click(250, 250)

        sleep(3000)

        onView(withId(R.id.pauseButton)).perform(click())

        sleep(3000)

        onView(withId(R.id.counter)).check(matches(withText("00:00:03")))
    }

    @Test
    fun shouldResumeCounter() {
        mDevice.click(250, 250)

        sleep(3000)

        onView(withId(R.id.pauseButton)).perform(click())

        sleep(3000)

        onView(withId(R.id.resumeButton)).perform(click())

        sleep(1000)

        onView(withId(R.id.counter)).check(matches(withText("00:00:04")))
    }

    @Test
    fun shouldDisplayDialogAfterStopCounter() {
        mDevice.click(250, 250)

        sleep(3000)

        onView(withId(R.id.stopButton)).perform(click())

        sleep(3000)

        onView(withId(R.id.dialog_discard_button)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayRightTimeInDialogAfterStopCounter() {
        mDevice.click(250, 250)

        sleep(3000)

        onView(withId(R.id.stopButton)).perform(click())

        sleep(3000)

        onView(withId(R.id.dialog_book_time_text_view)).check(matches(withText("00:00:03")))
    }

    @Test
    fun shouldDismissDialogAfterClickDiscard() {
        mDevice.click(250, 250)

        sleep(3000)

        onView(withId(R.id.stopButton)).perform(click())

        sleep(3000)

        onView(withId(R.id.dialog_discard_button)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayInitialStateInCounterAfterStop() {
        mDevice.click(250, 250)

        sleep(3000)

        onView(withId(R.id.stopButton)).perform(click())

        sleep(3000)

        onView(withId(R.id.dialog_discard_button)).perform(click())

        onView(withId(R.id.counter)).check(matches(withText("00:00:00")))
    }

    private fun getLauncherPackageName(): String {
        // Create launcher Intent
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)

        // Use PackageManager to get the launcher package name
        val pm = InstrumentationRegistry.getInstrumentation().context.packageManager
        val resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
        return resolveInfo.activityInfo.packageName
    }
}