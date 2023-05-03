package com.samm.space.tests.ui_tests

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.samm.space.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule

@HiltAndroidTest
open class BaseTest {

    @get:Rule(order = 0)
    val hiltRule by lazy { HiltAndroidRule(this) }
    @get:Rule(order = 1)
    val composeTestRule by lazy { createAndroidComposeRule<MainActivity>() }

    lateinit var navController: TestNavHostController

    companion object {

        val jsonStringMediaLibrary = Companion::class.java.classLoader
            ?.getResource("res/raw/media_library_response.json")
            ?.readText()

        val jsonStringMetadata = Companion::class.java.classLoader
            ?.getResource("res/raw/video_metadata_response.json")
            ?.toString()

        val jsonStringMediaLibraryNullData = Companion::class.java.classLoader
            ?.getResource("res/raw/media_library_response_blank_data.json")
            ?.readText()

        val jsonStringApod = Companion::class.java.classLoader
            ?.getResource("res/raw/apod_response.json")
            ?.readText()
    }

    fun pressBackButton(
        composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
    ) {
        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }
    }
}