package com.samm.space.tests.error_tests

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.samm.space.common.presentation.MainScaffold
import com.samm.space.common.presentation.SideNavigationDrawer
import com.samm.space.pages.nasa_media_library_page.presentation.view_models.MediaLibraryViewModel
import com.samm.space.tests.ui_tests.BaseTest
import com.samm.space.ui.theme.SpaceTheme
import com.samm.space.util.test_tags.GlobalTestTags.errorTag
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
class MediaLibraryErrorTests: BaseTest() {

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
                        val viewModel: MediaLibraryViewModel = hiltViewModel()
                        viewModel.getData("Mars")

                        SideNavigationDrawer(
                            navController = navController,
                            drawerState = drawerState,

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
    fun test_failed_response_media_library() {
        composeTestRule.onNodeWithTag(errorTag, true)
            .assertExists()
            .assertIsDisplayed()
            .assertTextEquals("Error: HTTP 404 Client Error")
    }
}