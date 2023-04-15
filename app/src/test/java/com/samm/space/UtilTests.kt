package com.samm.space

import com.samm.space.core.MediaType
import com.samm.space.nasa_media_library_page.presentation.view_models.MediaDataViewModel
import com.samm.space.nasa_media_library_page.presentation.view_models.MediaLibraryViewModel
import com.samm.space.presentation_common.util.FileHandler
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UtilTests {

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

        val array = arrayListOf("audio.wav", "video.mp4", "image.jpg")
        val mediaType = MediaType.VIDEO
        val result = mediaDataViewModel.fileTypeCheck(array, mediaType)


        assertEquals("video.mp4", result)
    }

    @Test
    fun `encodeText should return the encoded text`() {

        val text = "Hello world!"
        val result = mediaLibraryViewModel.encodeText(text)
        println(result)

        assertEquals("Hello+world%21", result)
    }

    @Test
    fun `decodeText should return the decoded text`() {

        val encoded = "Hello%20world%21"
        val result = mediaDataViewModel.decodeText(encoded)

        assertEquals("Hello world!", result)
    }
}