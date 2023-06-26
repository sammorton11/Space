package com.samm.space.tests.ui_tests

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.ComposeNavigator
import com.samm.space.common.presentation.MainScaffold
import com.samm.space.common.presentation.SideNavigationDrawer
import com.samm.space.features.nasa_media_library_page.presentation.view_models.MediaLibraryViewModel
import com.samm.space.features.nasa_media_library_page.util.LibraryUiEvent.SearchLibrary
import com.samm.space.tests.screens.MediaLibraryScreen
import com.samm.space.ui.theme.SpaceTheme
import com.samm.space.util.FakeResponseTrigger
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
class MediaLibraryUITest: BaseTest() {

    private lateinit var libraryScreen: MediaLibraryScreen
    
    @OptIn(ExperimentalMaterial3Api::class)
    @Before
    fun setUp() {
        libraryScreen = MediaLibraryScreen(composeTestRule, navController)
        hiltRule.inject()
        composeTestRule.activity.apply {
            setContent {
                SpaceTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {

                        navController.navigatorProvider.addNavigator(ComposeNavigator())

                        val drawerState = rememberDrawerState(DrawerValue.Closed)
                        val viewModel: MediaLibraryViewModel = hiltViewModel()
                        viewModel.sendEvent(SearchLibrary(FakeResponseTrigger.SUCCESS.value))

                        SideNavigationDrawer(
                            navController = navController,
                            drawerState = drawerState
                        ) {
                            MainScaffold(
                                event = viewModel::sendEvent,
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
        libraryScreen.typeInSearchField()
            .clickImeButton()
    }

    @Test
    fun test_library_list_cards() {
        libraryScreen.waitForListCards(3000)
            .clickListCards()
    }

    @Test
    fun test_favorite_button() {
        libraryScreen.waitForListCards(3000)
            .clickListCardFavoritesButton()
    }

    @Test
    fun test_options_menu() {
        libraryScreen.waitForListCards(3000)
            .openOptionsMenu()
            .openChangeBackgroundMenu()
            .backgroundMenuItemsVisibility()
            .closeChangeBackgroundMenu()
            .openSortingMenu()
            .filterList()
    }
}