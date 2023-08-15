package com.imannuel.petabencana.ui.home


import android.app.DatePickerDialog
import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.imannuel.petabencana.R
import com.imannuel.petabencana.ui.MainActivity
import com.imannuel.petabencana.ui.saved.SavedFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MapsFragmentTest {


    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun mapIsDisplayed() {
        onView(withId(R.id.map)).check(matches(isDisplayed()))
    }

    @Test
    fun testDatePickerDialogIsDisplayed() {
        onView(withId(R.id.menu_period)).perform(click())

        onView(withClassName(equalTo(DatePickerDialog::class.java.name))).check(matches(isDisplayed()))
    }

    @Test
    fun navigationToSavedFragmentSuccessfully() {
        onView(withId(R.id.menu_saved)).perform(click())

        onView(allOf(withId(R.id.savedFragment), isDisplayed()))

    }


    @Test
    fun navigationToSettingsFragmentSuccessfully() {
        onView(withId(R.id.menu_settings)).perform(click())

        onView(allOf(withId(R.id.settingFragment), isDisplayed()))

    }


}