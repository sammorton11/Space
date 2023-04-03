package com.samm.space.tests.mock_web_server_tests

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import com.samm.space.util.test_tags.GlobalTestTags.errorTag
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
class FailedResponseTests: BaseTest() {

    @Before
    fun setup() {
        hiltRule.inject()
        failedResponse()
    }

    @Test
    fun test_failed_response_apod() {
        apodScreenSetup()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(errorTag, true)
            .assertExists()
            .assertIsDisplayed()
    }

    // Todo: Test is failing
    @Test
    fun test_failed_response_media_library() {
        mediaLibraryScreenSetup()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(errorTag, true)
            .assertExists()
            .assertIsDisplayed()
    }
}