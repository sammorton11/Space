package com.samm.space.tests.ui_tests

import androidx.compose.ui.test.*
import com.samm.space.util.test_tags.MediaLibraryTestTags.detailsScreenTag
import com.samm.space.util.test_tags.MediaLibraryTestTags.listCardTag
import com.samm.space.util.test_tags.MediaLibraryTestTags.searchFieldTag
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
class MediaLibraryUITest: BaseTest() {

    companion object {
        val serverMediaLibrary = MockWebServer()
        val serverMetadata = MockWebServer()

        fun successfulResponse(body: String) {
            serverMediaLibrary.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(body)
            )
        }

        fun successfulMetadataResponse(body: String) {
            serverMetadata.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(body)
            )
        }

        @AfterClass
        @JvmStatic
        fun tearDownClass() {
            serverMediaLibrary.shutdown()
            serverMediaLibrary.close()
        }
    }

    @Before
    fun setUp() {
        hiltRule.inject()
        mediaLibraryScreenSetup()
    }

    @After
    fun tearDown() {
        serverMediaLibrary.shutdown()
        serverMediaLibrary.close()
    }

    @Test
    fun test_search_field() {
        successfulResponse(jsonStringMediaLibrary!!)
        composeTestRule.onNodeWithTag(searchFieldTag, true)
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performTextInput("Test")

        composeTestRule.onNodeWithTag(searchFieldTag, true)
            .performImeAction()
    }

    @Test
    fun test_library_list_cards() {
        successfulResponse(jsonStringMediaLibrary!!)
        val listOfCards = composeTestRule.onAllNodes(hasTestTag(listCardTag), true)

        composeTestRule.waitUntil {
            listOfCards.fetchSemanticsNodes().isNotEmpty()
        }

        for (index in 0 until listOfCards.fetchSemanticsNodes().size) {
            listOfCards[index]
                .assertIsDisplayed()
                .performClick()

            composeTestRule.waitForIdle()
            composeTestRule.waitUntil(3000) {
                composeTestRule.onAllNodes(hasTestTag(detailsScreenTag), true)
                    .fetchSemanticsNodes().isNotEmpty()
            }
            composeTestRule.onNodeWithTag(detailsScreenTag)
                .assertIsDisplayed()

            successfulMetadataResponse(jsonStringMetadata!!)
            composeTestRule.waitForIdle()
            pressBackButton(composeTestRule)
        }
    }

    @Test
    fun test_image_video_details_screen() {
        successfulResponse(jsonStringMediaLibrary!!)
        val listOfCards = composeTestRule.onAllNodes(hasTestTag(listCardTag), true)

        composeTestRule.waitUntil {
            listOfCards.fetchSemanticsNodes().isNotEmpty()
        }

        listOfCards[0]
            .assertIsDisplayed()
            .performClick()

        composeTestRule.waitUntil(3000) {
            composeTestRule.onAllNodes(hasTestTag(detailsScreenTag), true)
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithTag(detailsScreenTag)
            .assertIsDisplayed()

        successfulMetadataResponse(jsonStringMetadata!!)
        composeTestRule.waitForIdle()
        Thread.sleep(3000)
        pressBackButton(composeTestRule)
    }


    @Test
    fun test_null_data_for_details_screen() {
        successfulResponse(jsonStringMediaLibraryNullData!!)
        val listOfCards = composeTestRule.onAllNodes(hasTestTag(listCardTag), true)

        composeTestRule.waitUntil(2000) {
            listOfCards.fetchSemanticsNodes().isNotEmpty()
        }

        for (index in 0 until listOfCards.fetchSemanticsNodes().size) {
            listOfCards[index]
                .assertIsDisplayed()
                .performClick()

            composeTestRule.waitUntil(3000) {
                composeTestRule.onAllNodes(hasTestTag(detailsScreenTag), true)
                    .fetchSemanticsNodes().isNotEmpty()
            }

            composeTestRule.onNodeWithTag(detailsScreenTag)
                .assertIsDisplayed()

            composeTestRule.onNodeWithTag("Details Text")
                .assertIsDisplayed()
                .assertTextEquals("Not Available")

            pressBackButton(composeTestRule)
        }
    }
}