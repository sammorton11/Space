package com.samm.space.tests.mock_web_server_tests

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
import com.samm.space.util.ApodTestTags.apodCopyrightText
import com.samm.space.util.ApodTestTags.apodDateText
import com.samm.space.util.ApodTestTags.apodDescriptionTag
import com.samm.space.util.ApodTestTags.apodImageTag
import com.samm.space.util.GlobalTestTags.downloadButtonTag
import com.samm.space.util.GlobalTestTags.errorTag
import com.samm.space.util.GlobalTestTags.shareButtonTag
import com.samm.space.ui.theme.SpaceTheme
import com.samm.space.util.ApodTestTags.apodTitleTag
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*

// Todo: Tests fail when running class. server throws error.

@HiltAndroidTest
class ApodUITest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private fun successfulResponse() {
        server.enqueue(MockResponse()
            .setResponseCode(200)
            .setBody(jsonString!!)
        )
    }
    private fun failedResponse() {
        server.enqueue(MockResponse()
            .setResponseCode(404)
            .setBody("Error")
        )
    }

    companion object {
        val server = MockWebServer()
        val jsonString = this.javaClass
            .classLoader?.getResource("res/raw/apod_response.json")?.readText()
        @AfterClass
        @JvmStatic
        fun tearDownClass() {
            server.shutdown()
        }
    }

    @Before
    fun setUp() {
        hiltRule.inject()
        successfulResponse()
        composeTestRule.activity.apply {
            setContent {
                SpaceTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val viewModel: ApodViewModel = hiltViewModel()
                        ApodContent(viewModel = viewModel)
                    }
                }
            }
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
        server.close()
        Thread.sleep(1000)
    }

    @Test
    fun test_apod_title() {
        composeTestRule.waitForIdle()
        Thread.sleep(3000)
        composeTestRule.onNodeWithTag(apodTitleTag, true).assertIsDisplayed()
    }

    @Test
    fun test_apod_image() {

        composeTestRule.waitForIdle()
        Thread.sleep(500)
        composeTestRule.waitForIdle()
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
        successfulResponse()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(apodDescriptionTag, true)
            .assertIsDisplayed()
    }

    @Test
    fun test_apod_copyright() {
        successfulResponse()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(apodCopyrightText, true)
            .assertIsDisplayed()
    }

    @Test
    fun test_apod_date() {
        successfulResponse()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(apodDateText, true)
            .assertIsDisplayed()
    }

    @Test
    fun test_apod_share_button() {
        successfulResponse()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(shareButtonTag, true)
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun test_apod_download_button() {
        successfulResponse()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(downloadButtonTag, true)
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun testFailedResponse() {
        failedResponse()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(errorTag).assertIsDisplayed()
    }
}
