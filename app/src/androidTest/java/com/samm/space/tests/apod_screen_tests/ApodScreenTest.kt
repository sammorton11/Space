package com.samm.space.tests.apod_screen_tests

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
import com.samm.space.fakes.FakeApodRepository
import com.samm.space.picture_of_the_day.presentation.ApodViewModel
import com.samm.space.presentation.MainScaffold
import com.samm.space.presentation.SideNavigationDrawer
import com.samm.space.ui.theme.SpaceTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class ApodScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var mainActivity: MainActivity
    private lateinit var navController: TestNavHostController
    private lateinit var apodViewModel: ApodViewModel
    private lateinit var fakeRepository: FakeApodRepository


    @OptIn(ExperimentalMaterial3Api::class)
    @Before
    fun setUp() {
        mainActivity = composeTestRule.activity
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
                        /*
                            Todo:
                                - Click the side nav button
                                - Click the Picture of the Day button
                                - Wait for idle
                         */
                    }
                }
            }
        }

    }


    @Test
    fun test_content_description() {

        fakeRepository = FakeApodRepository("success")
        apodViewModel = ApodViewModel(fakeRepository)
        apodViewModel.getApodState()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("Navigation Drawer")
            .performClick()
        composeTestRule.onNodeWithTag("Apod Screen Button")
            .performClick()
        composeTestRule.waitForIdle()
        Thread.sleep(3000)
    }
}