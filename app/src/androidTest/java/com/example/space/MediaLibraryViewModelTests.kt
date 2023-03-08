package com.example.space

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.rememberNavController
import com.example.space.core.Constants
import com.example.space.di.AppModule
import com.example.space.fakes.FakeMediaLibraryRepository
import com.example.space.nasa_media_library.presentation.library_search_screen.LibrarySearchScreen
import com.example.space.nasa_media_library.presentation.view_models.MediaLibraryViewModel
import com.example.space.nasa_media_library.presentation.view_models.VideoDataViewModel
import com.example.space.ui.theme.SpaceTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class MediaLibraryViewModelTests {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var mainActivity: MainActivity
    private lateinit var libraryViewModel: MediaLibraryViewModel
    private lateinit var videoDataViewModel: VideoDataViewModel
    private val repository = FakeMediaLibraryRepository()

    private val testMP3 = "https://www.learningcontainer.com/wp-content/uploads/2020/02/Kalimba.mp3"
    private val testMP4 = "http://images-assets.nasa.gov/video/NHQ_2019_0508_We Are NASA/NHQ_2019_0508_We Are NASA~orig.mp4"
    private val testMP4After = "https://images-assets.nasa.gov/video/NHQ_2019_0508_We Are NASA/NHQ_2019_0508_We Are NASA~orig.mp4"
    private val testJPEG = "http://images-assets.nasa.gov/video/NHQ_2019_0508_We Are NASA/NHQ_2019_0508_We Are NASA~small_5.jpg"
    private val testJPEGAfter = "https://images-assets.nasa.gov/video/NHQ_2019_0508_We Are NASA/NHQ_2019_0508_We Are NASA~small_5.jpg"
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
        libraryViewModel = MediaLibraryViewModel(repository)
        videoDataViewModel = VideoDataViewModel(repository)

        composeTestRule.activity.apply {
            setContent {
                SpaceTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val navController = rememberNavController()
                        val filterType = remember { mutableStateOf("") }
                        val backgroundType = remember { mutableStateOf(Constants.NO_BACKGROUND) }
                        //AppNavigation(filterType, backgroundType, navController)
                        LibrarySearchScreen(
                            viewModel = libraryViewModel,
                            navController = navController,
                            filterType = filterType,
                            backgroundType = backgroundType
                        )
                    }
                }
            }
        }
    }

    @Test
    fun test_getData_success() {
        libraryViewModel.getData("success")
        composeTestRule.waitForIdle()
        val itemsList = libraryViewModel.state.value.data
        val dataList = itemsList.first()?.data
        val fakeDataModel = FakeMediaLibraryRepository.fakeDataObject
        dataList?.first()?.equals(fakeDataModel)?.let { assert(it) }
    }

    @Test
    fun test_getData_error() {
        libraryViewModel.getData("error")
        composeTestRule.waitForIdle()
        val error = libraryViewModel.state.value.error
        assert(error.isNotEmpty())
    }

    @Test
    fun test_getData_empty() {
        libraryViewModel.getData("empty")
        composeTestRule.waitForIdle()
        val itemsList = libraryViewModel.state.value.data
        assert(itemsList.isEmpty())
    }

    /**
     *  The json response from the media request returns a json list in a string format.
     *  This tests that the return value is an array list.
     */
    @Test
    fun test_JsonArrayToArrayList() {
        val result = videoDataViewModel.extractUrlsFromJsonArray(itemJsonLinkForVideo)
        assert(result == expectedJsonArrayConversion)
    }

    @Test
    fun test_items_are_not_null() {
        libraryViewModel.getData("success")
        composeTestRule.waitForIdle()
        val list = libraryViewModel.state.value.data
        list.forEach { item -> assert(item != null) }
    }

    @Test
    fun test_fileTypeCheckTestImage() {
        val result = videoDataViewModel.fileTypeCheck(fileTestList, "image")
        assert(result == testJPEGAfter)
    }

    @Test
    fun test_fileTypeCheckTestAudio() {
        val result = videoDataViewModel.fileTypeCheck(fileTestList, "audio")
        assert(result == testWAVAfter)
    }

    @Test
    fun test_fileTypeCheckTestVideo() {
        val result = videoDataViewModel.fileTypeCheck(fileTestList, "video")
        assert(result == testMP4After)
    }
}