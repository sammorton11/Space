package com.samm.space.tests.ui_tests

import androidx.compose.ui.test.*
import com.samm.space.util.test_tags.ApodTestTags.apodCopyrightText
import com.samm.space.util.test_tags.ApodTestTags.apodDateText
import com.samm.space.util.test_tags.ApodTestTags.apodDescriptionTag
import com.samm.space.util.test_tags.ApodTestTags.apodImageTag
import com.samm.space.util.test_tags.ApodTestTags.apodTitleTag
import com.samm.space.util.test_tags.GlobalTestTags.downloadButtonTag
import com.samm.space.util.test_tags.GlobalTestTags.shareButtonTag
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

// Todo: Tests fail when running class. serverApod throws error.
// Todo: Need to test null and blank data from response - create fake json for test - add that to the base test

@HiltAndroidTest
class ApodUITest: BaseTest() {

    companion object {
        val serverApod = MockWebServer()
    }

    @Before
    fun setUp() {
        serverApod.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(jsonStringApod!!)
        )
        hiltRule.inject()
        apodScreenSetup()
    }
    @After
    fun tearDownClass() {
        serverApod.shutdown()
        serverApod.close()
    }

    @Test
    fun test_apod_title() {
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(apodTitleTag, true)
            .assertIsDisplayed()
    }

    @Test
    fun test_apod_image(): Unit = runBlocking {

        composeTestRule.waitUntil {
            composeTestRule.onAllNodes(hasTestTag(apodImageTag), true)
                .fetchSemanticsNodes().size == 1
        }

        delay(500) // Trying to avoid using this but all other 'waits' do not work
        composeTestRule.onNodeWithTag(apodImageTag, true)
            .assertExists()
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
            .performClick()
    }
}
