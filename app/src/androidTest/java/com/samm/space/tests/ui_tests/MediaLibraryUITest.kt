package com.samm.space.tests.ui_tests

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.samm.space.common.presentation.MainScaffold
import com.samm.space.common.presentation.SideNavigationDrawer
import com.samm.space.pages.nasa_media_library_page.presentation.view_models.MediaLibraryViewModel
import com.samm.space.pages.nasa_media_library_page.util.LibraryUiEvent
import com.samm.space.ui.theme.SpaceTheme
import com.samm.space.util.FakeResponseTrigger
import com.samm.space.util.test_tags.GlobalTestTags
import com.samm.space.util.test_tags.GlobalTestTags.LIST_CARD_TAG
import com.samm.space.util.test_tags.GlobalTestTags.ToolbarTestTags.CHANGE_BACKGROUND_BUTTON_TAG
import com.samm.space.util.test_tags.GlobalTestTags.ToolbarTestTags.CHANGE_BACKGROUND_DROP_DOWN_TAG
import com.samm.space.util.test_tags.GlobalTestTags.ToolbarTestTags.FILTER_ALL_BUTTON_TAG
import com.samm.space.util.test_tags.GlobalTestTags.ToolbarTestTags.FILTER_AUDIO_BUTTON_TAG
import com.samm.space.util.test_tags.GlobalTestTags.ToolbarTestTags.FILTER_IMAGES_BUTTON_TAG
import com.samm.space.util.test_tags.GlobalTestTags.ToolbarTestTags.FILTER_VIDEOS_BUTTON_TAG
import com.samm.space.util.test_tags.GlobalTestTags.ToolbarTestTags.GALAXY_BACKGROUND_BUTTON_TAG
import com.samm.space.util.test_tags.GlobalTestTags.ToolbarTestTags.NO_BACKGROUND_BUTTON_TAG
import com.samm.space.util.test_tags.GlobalTestTags.ToolbarTestTags.OPTIONS_MENU_BUTTON_TAG
import com.samm.space.util.test_tags.GlobalTestTags.ToolbarTestTags.PLANETS_BACKGROUND_BUTTON_TAG
import com.samm.space.util.test_tags.GlobalTestTags.ToolbarTestTags.SCI_FI_PLANETS_BUTTON_TAG
import com.samm.space.util.test_tags.GlobalTestTags.ToolbarTestTags.SORTING_MENU_DROP_DOWN_TAG
import com.samm.space.util.test_tags.GlobalTestTags.ToolbarTestTags.SORT_BUTTON_TAG
import com.samm.space.util.test_tags.GlobalTestTags.ToolbarTestTags.SPACE_MAN_BACKGROUND_BUTTON_TAG
import com.samm.space.util.test_tags.MediaLibraryTestTags.DetailsTestTags.DETAILS_SCREEN_TAG
import com.samm.space.util.test_tags.MediaLibraryTestTags.SEARCH_FIELD_TAG
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
class MediaLibraryUITest: BaseTest() {
    
