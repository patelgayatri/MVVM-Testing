package com.techand.videoapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.schibsted.spain.barista.internal.matcher.DrawableMatcher.Companion.withDrawable
import com.techand.videoapp.di.idlingResource
import org.hamcrest.Matchers.allOf
import org.junit.Test



class PlayListFeature: BaseUiTest() {


    @Test
    fun displayScreenTitle() {
        assertDisplayed("Playlists")
    }

    @Test
    fun displayListOfPlayList() {

        assertRecyclerViewItemCount(R.id.playlist_list, 10)

        onView(
            allOf(
                withId(R.id.playlist_name),
                isDescendantOfA(nthChildOf(withId(R.id.playlist_list), 0))
            )
        )
            .check(matches(withText("Hard Rock Cafe")))
            .check(matches(isDisplayed()))
        onView(
            allOf(
                withId(R.id.playlist_category),
                isDescendantOfA(nthChildOf(withId(R.id.playlist_list), 0))
            )
        )
            .check(matches(withText("rock")))
            .check(matches(isDisplayed()))
        onView(
            allOf(
                withId(R.id.playlist_image),
                isDescendantOfA(nthChildOf(withId(R.id.playlist_list), 1))
            )
        )
            .check(matches(withDrawable(R.mipmap.ic_launcher)))
            .check(matches(isDisplayed()))
    }

    @Test
    fun displaysLoaderWhileFetchingThePlaylist() {

        IdlingRegistry.getInstance().unregister(idlingResource)
        assertDisplayed(R.id.loader)
    }

    @Test
    fun hideLoader() {

        assertNotDisplayed(R.id.loader)
    }

    @Test
    fun displaysRockImageForRockListItem(){

        onView(
            allOf(
                withId(R.id.playlist_image),
                isDescendantOfA(nthChildOf(withId(R.id.playlist_list), 0))
            )
        )
            .check(matches(withDrawable(R.drawable.ic_launcher_background)))
            .check(matches(isDisplayed()))
        onView(
            allOf(
                withId(R.id.playlist_image),
                isDescendantOfA(nthChildOf(withId(R.id.playlist_list), 3))
            )
        )
            .check(matches(withDrawable(R.drawable.ic_launcher_background)))
            .check(matches(isDisplayed()))
    }

    @Test
    fun navigateToDetailScreen(){
        onView(allOf(withId(R.id.playlist_image), isDescendantOfA(nthChildOf(withId(R.id.playlist_list), 0))))
            .perform(click())
        assertDisplayed(R.id.playlist_detail_root)
    }

}