package com.samm.space.pages.nasa_media_library_page.presentation.view_models

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samm.space.core.Constants
import com.samm.space.core.MediaType
import com.samm.space.core.Resource
import com.samm.space.pages.nasa_media_library_page.domain.repository.MediaLibraryRepository
import com.samm.space.pages.nasa_media_library_page.presentation.state.MediaDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.json.JSONArray
import org.json.JSONException
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class MediaDataViewModel @Inject constructor(
    private val mediaLibraryRepository: MediaLibraryRepository,
    private val application: Application
) : ViewModel() {

    private val _state = mutableStateOf(MediaDataState())
    val state: State<MediaDataState> = _state

    fun getMediaData(url: String) {

        mediaLibraryRepository.videoDataFlow(url).onEach { response ->
            val mediaData = response.data

            when (response) {
                is Resource.Success -> {
                    _state.value = MediaDataState(data = mediaData)
                }
                is Resource.Error -> {
                    _state.value = response.message?.let { MediaDataState(error = it) }!!
                }
                is Resource.Loading -> {
                    _state.value = MediaDataState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    /**
     *
     *  Below are some util functions to handle the uri's from the response.
     *  I'm not that happy with the quality of these functions and would like to improve them.
     *
     */


    fun getUri(state: String?, mediaType: MediaType): String {
        state?.let { uriStrings ->
            if (uriStrings.isNotEmpty()) {
                return try {
                    fileTypeCheck(createJsonArrayFromString(uriStrings), mediaType)
                } catch (e: Exception) {
                    e.toString()
                }
            }
        }
        return ""
    }

    /**
        The response from the video data api request returns a string
        so we must convert the string to a JSON Array.

        Then add all of the items from that JSON Array to a new ArrayList and return it.
     */
    fun createJsonArrayFromString(stringResponse: String): ArrayList<String> {

        val arrayList: ArrayList<String> = arrayListOf()

        try {
            val jsonArray = JSONArray(stringResponse)
            for (url in 0 until jsonArray.length()) {
                arrayList.add(jsonArray.getString(url))
            }
        }
        catch (e: JSONException) {
            e.toString()
        }

        return arrayList
    }

    fun fileTypeCheck(array: ArrayList<String>, mediaType: MediaType): String {

        for (i in 0 until array.size) {

            val file = array[i].replace("http://", "https://")

            when (mediaType) {
                MediaType.VIDEO -> {
                    when {
                        file.contains("mobile.mp4") -> { return file }
                        file.contains(".mp4") -> { return file }
                    }
                }
                MediaType.AUDIO -> {
                    when {
                        file.contains(".wav") -> { return file }
                        file.contains(".m4a") -> { return file }
                        file.contains(".mp3") -> { return file }
                    }
                }
                MediaType.IMAGE -> {
                    when {
                        file.contains(".jpg") -> { return file }
                        file.contains(".png") -> { return file }
                    }
                }
            }
        }

        return "File not found"
    }

    fun decodeText(text: String): String {
        var decodedText = "Decoding Failed"
        try {
            decodedText = URLDecoder.decode(text, Constants.utf8Encoding)
        } catch (e: UnsupportedEncodingException) {
            //Log.e("Decoding Failed", e.localizedMessage ?: "Unexpected Exception")
        }
        return decodedText
    }
}