    @OptIn(ExperimentalMaterial3Api::class)
    @Before
    fun setUp() {
        hiltRule.inject()
        composeTestRule.activity.apply {
            setContent {
                SpaceTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {

                        navController = TestNavHostController(LocalContext.current)
                        navController.navigatorProvider.addNavigator(ComposeNavigator())

                        val drawerState = rememberDrawerState(DrawerValue.Closed)
                        val viewModel: MediaLibraryViewModel = hiltViewModel()
                        viewModel.sendEvent(LibraryUiEvent.SearchLibrary(FakeResponseTrigger.SUCCESS.value))

                        SideNavigationDrawer(
                            navController = navController,
                            drawerState = drawerState
                        ) {
                            MainScaffold(
                                event = viewModel::sendEvent,
                                drawerState = drawerState,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }

    @Test
    fun test_search_field() {
        composeTestRule.onNodeWithTag(SEARCH_FIELD_TAG, true)
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performTextInput("Test")

        composeTestRule.onNodeWithTag(SEARCH_FIELD_TAG, true)
            .performImeAction()
    }

    @Test
    fun test_library_list_cards() {

        val listOfCards = composeTestRule.onAllNodes(hasTestTag(LIST_CARD_TAG), true)

        composeTestRule.waitUntil(3000) {
            listOfCards.fetchSemanticsNodes().isNotEmpty()
        }

        for (index in 0 until listOfCards.fetchSemanticsNodes().size) {

            listOfCards[index].onChildren().printToLog("List Card Child")
            val favoritesButton = listOfCards[index].onChildren()[1]

            favoritesButton
                .assertIsDisplayed()
                .performClick()

            listOfCards[index]
                .assertIsDisplayed()
                .performClick()

            composeTestRule.waitUntil(3000) {
                composeTestRule.onAllNodes(hasTestTag(DETAILS_SCREEN_TAG), true)
                    .fetchSemanticsNodes().isNotEmpty()
            }

            composeTestRule.onNodeWithTag(DETAILS_SCREEN_TAG)
                .assertIsDisplayed()

            composeTestRule.waitForIdle()
            pressBackButton(composeTestRule)
        }
    }


    @Test
    fun test_options_menu() {
        // Open the options menu
        composeTestRule.onNodeWithTag(OPTIONS_MENU_BUTTON_TAG)
            .assertIsDisplayed()
            .performClick()

        composeTestRule.onNodeWithTag(GlobalTestTags.ToolbarTestTags.OPTIONS_MENU_DROP_DOWN_TAG)
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag(SORT_BUTTON_TAG)
            .assertIsDisplayed()

        // Open the Change Background menu
        composeTestRule.onNodeWithTag(CHANGE_BACKGROUND_BUTTON_TAG)
            .assertIsDisplayed()
            .performClick()

        // The Change Background menu items should be displayed
        composeTestRule.onNodeWithTag(CHANGE_BACKGROUND_DROP_DOWN_TAG)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(PLANETS_BACKGROUND_BUTTON_TAG)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(SPACE_MAN_BACKGROUND_BUTTON_TAG)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(GALAXY_BACKGROUND_BUTTON_TAG)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(SCI_FI_PLANETS_BUTTON_TAG)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(NO_BACKGROUND_BUTTON_TAG)
            .assertIsDisplayed()

        // Close the Change Background Menu
        composeTestRule.onNodeWithTag(OPTIONS_MENU_BUTTON_TAG)
            .assertIsDisplayed()
            .performClick()

        composeTestRule.waitForIdle()
        val listOfCards = composeTestRule.onAllNodes(hasTestTag(LIST_CARD_TAG), true)

        // Open the Soring Menu
        composeTestRule.onNodeWithTag(SORT_BUTTON_TAG)
            .performClick()

        // Menu items should be displayed
        composeTestRule.onNodeWithTag(SORTING_MENU_DROP_DOWN_TAG)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(FILTER_IMAGES_BUTTON_TAG)
            .assertIsDisplayed()
            .performClick()

        composeTestRule.waitForIdle()
        assert(listOfCards.fetchSemanticsNodes().size == 1)


        composeTestRule.onNodeWithTag(FILTER_VIDEOS_BUTTON_TAG)
            .assertIsDisplayed()
            .performClick()
        composeTestRule.waitForIdle()
        assert(listOfCards.fetchSemanticsNodes().size == 1)

        composeTestRule.onNodeWithTag(FILTER_AUDIO_BUTTON_TAG)
            .assertIsDisplayed()
            .performClick()
        composeTestRule.waitForIdle()
        assert(listOfCards.fetchSemanticsNodes().size == 1)

        composeTestRule.onNodeWithTag(FILTER_ALL_BUTTON_TAG)
            .assertIsDisplayed()
            .performClick()
        composeTestRule.waitForIdle()
        assert(listOfCards.fetchSemanticsNodes().size == 1)

        // Close the menu
        composeTestRule.onNodeWithTag(SORT_BUTTON_TAG)
            .performClick()

        composeTestRule.waitForIdle()
        Thread.sleep(2000)
    }
}