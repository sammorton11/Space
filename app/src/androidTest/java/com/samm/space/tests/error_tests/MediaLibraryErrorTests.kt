package com.samm.space.tests.error_tests

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import com.samm.space.tests.ui_tests.BaseTest
import com.samm.space.tests.ui_tests.MediaLibraryUITest.Companion.serverMediaLibrary
import com.samm.space.util.test_tags.GlobalTestTags.errorTag
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockResponse
import org.junit.After
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
class MediaLibraryErrorTests: BaseTest() {

    @Before
    fun setup() {
        hiltRule.inject()
        serverMediaLibrary.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody("Error")
        )
    }

    @After
    fun tearDownClass() {
        serverMediaLibrary.shutdown()
        serverMediaLibrary.close()
    }

    @Test
    fun test_failed_response_media_library() {
        mediaLibraryScreenSetup()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(errorTag, true)
            .assertExists()
            .assertIsDisplayed()
    }
}