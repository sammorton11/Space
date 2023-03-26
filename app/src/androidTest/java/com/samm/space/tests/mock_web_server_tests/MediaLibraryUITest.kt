package com.samm.space.tests.mock_web_server_tests

import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.samm.space.MainActivity
import com.samm.space.core.Constants
import com.samm.space.nasa_media_library.presentation.library_search_screen.LibraryScreenContent
import com.samm.space.nasa_media_library.presentation.view_models.MediaLibraryViewModel
import com.samm.space.ui.theme.SpaceTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MediaLibraryUITest {

    // Todo: Can't get list data to load in tests

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    val server = MockWebServer()
    private val jsonString = this.javaClass
        .classLoader?.getResource("res/raw/media_library_response.json")?.readText()

    private lateinit var navController: TestNavHostController

    lateinit var viewModel: MediaLibraryViewModel

    private fun successfulDispatcher() {
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(jsonString!!)
        )
    }

    private fun failedDispatcher() {
        server.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody("Error")
        )
    }

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
                        val filterType = remember { mutableStateOf("") }
                        val backgroundType = remember { mutableStateOf(Constants.NO_BACKGROUND) }
                        viewModel = hiltViewModel()
                        viewModel.getData("Mars")

                        LibraryScreenContent(
                            viewModel = viewModel,
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
    fun test_library_title() = runBlocking {
        Log.d("JSON Media Library data:", jsonString!!)
//        successfulDispatcher()
        composeTestRule.waitForIdle()
        composeTestRule.waitUntil(8000) {

            composeTestRule.onAllNodes(hasTestTag("List Card"), true)
                .fetchSemanticsNodes().size == 1
        }
    }

    @Test
    fun failedResponse() = runBlocking {
        failedDispatcher()
        delay(3000)
    }
}