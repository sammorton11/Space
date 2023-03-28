package com.samm.space.tests.mock_web_server_tests

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.samm.space.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule

@HiltAndroidTest
open class BaseTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    fun successfulResponse(body: String, server: MockWebServer) {
        server.enqueue(
            MockResponse()
            .setResponseCode(200)
            .setBody(body)
        )
    }

    fun failedResponse(server: MockWebServer) {
        server.enqueue(
            MockResponse()
            .setResponseCode(404)
            .setBody("Error")
        )
    }

    fun pressBackButton() {
        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }
    }
}