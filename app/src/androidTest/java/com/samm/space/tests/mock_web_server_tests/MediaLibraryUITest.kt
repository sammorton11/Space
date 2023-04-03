package com.samm.space.tests.mock_web_server_tests

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.samm.space.core.Constants
import com.samm.space.nasa_media_library.presentation.view_models.MediaLibraryViewModel
import com.samm.space.presentation.MainScaffold
import com.samm.space.presentation.SideNavigationDrawer
import com.samm.space.ui.theme.SpaceTheme
import com.samm.space.util.test_tags.MediaLibraryTestTags.detailsScreenTag
import com.samm.space.util.test_tags.MediaLibraryTestTags.listCardTag
import com.samm.space.util.test_tags.MediaLibraryTestTags.searchFieldTag
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.AfterClass
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
class MediaLibraryUITest: BaseTest() {

    companion object {
        val serverMediaLibraryScreen = MockWebServer()

        // contains non-null data
        val jsonString = this.javaClass
            .classLoader
            ?.getResource("res/raw/media_library_response.json")
            ?.readText()

        // contains null and empty data
        val jsonStringNullData = this.javaClass
            .classLoader
            ?.getResource("res/raw/media_library_response_blank_data.json")
            ?.readText()

        @AfterClass
        @JvmStatic
        fun tearDownClass() {
            serverMediaLibraryScreen.shutdown()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Before
    fun setUp() {
        hiltRule.inject()
        mediaLibraryScreenSetup()
    }

    @Test
    fun test_search_field() {
        composeTestRule.onNodeWithTag(searchFieldTag, true)
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performTextInput("Test")

        composeTestRule.onNodeWithTag(searchFieldTag, true)
            .performImeAction()
    }

    @Test
    fun test_library_list_card() {

        successfulResponse(jsonString!!)
        val listOfCards = composeTestRule.onAllNodes(hasTestTag(listCardTag), true)

        composeTestRule.waitUntil(2000) {
            listOfCards.fetchSemanticsNodes().isNotEmpty()
        }

        for (index in 0 until listOfCards.fetchSemanticsNodes().size) {
            listOfCards[index]
                .assertIsDisplayed()
                .performClick()

            composeTestRule.waitForIdle()
            composeTestRule.onNodeWithTag(detailsScreenTag)
                .assertIsDisplayed()

            pressBackButton()
        }

        composeTestRule.waitForIdle()

    }

    @Test
    fun test_null_data_for_details_screen() {
        successfulResponse(jsonStringNullData!!)
        val listOfCards = composeTestRule.onAllNodes(hasTestTag(listCardTag), true)

        composeTestRule.waitUntil(2000) {
            listOfCards.fetchSemanticsNodes().isNotEmpty()
        }
        for (index in 0 until listOfCards.fetchSemanticsNodes().size) {
            listOfCards[index]
                .assertIsDisplayed()
                .performClick()

            composeTestRule.waitForIdle()
            composeTestRule.onNodeWithTag(detailsScreenTag)
                .assertIsDisplayed()

            composeTestRule.onNodeWithTag("Details Text" )
                .assertIsDisplayed()

            pressBackButton()
        }

        composeTestRule.waitForIdle()
    }

    @Test
    fun test_failedResponse() {
        failedResponse()
    }
}