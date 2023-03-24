package com.samm.space.tests.apod_screen_tests

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.samm.space.MainActivity
import com.samm.space.di.AppModule
import com.samm.space.fakes.FakeApodRepository
import com.samm.space.picture_of_the_day.domain.repository.ApodRepository
import com.samm.space.picture_of_the_day.presentation.ApodViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class ApodViewModelTests {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var fakeRepository: ApodRepository
    private lateinit var apodViewModel: ApodViewModel

    @Test
    fun test_response_is_success(){
        fakeRepository = FakeApodRepository("success")
        apodViewModel = ApodViewModel(fakeRepository)
        apodViewModel.getApodState()
        composeTestRule.waitForIdle()
        apodViewModel.state.value.data.let {
            assert(it != null)
        }
    }


    @Test
    fun test_response_is_error(){
        fakeRepository = FakeApodRepository("error")
        apodViewModel = ApodViewModel(fakeRepository)
        apodViewModel.getApodState()
        composeTestRule.waitForIdle()
        val error = apodViewModel.state.value.data
        composeTestRule.waitForIdle()
        assert(error == null) // using null to simulate error for now
    }

    @Test
    fun test_response_is_empty(){
        fakeRepository = FakeApodRepository("empty")
        apodViewModel = ApodViewModel(fakeRepository)
        apodViewModel.getApodState()
        composeTestRule.waitForIdle()
        apodViewModel.state.value.data.let {
            if (it != null) {
                assert(it.url.isEmpty())
            }
        }
    }
}