package com.samm.space.tests.mock_web_server_tests

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.samm.space.MainActivity
import com.samm.space.core.Constants
import com.samm.space.nasa_media_library.presentation.view_models.MediaLibraryViewModel
import com.samm.space.picture_of_the_day.presentation.ApodScreen
import com.samm.space.picture_of_the_day.presentation.ApodViewModel
import com.samm.space.presentation.MainScaffold
import com.samm.space.presentation.SideNavigationDrawer
import com.samm.space.ui.theme.SpaceTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import java.util.*
import kotlin.concurrent.schedule

@HiltAndroidTest
open class BaseTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController

    companion object {
        val server = MockWebServer()
    }


    fun successfulResponse(body: String) {
        server.enqueue(
            MockResponse()
            .setResponseCode(200)
            .setBody(body)
        )
    }

    fun failedResponse() {
        server.enqueue(
            MockResponse()
            .setResponseCode(404)
            .setBody("Error")
        )
    }

    fun pressBackButton() {
        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }
    }

    fun asyncTimerWait (delay: Long) {
        AsyncTimer.start (delay)
        composeTestRule.waitUntil (
            condition = {AsyncTimer.expired},
            timeoutMillis = delay + 1000
        )
    }

    object AsyncTimer {
        var expired = false
        fun start(delay: Long){
            expired = false
            Timer().schedule(delay) {
                expired = true
            }
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
                        val filterType = remember { mutableStateOf("") }
                        val backgroundType = remember { mutableStateOf(Constants.NO_BACKGROUND) }

                        val scope = rememberCoroutineScope()
                        val title = remember { mutableStateOf("NASA Media Library") }

                        val viewModel: MediaLibraryViewModel = hiltViewModel()
                        viewModel.getData("Mars")

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
}