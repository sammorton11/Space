package com.samm.space.tests.mock_server_tests

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.hilt.navigation.compose.hiltViewModel
import com.samm.space.MainActivity
import com.samm.space.picture_of_the_day.presentation.ApodContent
import com.samm.space.picture_of_the_day.presentation.ApodViewModel
import com.samm.space.tests.mock_web_server_tests.util.ApodTestTags.apodCopyrightText
import com.samm.space.tests.mock_web_server_tests.util.ApodTestTags.apodDateText
import com.samm.space.tests.mock_web_server_tests.util.ApodTestTags.apodDescriptionTag
import com.samm.space.tests.mock_web_server_tests.util.ApodTestTags.apodImageTag
import com.samm.space.tests.mock_web_server_tests.util.GlobalTestTags.downloadButtonTag
import com.samm.space.tests.mock_web_server_tests.util.GlobalTestTags.errorTag
import com.samm.space.tests.mock_web_server_tests.util.GlobalTestTags.shareButtonTag
import com.samm.space.ui.theme.SpaceTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ApodMockServerTest: HttpTestBase() {


    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val jsonString = this.javaClass.classLoader?.getResource("res/raw/apod_response.json")?.readText()

    @Before
    fun setUp() {
        hiltRule.inject()
        prepare()
        mockServer.setup(
            "GET",
            "planetary/apod",
            200,
            jsonString!!
        )
        composeTestRule.activity.apply {
            setContent {
                SpaceTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        var viewModel: ApodViewModel = hiltViewModel()
                        ApodContent(viewModel = viewModel)
                    }
                }
            }
        }
    }


    @Test
    fun test_apod_title() {

        composeTestRule.waitForIdle()
//        composeTestRule.onNodeWithTag(apodTitleTag, true).assertIsDisplayed()
        Thread.sleep(1000)
    }

    @Test
    fun test_apod_image() {
        //successfulDispatcher()
        composeTestRule.waitUntil {
            composeTestRule.onAllNodes(hasTestTag(apodImageTag), true)
                .fetchSemanticsNodes().size == 1
        }
        composeTestRule.onNodeWithTag(apodImageTag, true)
            .assertExists()
        composeTestRule.waitForIdle()
        composeTestRule.onAllNodesWithTag(apodImageTag, true)
            .assertCountEquals(1)
            .onFirst()
            .assertIsDisplayed()
    }

    @Test
    fun test_apod_description() {

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(apodDescriptionTag, true)
            .assertIsDisplayed()
    }

    @Test
    fun test_apod_copyright() {

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(apodCopyrightText, true)
            .assertIsDisplayed()
    }

    @Test
    fun test_apod_date() {

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(apodDateText, true)
            .assertIsDisplayed()
    }

    @Test
    fun test_apod_share_button() {

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(shareButtonTag, true)
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun test_apod_download_button() {

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(downloadButtonTag, true)
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun testFailedResponse() {

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(errorTag).assertIsDisplayed()
    }
}
