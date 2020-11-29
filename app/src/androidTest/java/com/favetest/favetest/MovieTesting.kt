package com.favetest.favetest

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.favetest.favetest.view.MovieActivity
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class MovieTesting {
    // To launch the mentioned activity under testing
    @get:Rule
    var mActivityRule: ActivityTestRule<MovieActivity> = ActivityTestRule(MovieActivity::class.java)

    @Test
    fun testRecyclerView() {
        onView(withId(R.id.list_movie)).check(matches(isDisplayed()))
    }

    @Test
    fun testPerformClickRecyclerViewVisibility() {
        onView(withId(R.id.list_movie))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    3,
                    click()
                )
            )

        onView(withId(R.id.book_movie)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.book_movie)).perform(click());
    }

    @Test
    fun testRecyclerViewVisibility() {
        onView(withId(R.id.list_movie)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }
}