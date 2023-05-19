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
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.samm.space.common.presentation.MainScaffold
import com.samm.space.common.presentation.SideNavigationDrawer
import com.samm.space.pages.nasa_media_library_page.presentation.view_models.MediaLibraryViewModel
import com.samm.space.pages.nasa_media_library_page.util.LibraryUiEvent
import com.samm.space.ui.theme.SpaceTheme
import com.samm.space.util.FakeResponseTrigger
import com.samm.space.util.test_tags.MediaLibraryTestTags.detailsScreenTag
import com.samm.space.util.test_tags.MediaLibraryTestTags.listCardTag
import com.samm.space.util.test_tags.MediaLibraryTestTags.searchFieldTag
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
        composeTestRule.onNodeWithTag(searchFieldTag, true)
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performTextInput("Test")

        composeTestRule.onNodeWithTag(searchFieldTag, true)
            .performImeAction()
    }

    @Test
    fun test_library_list_cards() {
        val listOfCards = composeTestRule.onAllNodes(hasTestTag(listCardTag), true)

        composeTestRule.waitUntil(3000) {
            listOfCards.fetchSemanticsNodes().isNotEmpty()
        }

        for (index in 0 until listOfCards.fetchSemanticsNodes().size) {
            listOfCards[index]
                .assertIsDisplayed()
                .performClick()

            composeTestRule.waitUntil(3000) {
                composeTestRule.onAllNodes(hasTestTag(detailsScreenTag), true)
                    .fetchSemanticsNodes().isNotEmpty()
            }
            composeTestRule.onNodeWithTag(detailsScreenTag)
                .assertIsDisplayed()

            composeTestRule.waitForIdle()
            pressBackButton(composeTestRule)
        }
    }
}