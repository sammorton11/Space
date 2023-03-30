package com.samm.space.tests.mock_web_server_tests

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.samm.space.picture_of_the_day.presentation.ApodScreen
import com.samm.space.picture_of_the_day.presentation.ApodViewModel
import com.samm.space.ui.theme.SpaceTheme
import com.samm.space.util.test_tags.ApodTestTags.apodCopyrightText
import com.samm.space.util.test_tags.ApodTestTags.apodDateText
import com.samm.space.util.test_tags.ApodTestTags.apodDescriptionTag
import com.samm.space.util.test_tags.ApodTestTags.apodImageTag
import com.samm.space.util.test_tags.ApodTestTags.apodTitleTag
import com.samm.space.util.test_tags.GlobalTestTags.downloadButtonTag
import com.samm.space.util.test_tags.GlobalTestTags.shareButtonTag
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.Test

// Todo: Tests fail when running class. server throws error.

@HiltAndroidTest
class ApodUITest : BaseTest() {

    companion object {

        val server = MockWebServer() // fake back end server

        // Getting the fake payload from resources
        val jsonString = this.javaClass
            .classLoader
            ?.getResource("res/raw/apod_response.json")
            ?.readText()

        @AfterClass
        @JvmStatic
        fun tearDownClass() {
            server.shutdown()
        }
    }

    @Before
    fun setUp() {
        hiltRule.inject()
        successfulResponse(jsonString!!, server)
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

    @After
    fun tearDown() {
        server.shutdown()
        server.close()
    }

    @Test
    fun test_apod_title() {
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(apodTitleTag, true).assertIsDisplayed()
    }

    @Test
    fun test_apod_image() {

        composeTestRule.waitForIdle()
        composeTestRule.waitUntil(2000) {
            composeTestRule.onAllNodes(hasTestTag(apodImageTag), true)
                .fetchSemanticsNodes()
                .isNotEmpty()
        }
        Thread.sleep(1000) // Todo: Find another way to wait. Fails without this though.

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(apodImageTag, true)
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
            .performClick()
    }

    @Test
    fun test_apod_download_button() {

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(downloadButtonTag, true)
            .assertIsDisplayed()
            .assertHasClickAction()
    }

//    @Test
//    fun testFailedResponse() {
//        failedResponse(server)
//        composeTestRule.waitForIdle()
//        composeTestRule.onNodeWithTag(errorTag).assertIsDisplayed()
//    }
}
