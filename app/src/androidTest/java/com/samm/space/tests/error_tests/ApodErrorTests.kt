package com.samm.space.tests.error_tests

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import com.samm.space.tests.ui_tests.ApodUITest.Companion.serverApod
import com.samm.space.tests.ui_tests.BaseTest
import com.samm.space.util.test_tags.GlobalTestTags.errorTag
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockResponse
import org.junit.After
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
class ApodErrorTests: BaseTest() {

    @Before
    fun setup() {
        hiltRule.inject()
        serverApod.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody("Error")
        )
    }
    @After
    fun tearDownClass() {
        serverApod.shutdown()
        serverApod.close()
    }

    @Test
    fun test_failed_response_apod() {
        apodScreenSetup()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(errorTag, true)
            .assertExists()
            .assertIsDisplayed()
    }
}