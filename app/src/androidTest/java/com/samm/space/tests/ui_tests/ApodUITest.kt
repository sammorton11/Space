package com.samm.space.tests.ui_tests

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.hilt.navigation.compose.hiltViewModel
import com.samm.space.pages.picture_of_the_day_page.presentation.ApodScreen
import com.samm.space.pages.picture_of_the_day_page.presentation.ApodViewModel
import com.samm.space.ui.theme.SpaceTheme
import com.samm.space.util.test_tags.ApodTestTags.apodCopyrightText
import com.samm.space.util.test_tags.ApodTestTags.apodDateText
import com.samm.space.util.test_tags.ApodTestTags.apodDescriptionTag
import com.samm.space.util.test_tags.ApodTestTags.apodImageTag
import com.samm.space.util.test_tags.ApodTestTags.apodTitleTag
import com.samm.space.util.test_tags.GlobalTestTags.DOWNLOAD_BUTTON_TAG
import com.samm.space.util.test_tags.GlobalTestTags.SHARE_BUTTON_TAG
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

// Todo: Tests fail when running class. serverApod throws error.

@HiltAndroidTest
class ApodUITest: BaseTest() {

    companion object {
        val serverApod = MockWebServer()
        val jsonStringApod = Companion::class.java.classLoader
            ?.getResource("res/raw/apod_response.json")
            ?.readText()
    }

    @Before
    fun setUp() {
        serverApod.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(jsonStringApod!!)
        )

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

        delay(1500) // Trying to avoid using this but all other 'waits' do not work
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
        composeTestRule.onNodeWithTag(SHARE_BUTTON_TAG, true)
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()
    }

    @Test
    fun test_apod_download_button() {
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(DOWNLOAD_BUTTON_TAG, true)
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()
    }
}
