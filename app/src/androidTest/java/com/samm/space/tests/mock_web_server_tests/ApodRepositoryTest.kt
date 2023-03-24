package com.samm.space.tests.mock_web_server_tests

import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.samm.space.MainActivity
import com.samm.space.core.Constants
import com.samm.space.presentation.MainScaffold
import com.samm.space.presentation.SideNavigationDrawer
import com.samm.space.tests.mock_web_server_tests.MockServer.Companion.server
import com.samm.space.ui.theme.SpaceTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ApodRepositoryTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController
    private val jsonString = this.javaClass.classLoader?.getResource("res/raw/apod_response.json")?.readText()

    private fun successDispatcher(): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when(request.path) {
                    else -> MockResponse()
                        .setResponseCode(200)
                        .setBody("This is a test")
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Before
    fun setUp() {
        Log.d("Json string from test", jsonString.toString())
        server.enqueue(MockResponse()
            .setResponseCode(200)
            .setBody(jsonString!!)
        )
        //server.dispatcher = successDispatcher()
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
                        val drawerState = rememberDrawerState(DrawerValue.Open)
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

    @After
    fun tearDown() {
        // Shut down the mock web server
        server.shutdown()
    }

    @Test
    fun testGetApodData() {
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("Navigation Drawer")
            .performClick()
        composeTestRule.onNodeWithTag("Apod Screen Button")
            .performClick()
        composeTestRule.waitForIdle()
        composeTestRule.waitUntil {
            composeTestRule.onAllNodes(hasTestTag("Apod Picture"), true)
                .fetchSemanticsNodes().isNotEmpty()
        }
        Thread.sleep(3000)
    }
}
