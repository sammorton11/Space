package com.samm.space.tests.ui_tests

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.samm.space.MainActivity
import com.samm.space.nasa_media_library_page.presentation.view_models.MediaLibraryViewModel
import com.samm.space.picture_of_the_day_page.presentation.ApodScreen
import com.samm.space.picture_of_the_day_page.presentation.ApodViewModel
import com.samm.space.presentation_common.MainScaffold
import com.samm.space.presentation_common.SideNavigationDrawer
import com.samm.space.ui.theme.SpaceTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import java.util.*

@HiltAndroidTest
open class BaseTest {

    @get:Rule(order = 0)
    val hiltRule by lazy { HiltAndroidRule(this) }
    @get:Rule(order = 1)
    val composeTestRule by lazy { createAndroidComposeRule<MainActivity>() }

    private lateinit var navController: TestNavHostController

    companion object {

        val jsonStringMediaLibrary = Companion::class.java.classLoader
            ?.getResource("res/raw/media_library_response.json")
            ?.readText()

        val jsonStringMetadata = Companion::class.java.classLoader
            ?.getResource("res/raw/video_metadata_response.json")
            ?.toString()

        val jsonStringMediaLibraryNullData = Companion::class.java.classLoader
            ?.getResource("res/raw/media_library_response_blank_data.json")
            ?.readText()

        val jsonStringApod = Companion::class.java.classLoader
            ?.getResource("res/raw/apod_response.json")
            ?.readText()
    }

    fun pressBackButton(composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>) {
        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }
    }

    fun apodScreenSetup() {
        composeTestRule.activity.apply {
            setContent {
                SpaceTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val viewModel: ApodViewModel = hiltViewModel()
                        ApodScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun mediaLibraryScreenSetup() {
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
                                viewModel = viewModel,
                                drawerState = drawerState,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}