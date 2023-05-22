package com.samm.space.tests.integration_tests

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.samm.space.common.presentation.MainScaffold
import com.samm.space.common.presentation.SideNavigationDrawer
import com.samm.space.pages.nasa_media_library_page.presentation.view_models.MediaLibraryViewModel
import com.samm.space.pages.nasa_media_library_page.util.LibraryUiEvent
import com.samm.space.tests.ui_tests.BaseTest
import com.samm.space.ui.theme.SpaceTheme
import com.samm.space.util.FakeResponseTrigger
import com.samm.space.util.test_tags.FavoriteScreenTestTags.FAVORITES_SCREEN_TAG
import com.samm.space.util.test_tags.GlobalTestTags.FAVORITES_SCREEN_NAV_BUTTON
import com.samm.space.util.test_tags.GlobalTestTags.LIBRARY_SCREEN_NAV_BUTTON
import com.samm.space.util.test_tags.GlobalTestTags.LIST_CARD_TAG
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
class FavoritesButtonTest: BaseTest() {

    private lateinit var viewModel: MediaLibraryViewModel
    @OptIn(ExperimentalMaterial3Api::class)
    @Before
    fun setup() {
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
                        viewModel = hiltViewModel()
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
    fun testFavoritesButton() {

        // Wait for the cards to appear
        val libraryListCards = composeTestRule.onAllNodes(hasTestTag(LIST_CARD_TAG), true)
        composeTestRule.waitUntil(3000) {
            libraryListCards.fetchSemanticsNodes().isNotEmpty()
        }

        // Click the favorite button on each card in the list of cards
        for (index in 0 until libraryListCards.fetchSemanticsNodes().size) {
            val favoritesButton = libraryListCards[index].onChildren()[1]
            favoritesButton.performClick()
        }

        // Go to the favorites screen
        composeTestRule.onNodeWithTag(FAVORITES_SCREEN_NAV_BUTTON).performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(FAVORITES_SCREEN_TAG).assertIsDisplayed()

        // Wait for the Favorite cards to appear
        val favoritesList = composeTestRule.onAllNodes(hasTestTag(LIST_CARD_TAG), true)
        composeTestRule.waitUntil(3000) {
            favoritesList.fetchSemanticsNodes().isNotEmpty()
        }

        // All favorites cards should appear at this point -- Click the favorites button to remove
        while(favoritesList.fetchSemanticsNodes().isNotEmpty()) {
            val favoritesListCard = favoritesList[0]
            val favoritesButton = favoritesListCard.onChildren()[1]

            favoritesListCard.assertIsDisplayed()
            favoritesButton.performClick() // remove from favorites
        }

        // Go to the Library screen
        composeTestRule.onNodeWithTag(LIBRARY_SCREEN_NAV_BUTTON).performClick()
        assert(libraryListCards.fetchSemanticsNodes().isEmpty())
    }


    @Test
    fun favorites_screen_test() {
        composeTestRule.onNodeWithTag(FAVORITES_SCREEN_NAV_BUTTON, true)
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(FAVORITES_SCREEN_TAG).assertIsDisplayed()
    }
}
