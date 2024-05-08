package com.example.templateapp.ui.screen.dashboard

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.templateapp.R
import com.example.templateapp.ui.screen.dashboard.adapter.DashboardPagingAdapter
import com.example.templateapp.ui.screen.home.HomeActivity
import com.example.templateapp.util.TemplateIdlingResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
@ExperimentalCoroutinesApi
class DashboardFragmentTest {

    companion object {
        private const val LIST_FIRST_POSITION = 1
    }

    @get:Rule
    val activityScenarioRule = activityScenarioRule<HomeActivity>()

    private val templateIdlingResource = TemplateIdlingResource("DashboardListing")

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(templateIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(templateIdlingResource)
    }

    @Test
    fun itemAtTopOfList_checkIdOnClick() {
        runBlocking { delay(20000L) }
        templateIdlingResource.setIdle(true)
        onView(withId(R.id.rv_dashboard)).perform(
            RecyclerViewActions.actionOnItemAtPosition<DashboardPagingAdapter.DashboardViewHolder>(
                LIST_FIRST_POSITION,
                click()
            )
        )

        onView(withText("108354 selected")).check(matches(isDisplayed()))
    }
}