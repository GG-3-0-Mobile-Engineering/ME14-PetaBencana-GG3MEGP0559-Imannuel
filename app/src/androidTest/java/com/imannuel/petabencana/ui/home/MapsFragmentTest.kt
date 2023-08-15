package com.imannuel.petabencana.ui.home


import android.app.DatePickerDialog
import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.imannuel.petabencana.R
import com.imannuel.petabencana.ui.MainActivity
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matchers
import org.junit.Assert.*
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
    fun bottomSheetDisplayed(){
        onView(withId(R.id.persistent_bottom_sheet)).check(matches(isDisplayed()))
    }

    @Test
    fun disasterChipGroupDisplayedAndClickable(){
        onView(withId(R.id.disasterChipGroup)).check(matches(isDisplayed()))
        onView(withId(R.id.floodChip)).perform(click())
        onView(withId(R.id.earthquakeChip)).perform(click())
    }

    @Test
    fun searchBarFunctionalityWorking(){
        onView(withId(R.id.search_bar)).check(matches(isDisplayed()))
        onView(withId(R.id.search_bar)).perform(click())

        onView(isAssignableFrom(EditText::class.java)).perform(typeText("DI Yogyakarta")).perform(pressImeActionButton())
    }

    @Test
    fun datePickerDialogDisplayed(){
        onView(withId(R.id.menu_period)).check(matches(isDisplayed()))
        onView(withId(R.id.menu_period)).perform(click())
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