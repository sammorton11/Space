package com.samm.space

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

    private lateinit var mediaDataViewModel: MediaDataViewModel
    private lateinit var mediaLibraryViewModel: MediaLibraryViewModel

    @Before
    fun setup() {
        mediaDataViewModel = mockk()
        mediaLibraryViewModel = mockk()
    }

    @Test
    fun `fileTypeCheck should return the correct file for a given media type`() {
        val array = arrayListOf("http://audio.wav", "http://video.mp4", "http://image.jpg")
        val videoMediaType = MediaType.VIDEO
        val imageMediaType = MediaType.IMAGE
        val audioMediaType = MediaType.AUDIO
        every { mediaDataViewModel.fileTypeCheck(array, videoMediaType) } returns "https://video.mp4"
        every { mediaDataViewModel.fileTypeCheck(array, imageMediaType) } returns "https://image.jpg"
        every { mediaDataViewModel.fileTypeCheck(array, audioMediaType) } returns "https://audio.wav"
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
        val uri = "https://images-assets.nasa.gov/image/S66-34126/collection.json/S66-34124 (6 June 1966) --- Astronauts Eugene A. Cernan and Thomas P. Stafford sit with their Gemini 9A spacecraft hatches open while awaiting the arrival of the recovery ship U.S.S. Wasp./image/ASTRONAUT CERNAN, EUGENE A. - RECOVERY (GT-9A)(S/C IN WATER W/HATCHES OPEN)/1966-06-06T00:00:00Z"
        val encodedUri = "https%3A%2F%2Fimages-assets.nasa.gov%2Fimage%2FS66-34126%2Fcollection.json/S66-34124%20(6%20June%201966)%20---%20Astronauts%20Eugene%20A.%20Cernan%20and%20Thomas%20P.%20Stafford%20sit%20with%20their%20Gemini%209A%20spacecraft%20hatches%20open%20while%20awaiting%20the%20arrival%20of%20the%20recovery%20ship%20U.S.S.%20Wasp./image/ASTRONAUT CERNAN, EUGENE A. - RECOVERY (GT-9A)(S/C IN WATER W/HATCHES OPEN)/1966-06-06T00:00:00Z"
        every { mediaDataViewModel.decodeText(encodedUri) } returns uri
    }
}