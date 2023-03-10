package com.example.space

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.space.core.Constants
import com.example.space.di.AppModule
import com.example.space.fakes.FakeMediaLibraryRepository
import com.example.space.nasa_media_library.presentation.view_models.MediaLibraryViewModel
import com.example.space.nasa_media_library.presentation.view_models.VideoDataViewModel
import com.example.space.presentation.MainScaffold
import com.example.space.presentation.SideNavigationDrawer
import com.example.space.ui.theme.SpaceTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
@UninstallModules(AppModule::class)
class MediaLibraryScreenTests {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var mainActivity: MainActivity
    private lateinit var libraryViewModel: MediaLibraryViewModel
    private lateinit var videoDataViewModel: VideoDataViewModel
    private val repository = FakeMediaLibraryRepository()
    private lateinit var navController: TestNavHostController

    @OptIn(ExperimentalMaterial3Api::class)
    @Before
    fun setUp() {
        mainActivity = composeTestRule.activity
        hiltRule.inject()
        libraryViewModel = MediaLibraryViewModel(repository)
        videoDataViewModel = VideoDataViewModel(repository)

        composeTestRule.activity.apply {
            setContent {
                SpaceTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {

                        navController = TestNavHostController(LocalContext.current)
                        navController.navigatorProvider.addNavigator(ComposeNavigator())
                        val filterType = remember { mutableStateOf("") }
                        val backgroundType = remember { mutableStateOf(Constants.NO_BACKGROUND) }
                        val drawerState = rememberDrawerState(DrawerValue.Closed)
                        val scope = rememberCoroutineScope()
                        val title = remember { mutableStateOf("NASA Media Library") }

                        SideNavigationDrawer(
                            navController = navController,
                            drawerState = drawerState,
                            scope = scope,
                            title = title
                        ) {
                            MainScaffold(
                                filterType = filterType,
                                drawerState = drawerState,
                                scope = scope,
                                backgroundType = backgroundType,
                                title = title,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }


    @Test
    fun test_toolbar() {
        composeTestRule.waitForIdle()
        composeTestRule
            .onNodeWithTag("Toolbar", false)
            .assertIsDisplayed()
    }

    @Test
    fun test_search_field() {
        composeTestRule
            .onNodeWithTag("Search", false)
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()
            .performTextInput("success")
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("Search", false)
            .performImeAction()
    }

    @Test
    fun test_list_card() {
        composeTestRule
            .onNodeWithTag("Search", false)
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()
            .performTextInput("success")
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("Search", false)
            .performImeAction()
        composeTestRule.waitForIdle()
        Thread.sleep(2000)

        val cards = composeTestRule
            .onAllNodes(hasTestTag("List Card"), false)
        for (i in cards.fetchSemanticsNodes().indices) {
            cards[i].assertIsDisplayed()
                .assertHasClickAction()
        }
        cards.onFirst()
            .performClick()

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("Details Screen", false)
            .assertIsDisplayed()
    }
}