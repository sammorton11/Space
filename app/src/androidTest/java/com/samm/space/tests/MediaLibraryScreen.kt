package com.samm.space.tests

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.samm.space.MainActivity
import com.samm.space.util.test_tags.FavoriteScreenTestTags
import com.samm.space.util.test_tags.GlobalTestTags
import com.samm.space.util.test_tags.MediaLibraryTestTags
import com.samm.space.util.test_tags.MediaLibraryTestTags.DetailsTestTags.DETAILS_SCREEN_TAG

class MediaLibraryScreen(private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>) {

    private val searchField by lazy {
        composeTestRule.onNodeWithTag(MediaLibraryTestTags.SEARCH_FIELD_TAG)
    }

    private val listItems by lazy {
        composeTestRule.onAllNodes(
            hasTestTag("List Card"),
            useUnmergedTree = true
        )
    }

    fun clickListCards(): MediaLibraryScreen  {

        listItems[0].performClick()
        waitForImageDetailsScreen()
        pressBackButton()

        listItems[1].performClick()
        waitForAudioDetailsScreen()
        pressBackButton()

        listItems[2].performClick()
        waitForVideoDetailsScreen()
        pressBackButton()

        return this
    }

    fun clickListCardFavoritesButton(): MediaLibraryScreen  {
        for (index in 0 until listItems.fetchSemanticsNodes().size) {
            val favoriteButton = listItems[index].onChildren()[1]
            favoriteButton.performClick()
            composeTestRule.onNodeWithTag(GlobalTestTags.FAVORITES_SCREEN_NAV_BUTTON)
                .performClick()
            composeTestRule.onNodeWithTag(FavoriteScreenTestTags.FAVORITES_SCREEN_TAG)
                .assertIsDisplayed()
            composeTestRule.onNodeWithTag("Card Title Mars Celebration")
            pressBackButton()
        }

        return this
    }

    fun waitForListCards(timeout: Long): MediaLibraryScreen {
        composeTestRule.waitUntil(timeout) {
            listItems.fetchSemanticsNodes().isNotEmpty()
        }
        return this
    }

    private fun waitForAudioDetailsScreen() {
        composeTestRule.onNodeWithTag("Audio $DETAILS_SCREEN_TAG")
            .assertIsDisplayed()
    }

    private fun waitForImageDetailsScreen() {
        composeTestRule.onNodeWithTag("Image $DETAILS_SCREEN_TAG")
            .assertIsDisplayed()
    }

    private fun waitForVideoDetailsScreen() {
        composeTestRule.onNodeWithTag("Video $DETAILS_SCREEN_TAG")
            .assertIsDisplayed()
    }


    fun typeInSearchField(): MediaLibraryScreen {
        searchField.performTextInput("Test")
        return this
    }

    fun clickImeButton(): MediaLibraryScreen {
        searchField.performImeAction()
        return this
    }

    private fun pressBackButton() {
        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }
    }

    fun openOptionsMenu(): MediaLibraryScreen {
        composeTestRule.onNodeWithTag(GlobalTestTags.ToolbarTestTags.OPTIONS_MENU_BUTTON_TAG)
            .assertIsDisplayed()
            .performClick()

        composeTestRule.onNodeWithTag(GlobalTestTags.ToolbarTestTags.OPTIONS_MENU_DROP_DOWN_TAG)
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag(GlobalTestTags.ToolbarTestTags.SORT_BUTTON_TAG)
            .assertIsDisplayed()

        return this
    }

    fun openChangeBackgroundMenu(): MediaLibraryScreen {
        composeTestRule.onNodeWithTag(GlobalTestTags.ToolbarTestTags.CHANGE_BACKGROUND_BUTTON_TAG)
            .assertIsDisplayed()
            .performClick()

        return this
    }

    fun backgroundMenuItemsVisibility(): MediaLibraryScreen {
        composeTestRule.onNodeWithTag(GlobalTestTags.ToolbarTestTags.CHANGE_BACKGROUND_DROP_DOWN_TAG)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(GlobalTestTags.ToolbarTestTags.PLANETS_BACKGROUND_BUTTON_TAG)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(GlobalTestTags.ToolbarTestTags.SPACE_MAN_BACKGROUND_BUTTON_TAG)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(GlobalTestTags.ToolbarTestTags.GALAXY_BACKGROUND_BUTTON_TAG)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(GlobalTestTags.ToolbarTestTags.SCI_FI_PLANETS_BUTTON_TAG)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(GlobalTestTags.ToolbarTestTags.NO_BACKGROUND_BUTTON_TAG)
            .assertIsDisplayed()

        return this
    }

    fun closeChangeBackgroundMenu(): MediaLibraryScreen {
        composeTestRule.onNodeWithTag(GlobalTestTags.ToolbarTestTags.OPTIONS_MENU_BUTTON_TAG)
            .assertIsDisplayed()
            .performClick()

        return this
    }

    fun openSortingMenu(): MediaLibraryScreen {
        composeTestRule.onNodeWithTag(GlobalTestTags.ToolbarTestTags.SORT_BUTTON_TAG)
            .performClick()

        return this
    }

    fun filterList(): MediaLibraryScreen {

        val sortingMenuItems = composeTestRule.onAllNodes(hasTestTag("Filter List Button"))
        val allButton = sortingMenuItems[0]
        val imageButton = sortingMenuItems[1]
        val videoButton = sortingMenuItems[2]
        val audioButton = sortingMenuItems[3]

        composeTestRule.onNodeWithTag(GlobalTestTags.ToolbarTestTags.SORTING_MENU_DROP_DOWN_TAG)
            .assertIsDisplayed()

        imageButton.performClick()
        composeTestRule.waitForIdle()
        listItems.onFirst().performClick()
        composeTestRule.onNodeWithTag("Image Details Screen").assertIsDisplayed()
        pressBackButton()

        videoButton.performClick()
        composeTestRule.waitForIdle()
        listItems.onFirst().performClick()
        composeTestRule.onNodeWithTag("Video Details Screen").assertIsDisplayed()
        pressBackButton()


        audioButton.performClick()
        composeTestRule.waitForIdle()
        listItems.onFirst().performClick()
        composeTestRule.onNodeWithTag("Audio Details Screen").assertIsDisplayed()
        pressBackButton()

        allButton.performClick()
        composeTestRule.waitForIdle()

        clickListCards() // all three cards should be present

        return this
    }
}
