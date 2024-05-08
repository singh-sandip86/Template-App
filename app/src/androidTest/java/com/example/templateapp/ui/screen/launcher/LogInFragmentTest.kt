package com.example.templateapp.ui.screen.launcher

import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.templateapp.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class LogInFragmentTest {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<LauncherActivity>()

    companion object {
        const val LOGIN_EMAIL = "nikhil+agent3@torinit.com"
        const val LOGIN_PASSWORD = "kF!@367SApyd"
    }

    @Test
    fun testLoginButton_expectedLoginSuccess() {
        // Arrange
        onView(withId(R.id.edtEmailId)).perform(typeText(LOGIN_EMAIL))
        onView(withId(R.id.edtPassword)).perform(typeText(LOGIN_PASSWORD), closeSoftKeyboard())

        // Act
        onView(withId(R.id.btnLogIn)).perform(click())
        runBlocking { delay(10000L) } // Login API call takes place, so blocking execution for some time

        // Assert
        onView(withId(R.id.toolbar_title)).check(matches(withText("Hello, Julianna")))
    }
}