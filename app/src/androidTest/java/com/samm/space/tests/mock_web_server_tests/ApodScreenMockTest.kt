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
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.samm.space.MainActivity
import com.samm.space.core.Constants
import com.samm.space.di.AppModule
import com.samm.space.presentation.MainScaffold
import com.samm.space.presentation.SideNavigationDrawer
import com.samm.space.tests.mock_web_server_tests.MockServer.Companion.server
import com.samm.space.ui.theme.SpaceTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class ApodScreenMockTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController
    val jsonString = this.javaClass.classLoader?.getResource("success_response.json")?.readText()

    private fun successDispatcher(): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when(request.path) {
                    else -> MockResponse()
                        .setResponseCode(200)
                        .setBody(jsonString!!)
                }
            }
        }
    }

    fun errorDispatcher(): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when(request.path) {
                    else -> MockResponse().setResponseCode(404).setBody("{ \"error\": \"Not Found\"}")
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Before
    fun setup() {
        server.dispatcher = successDispatcher()
        server.start(8080)

//        // Create a mock API implementation that uses the mock server
//        val mockApi = Retrofit.Builder()
//            .baseUrl(server.url("/"))
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(ApodApi::class.java)
//
//        // Inject the FakeApodRepositoryMock instance with the mock API implementation
//        val fakeRepository = FakeApodRepositoryMock(mockApi)
//        val viewModel = ApodViewModel(fakeRepository)
//        viewModel.getApodState()

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
    fun testURLCall() {
        // Test with the url call
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("Navigation Drawer")
            .performClick()
        composeTestRule.onNodeWithTag("Apod Screen Button")
            .performClick()
        composeTestRule.waitForIdle()
        Thread.sleep(3000)
    }

    @After
    fun tearDown() {
        MockServer.server.shutdown()
    }
}