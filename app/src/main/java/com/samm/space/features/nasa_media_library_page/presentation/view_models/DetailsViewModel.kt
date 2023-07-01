package com.samm.space.features.nasa_media_library_page.presentation.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samm.space.core.Constants
import com.samm.space.core.MediaType
import com.samm.space.core.Resource
import com.samm.space.features.nasa_media_library_page.domain.repository.MediaLibraryRepository
import com.samm.space.features.nasa_media_library_page.presentation.state.DetailsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.json.JSONArray
import org.json.JSONException
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val mediaLibraryRepository: MediaLibraryRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsScreenState())
    val state: StateFlow<DetailsScreenState> = _state

    fun getMediaData(url: String) {
        mediaLibraryRepository.mediaDataFlow(url).onEach { response ->

            when (response) {
                is Resource.Success -> {
                    _state.value = DetailsScreenState(data = response.data)
                }
                is Resource.Error -> {
                    _state.value = response.message?.let { DetailsScreenState(error = it) }!!
                }
                is Resource.Loading -> {
                    _state.value = DetailsScreenState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

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
            Log.e("Error converting String response to a JSON Array", e.toString())
        }
        return arrayList
    }

    fun fileTypeCheck(array: ArrayList<String>, mediaType: MediaType): String {
        Log.d("fileTypeCheck", array.toString())
        Log.d("fileTypeCheck - type", mediaType.type)
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
                        file.contains(".wav") -> {
                            Log.d("audio file", file)
                            return file
                        }
                        file.contains(".m4a") -> {
                            Log.d("audio file", file)
                            return file
                        }
                        // Using replace() because a letter 'k' was being added to the end of some audio files.
                        file.contains(".mp3") -> {
                            Log.d("audio file", file)
                            val editedFile = file.replace(".mp3k", ".mp3")
                            Log.d("audio file - editedFile", editedFile)
                            return editedFile
                        }
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
            decodedText = URLDecoder.decode(text.replace("`", "/"), Constants.utf8Encoding)
        } catch (e: UnsupportedEncodingException) {
            Log.e("Decoding Failed", e.localizedMessage ?: "Unexpected Exception")
        }
        return decodedText
    }
}
