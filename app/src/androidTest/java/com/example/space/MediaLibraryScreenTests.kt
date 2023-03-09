package com.example.space

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.rememberNavController
import com.example.space.core.Constants
import com.example.space.di.AppModule
import com.example.space.fakes.FakeMediaLibraryRepository
import com.example.space.nasa_media_library.presentation.library_search_screen.LibrarySearchScreen
import com.example.space.nasa_media_library.presentation.view_models.MediaLibraryViewModel
import com.example.space.nasa_media_library.presentation.view_models.VideoDataViewModel
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
                        val navController = rememberNavController()
                        val filterType = remember { mutableStateOf("") }
                        val backgroundType = remember { mutableStateOf(Constants.NO_BACKGROUND) }

                        LibrarySearchScreen(
                            viewModel = libraryViewModel,
                            navController = navController,
                            filterType = filterType,
                            backgroundType = backgroundType
                        )
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
        composeTestRule.waitForIdle()
        composeTestRule
            .onNodeWithTag("Search", false)
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun test_list_card() {
        libraryViewModel.getData("success")
        composeTestRule.waitForIdle()
        val cards = composeTestRule
            .onAllNodes(hasTestTag("List Card"), false)
        for (i in cards.fetchSemanticsNodes().indices) {
            cards[i].assertIsDisplayed()
                .assertHasClickAction()
        }
    }
}