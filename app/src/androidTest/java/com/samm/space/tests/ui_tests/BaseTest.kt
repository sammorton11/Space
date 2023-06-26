package com.samm.space.tests.ui_tests

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.testing.TestNavHostController
import androidx.test.platform.app.InstrumentationRegistry
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

    private val context = InstrumentationRegistry.getInstrumentation().context
    val navController: TestNavHostController = TestNavHostController(context)

    open fun pressBackButton() {
        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }
    }

}