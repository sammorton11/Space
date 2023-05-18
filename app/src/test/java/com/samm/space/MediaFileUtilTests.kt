package com.samm.space

import com.samm.space.common.presentation.util.FileHandler
import com.samm.space.core.MediaType
import com.samm.space.pages.nasa_media_library_page.presentation.view_models.MediaDataViewModel
import com.samm.space.pages.nasa_media_library_page.presentation.view_models.MediaLibraryViewModel
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
class MediaFileUtilTests {

    private lateinit var utils: FileHandler
    private lateinit var mediaDataViewModel: MediaDataViewModel
    private lateinit var mediaLibraryViewModel: MediaLibraryViewModel

    @Before
    fun setup() {
        utils = FileHandler()

        mediaDataViewModel = mockk()
        mediaLibraryViewModel = mockk()
    }

    @Test
    fun `fileTypeCheck should return the correct file for a given media type`() {
        val array = arrayListOf("http://audio.wav", "http://video.mp4", "http://image.jpg")
        val mediaType = MediaType.VIDEO
        every { mediaDataViewModel.fileTypeCheck(array, mediaType) } returns "https://video.mp4"
    }


    @Test
    fun `encodeText should return the encoded text`() {
        val text = "Hello world!"
        every { mediaLibraryViewModel.encodeText(text) } returns "Hello+world%21"
    }

    @Test
    fun `decodeText should return the decoded text`() {
        val encoded = "Hello%20world%21"
        every { mediaDataViewModel.decodeText(encoded) } returns "Hello world!"
    }
}