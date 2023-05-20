package com.samm.space.tests.error_tests

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.hilt.navigation.compose.hiltViewModel
import com.samm.apod.picture_of_the_day_page.presentation.ApodScreen
import com.samm.apod.picture_of_the_day_page.presentation.ApodViewModel
import com.samm.space.tests.ui_tests.ApodUITest.Companion.serverApod
import com.samm.space.tests.ui_tests.BaseTest
import com.samm.space.ui.theme.SpaceTheme
import com.samm.space.util.test_tags.GlobalTestTags.errorTag
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockResponse
import org.junit.After
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
class ApodErrorTests: BaseTest() {

    private fun serverError404() = run {
        serverApod.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody("Error")
        )
    }
    private fun serverError403() = run {
        serverApod.enqueue(
            MockResponse()
                .setResponseCode(403)
                .setBody("Error")
        )
    }

    @Before
    fun setup() {
        hiltRule.inject()
        composeTestRule.activity.apply {

            setContent {
                SpaceTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val viewModel: ApodViewModel = hiltViewModel()
                        val state = viewModel.state
                        ApodScreen(stateFlow = state, refresh = viewModel::getApodState)
                    }
                }
            }
        }
    }
    @After
    fun tearDownClass() {
        serverApod.shutdown()
        serverApod.close()
    }

    @Test
    fun test_response_error_404() {
        serverError404()
        composeTestRule.onNodeWithTag(errorTag, true)
            .assertExists()
            .assertIsDisplayed()
            .assertTextEquals("Error: HTTP 404 Client Error")
    }

    @Test
    fun test_response_error_403() {
        serverError403()
        composeTestRule.onNodeWithTag(errorTag, true)
            .assertExists()
            .assertIsDisplayed()
            .assertTextContains("Error: HTTP 403 Client Error")
    }
}