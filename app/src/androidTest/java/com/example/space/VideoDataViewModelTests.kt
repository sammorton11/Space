package com.example.space

import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.space.di.AppModule
import com.example.space.presentation.nasa_media_library.view_models.NasaLibraryViewModel
import com.example.space.presentation.nasa_media_library.view_models.VideoDataViewModel
import com.example.space.presentation.navigation.AppNavigation
import com.example.space.ui.theme.SpaceTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class VideoDataViewModelTests {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    private lateinit var mainActivity: MainActivity
    private lateinit var libraryViewModel: NasaLibraryViewModel
    private lateinit var videoDataViewModel: VideoDataViewModel
    private val repository = FakeRepository()

    private val testMP3 = "https://www.learningcontainer.com/wp-content/uploads/2020/02/Kalimba.mp3"
    private val testMP4 = "http://images-assets.nasa.gov/video/NHQ_2019_0508_We Are NASA/NHQ_2019_0508_We Are NASA~orig.mp4"
    private val testMP4After = "https://images-assets.nasa.gov/video/NHQ_2019_0508_We Are NASA/NHQ_2019_0508_We Are NASA~orig.mp4"
    private val testJPEG = "http://images-assets.nasa.gov/video/NHQ_2019_0508_We Are NASA/NHQ_2019_0508_We Are NASA~small_5.jpg"
    private val testWAVBefore = "http://images-assets.nasa.gov/audio/367-AAA/367-AAA~orig.wav"
    private val testWAVAfter = "https://images-assets.nasa.gov/audio/367-AAA/367-AAA~orig.wav"
    private val fileTestList = arrayListOf(testMP3, testMP4, testJPEG, testWAVBefore)

    private val itemJsonLinkForVideo = "[\"http://images-assets.nasa.gov/video/GRC-2022-CM-0123/GRC-2022-CM-0123~orig.mp4\", \"http://images-assets.nasa.gov/video/GRC-2022-CM-0123/GRC-2022-CM-0123.vtt\", \"http://images-assets.nasa.gov/video/GRC-2022-CM-0123/GRC-2022-CM-0123~medium.mp4\", \"http://images-assets.nasa.gov/video/GRC-2022-CM-0123/GRC-2022-CM-0123~mobile.mp4\"]"
    private val expectedJsonArrayConversion = arrayListOf(
        "http://images-assets.nasa.gov/video/GRC-2022-CM-0123/GRC-2022-CM-0123~orig.mp4",
        "http://images-assets.nasa.gov/video/GRC-2022-CM-0123/GRC-2022-CM-0123.vtt",
        "http://images-assets.nasa.gov/video/GRC-2022-CM-0123/GRC-2022-CM-0123~medium.mp4",
        "http://images-assets.nasa.gov/video/GRC-2022-CM-0123/GRC-2022-CM-0123~mobile.mp4"
    )
    @Before
    fun setUp() {
        mainActivity = composeTestRule.activity
        hiltRule.inject()
        libraryViewModel = NasaLibraryViewModel(repository)
        libraryViewModel.getData("test")
        videoDataViewModel = VideoDataViewModel(repository)
        composeTestRule.activity.apply {
            setContent {
                SpaceTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        AppNavigation()
                    }
                }
            }
        }
    }

    @Test
    fun fileTypeCheckTestAudio() {
        val result = videoDataViewModel.fileTypeCheck(fileTestList, "audio")
        assert(result == testWAVAfter)
    }

    @Test
    fun fileTypeCheckTestVideo() {
        val result = videoDataViewModel.fileTypeCheck(fileTestList, "video")
        assert(result == testMP4After)
    }

    @Test
    fun testJsonArrayToArrayList() {
        val result = videoDataViewModel.getUrlList(itemJsonLinkForVideo)
        result.forEach {
            Log.d("itemJsonLinkForVideo", it)
        }
        assert(result == expectedJsonArrayConversion)
    }
}