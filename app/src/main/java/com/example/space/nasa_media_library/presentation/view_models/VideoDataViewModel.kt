package com.example.space.nasa_media_library.presentation.view_models

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.space.core.Constants.utf8Encoding
import com.example.space.core.Resource
import com.example.space.nasa_media_library.domain.repository.MediaLibraryRepository
import com.example.space.nasa_media_library.presentation.state.VideoDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.json.JSONArray
import org.json.JSONException
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class VideoDataViewModel @Inject constructor (private val mediaLibraryRepository: MediaLibraryRepository): ViewModel() {

    private val _state = mutableStateOf(VideoDataState())
    val state: State<VideoDataState> = _state

    private fun videoDataFlow(url: String) = flow {
        emit(Resource.Loading())
        val response = mediaLibraryRepository.getVideoData(url)
        emit(Resource.Success(response))
    }.catch { throwable ->
        emit(Resource.Error(throwable.toString()))
    }

    fun getVideoData(url: String) {
        videoDataFlow(url).onEach { response ->
            val videoData = response.data?.body()
            val error = response.data?.errorBody()

            when(response) {
                is Resource.Success -> {
                    _state.value = VideoDataState(data = videoData)
                }
                is Resource.Error -> {
                    _state.value = VideoDataState(error = error.toString())
                }
                is Resource.Loading -> {
                    _state.value = VideoDataState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    /**
     *      This will grab any video or audio file that is available from the casted JSON Array
     *      The media type is already checked whether it is video or audio before this is called
     */
    fun fileTypeCheck(array: ArrayList<String>, mediaType: String): String {
        var file = ""
        for (i in 0 until array.size) {
            if (mediaType == "video") {
                when {
                    array[i].contains("mobile.mp4") -> { file = array[i] }
                    array[i].contains(".mp4") -> { file = array[i] }
                }
            }
            if (mediaType == "audio") {
                when {
                    array[i].contains(".wav") -> { file = array[i] }
                    array[i].contains(".m4a") -> { file = array[i] }
                    array[i].contains(".mp3") -> { file = array[i] }
                }
            }
            if (mediaType == "image") {
                when {
                    array[i].contains(".jpg") -> { file = array[i] }
                    array[i].contains(".png") -> { file = array[i] }
                }
            }
        }
        file = file.replace("http://", "https://") // --- http:// won't work

        return file
    }

    /**
        The response from the video data api call is in string format
        so we must convert the string to a JSON Array.

        Then add all of the items from that JSON Array to a new ArrayList and return it.
     */
    fun extractUrlsFromJsonArray(stringResponse: String): ArrayList<String> {
        val arrayList: ArrayList<String> = arrayListOf()

        try {
            val jsonArray = JSONArray(stringResponse)
            for (url in 0 until jsonArray.length()) {
                arrayList.add(jsonArray.getString(url))
            }
        }
        catch (e: JSONException) {
            Log.d("Can't convert to JSON Array", e.toString())
        }

        return arrayList
    }

    fun decodeText(text: String): String {
        var decodedText = "Decoding Failed"
        try {
            decodedText = URLDecoder.decode(text, utf8Encoding)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return decodedText
    }
}


