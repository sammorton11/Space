package com.samm.space.tests.media_library_screen_tests

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
import com.samm.space.MainActivity
import com.samm.space.core.Constants
import com.samm.space.di.AppModule
import com.samm.space.fakes.FakeMediaLibraryRepository
import com.samm.space.nasa_media_library.presentation.view_models.MediaLibraryViewModel
import com.samm.space.nasa_media_library.presentation.view_models.VideoDataViewModel
import com.samm.space.presentation.MainScaffold
import com.samm.space.presentation.SideNavigationDrawer
import com.samm.space.ui.theme.SpaceTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 *  Todo:
 *      - Exoplayer is not loading in tests
 *      - Images in image card details screen not loading
 */

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
    fun test_list_card_image() {
        composeTestRule
            .onNodeWithTag("Search", false)
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()
            .performTextInput("success")

        composeTestRule.onNodeWithTag("Search", false)
            .performImeAction()

        composeTestRule.waitUntil {
            composeTestRule
                .onAllNodes(hasTestTag("List Card"), false)
                .fetchSemanticsNodes().isNotEmpty()
        }

        val cards = composeTestRule
            .onAllNodes(hasTestTag("List Card"), false)
        for (i in cards.fetchSemanticsNodes().indices) {
            cards[i].assertIsDisplayed()
                .assertHasClickAction()
        }
        val cardImage = cards.onFirst()
        cardImage.performClick()

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("Details Screen", false)
            .assertIsDisplayed()
        composeTestRule.waitUntil {
            composeTestRule.onAllNodes(hasTestTag("Expandable Details Card - Clickable"))
                .fetchSemanticsNodes().size == 1
        }

        composeTestRule.onNodeWithTag("Expandable Details Card - Clickable",false)
            .assertIsDisplayed()
            .assertHasClickAction()
        composeTestRule.onNodeWithTag("Download Button",false)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag("Share Button",false)
            .assertIsDisplayed()
    }

    @Test
    fun test_list_card_video() {
        composeTestRule
            .onNodeWithTag("Search", false)
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()
            .performTextInput("success")

        composeTestRule.onNodeWithTag("Search", false)
            .performImeAction()

        composeTestRule.waitUntil {
            composeTestRule
                .onAllNodes(hasTestTag("List Card"), false)
                .fetchSemanticsNodes().isNotEmpty()
        }

        val cards = composeTestRule
            .onAllNodes(hasTestTag("List Card"), false)
        for (i in cards.fetchSemanticsNodes().indices) {
            cards[i].assertIsDisplayed()
                .assertHasClickAction()
        }
        val cardVideo = cards[1]
        cardVideo.performClick()

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("Details Screen", false)
            .assertIsDisplayed()

        composeTestRule.waitUntil {
            composeTestRule.onAllNodes(hasTestTag("Expandable Details Card - Clickable"))
                .fetchSemanticsNodes().size == 1
        }

        composeTestRule.onNodeWithTag("Expandable Details Card - Clickable",false)
            .assertIsDisplayed()
            .assertHasClickAction()
        composeTestRule.onNodeWithTag("Download Button",false)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag("Share Button",false)
            .assertIsDisplayed()
        composeTestRule.waitForIdle()
    }

    @Test
    fun test_list_card_audio() {
        composeTestRule
            .onNodeWithTag("Search", false)
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()
            .performTextInput("success")

        composeTestRule.onNodeWithTag("Search", false)
            .performImeAction()

        composeTestRule.waitUntil {
            composeTestRule
                .onAllNodes(hasTestTag("List Card"), false)
                .fetchSemanticsNodes().isNotEmpty()
        }

        val cards = composeTestRule
            .onAllNodes(hasTestTag("List Card"), false)

        for (i in cards.fetchSemanticsNodes().indices) {
            cards[i].assertIsDisplayed()
                .assertHasClickAction()
        }

        val cardAudio = cards.onLast() // fake audio card is the last in the list of fake cards

        cardAudio.performClick()

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("Details Screen", false)
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag("Play Button", false)
            .assertIsDisplayed()
            .assertHasClickAction()

        composeTestRule.onNodeWithTag("Restart Button",false)
            .assertIsDisplayed()
            .assertHasClickAction()

        composeTestRule.onNodeWithTag("Details Image Card",false)
            .assertIsDisplayed()

        composeTestRule.waitUntil {
            composeTestRule.onAllNodes(hasTestTag("Expandable Details Card - Clickable"))
                .fetchSemanticsNodes().size == 1
        }
        composeTestRule.onNodeWithTag("Expandable Details Card - Clickable",false)
            .assertIsDisplayed()
            .assertHasClickAction()

        composeTestRule.onNodeWithTag("Download Button",false)
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag("Share Button",false)
            .assertIsDisplayed()
    }
}