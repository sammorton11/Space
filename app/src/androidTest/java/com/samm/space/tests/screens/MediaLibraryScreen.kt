package com.samm.space.tests.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavController
import com.samm.space.Page
import com.samm.space.util.test_tags.FavoriteScreenTestTags.FAVORITES_SCREEN_TAG
import com.samm.space.util.test_tags.FavoriteScreenTestTags.FIRST_CARD
import com.samm.space.util.test_tags.FavoriteScreenTestTags.SECOND_CARD
import com.samm.space.util.test_tags.FavoriteScreenTestTags.THIRD_CARD
import com.samm.space.util.test_tags.GlobalTestTags
import com.samm.space.util.test_tags.MediaLibraryTestTags
import com.samm.space.util.test_tags.MediaLibraryTestTags.DetailsTestTags.DETAILS_SCREEN_TAG


class MediaLibraryScreen (
    private val composeTestRule: ComposeTestRule,
    private val navController: NavController
): Page {

    private fun waitForAudioDetailsScreen() = audioDetailsScreen.assertIsDisplayed()
    private fun waitForImageDetailsScreen() = imageDetailsScreen.assertIsDisplayed()
    private fun waitForVideoDetailsScreen() = videoDetailsScreen.assertIsDisplayed()

    private val searchField by lazy {
        composeTestRule.onNodeWithTag(MediaLibraryTestTags.SEARCH_FIELD_TAG)
    }

    private val listItems by lazy {
        composeTestRule.onAllNodes(
            hasTestTag("List Card"),
            useUnmergedTree = true
        )
    }

    private val favoriteScreenButton by lazy {
        composeTestRule.onNodeWithTag(GlobalTestTags.FAVORITES_SCREEN_NAV_BUTTON)
    }

    private val audioDetailsScreen by lazy {
        composeTestRule.onNodeWithTag("Audio $DETAILS_SCREEN_TAG")
    }

    private val imageDetailsScreen by lazy {
        composeTestRule.onNodeWithTag("Image $DETAILS_SCREEN_TAG")
    }

    private val videoDetailsScreen by lazy {
        composeTestRule.onNodeWithTag("Video $DETAILS_SCREEN_TAG")
    }

    private val optionsMenuButton by lazy {
        composeTestRule.onNodeWithTag(GlobalTestTags.ToolbarTestTags.OPTIONS_MENU_BUTTON_TAG)
    }

    private val expandedOptionsMenu by lazy {
        composeTestRule.onNodeWithTag(GlobalTestTags.ToolbarTestTags.OPTIONS_MENU_DROP_DOWN_TAG)
    }

    private val sortMenuButton by lazy {
        composeTestRule.onNodeWithTag(GlobalTestTags.ToolbarTestTags.SORT_BUTTON_TAG)
    }

    private val expandedSortingMenu by lazy {
        composeTestRule.onNodeWithTag(GlobalTestTags.ToolbarTestTags.SORTING_MENU_DROP_DOWN_TAG)
    }

    private val changeBackgroundMenuButton by lazy {
        composeTestRule.onNodeWithTag(GlobalTestTags.ToolbarTestTags.CHANGE_BACKGROUND_BUTTON_TAG)
    }

    private val expandedChangeBackgroundMenu by lazy {
        composeTestRule.onNodeWithTag(GlobalTestTags.ToolbarTestTags.CHANGE_BACKGROUND_DROP_DOWN_TAG)
    }

    private val planetsBackgroundButton by lazy {
        composeTestRule.onNodeWithTag(GlobalTestTags.ToolbarTestTags.PLANETS_BACKGROUND_BUTTON_TAG)
    }

    private val spaceManBackgroundButton by lazy {
        composeTestRule.onNodeWithTag(GlobalTestTags.ToolbarTestTags.SPACE_MAN_BACKGROUND_BUTTON_TAG)
    }

    private val galaxyBackgroundButton by lazy {
        composeTestRule.onNodeWithTag(GlobalTestTags.ToolbarTestTags.GALAXY_BACKGROUND_BUTTON_TAG)
    }

    private val sciFiBackgroundButton by lazy {
        composeTestRule.onNodeWithTag(GlobalTestTags.ToolbarTestTags.SCI_FI_PLANETS_BUTTON_TAG)
    }

    private val noBackgroundButton by lazy {
        composeTestRule.onNodeWithTag(GlobalTestTags.ToolbarTestTags.NO_BACKGROUND_BUTTON_TAG)
    }

    override fun isDisplayed(tag: String): Page {
        composeTestRule.onNodeWithTag(tag, true).assertIsDisplayed()
        return this
    }

    override fun pressBackButton(): MediaLibraryScreen {
        navController.popBackStack()
        return this
    }

    fun clickListCards(): MediaLibraryScreen {

        val firstCard = listItems[0]
        val secondCard = listItems[1]
        val thirdCard = listItems[2]

        firstCard.performClick()
        waitForImageDetailsScreen()
        pressBackButton()

        secondCard.performClick()
        waitForAudioDetailsScreen()
        pressBackButton()

        thirdCard.performClick()
        waitForVideoDetailsScreen()
        pressBackButton()

        return this
    }

    fun clickListCardFavoritesButton(): MediaLibraryScreen {
        for (index in 0 until listItems.fetchSemanticsNodes().size) {
            val favoriteButton = listItems[index].onChildren()[1]
            favoriteButton.performClick()
        }
        favoriteScreenButton.performClick()

        isDisplayed(FAVORITES_SCREEN_TAG)
        isDisplayed(FIRST_CARD)
        isDisplayed(SECOND_CARD)
        isDisplayed(THIRD_CARD)
        return this
    }

    fun waitForListCards(timeout: Long): MediaLibraryScreen {
        composeTestRule.waitUntil(timeout) {
            listItems.fetchSemanticsNodes().isNotEmpty()
        }
        return this
    }

    fun typeInSearchField(): MediaLibraryScreen {
        searchField.performTextInput("Test")
        return this
    }

    fun clickImeButton(): MediaLibraryScreen {
        searchField.performImeAction()
        return this
    }

    fun openOptionsMenu(): MediaLibraryScreen {
        optionsMenuButton
            .assertIsDisplayed()
            .performClick()

        expandedOptionsMenu.assertIsDisplayed()
        sortMenuButton.assertIsDisplayed()

        return this
    }

    fun openChangeBackgroundMenu(): MediaLibraryScreen {
        changeBackgroundMenuButton
            .assertIsDisplayed()
            .performClick()

        return this
    }


    fun backgroundMenuItemsVisibility(): MediaLibraryScreen {
        expandedChangeBackgroundMenu.assertIsDisplayed()
        planetsBackgroundButton.assertIsDisplayed()
        spaceManBackgroundButton.assertIsDisplayed()
        galaxyBackgroundButton.assertIsDisplayed()
        sciFiBackgroundButton.assertIsDisplayed()
        noBackgroundButton.assertIsDisplayed()

        return this
    }

    fun closeChangeBackgroundMenu(): MediaLibraryScreen {
        optionsMenuButton.assertIsDisplayed()
            .performClick()

        return this
    }

    fun openSortingMenu(): MediaLibraryScreen {
        sortMenuButton.performClick()
        return this
    }

    fun filterList(): MediaLibraryScreen {

        val sortingMenuItems = composeTestRule.onAllNodes(hasTestTag("Filter List Button"))
        val allButton = sortingMenuItems[0]
        val imageButton = sortingMenuItems[1]
        val videoButton = sortingMenuItems[2]
        val audioButton = sortingMenuItems[3]

        expandedSortingMenu.assertIsDisplayed()

        imageButton.performClick()
        composeTestRule.waitForIdle()
        listItems.onFirst().performClick()
        imageDetailsScreen.assertIsDisplayed()
        pressBackButton()

        videoButton.performClick()
        composeTestRule.waitForIdle()
        listItems.onFirst().performClick()
        videoDetailsScreen.assertIsDisplayed()
        pressBackButton()


        audioButton.performClick()
        composeTestRule.waitForIdle()
        listItems.onFirst().performClick()
        audioDetailsScreen.assertIsDisplayed()
        pressBackButton()

        allButton.performClick()
        composeTestRule.waitForIdle()

        clickListCards() // all three cards should be present

        return this
    }
}
