package com.example.disneycodechallenge

import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.disneycodechallenge.comic.hub.ComicViewHolder
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class DisneyCodeChallengeActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val activityRule = ActivityScenarioRule(DisneyCodeChallengeActivity::class.java)

    @Test
    fun loadComicListAndNavigateToComicDetail() {

        onView(allOf(instanceOf(TextView::class.java), withParent(withId(R.id.tool_bar)))).check(
            matches(withText("Comics"))
        )
        //TODO : Remove this line Thread.sleep(2000).
        // The class IdlingThreadPoolExecutor should fix the problem but is not working.
        // Other solution is create our own idle resource.
        Thread.sleep(2000)
        onView(withId(R.id.comic_list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<ComicViewHolder>(
                    0,
                    click()
                )
            )
        onView(allOf(instanceOf(TextView::class.java), withParent(withId(R.id.tool_bar)))).check(
            matches(withText("Comic Detail"))
        )
    }
}