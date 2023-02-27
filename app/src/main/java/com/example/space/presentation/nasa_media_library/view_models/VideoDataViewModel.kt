package com.example.space.presentation.nasa_media_library.view_models

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.space.core.Resource
import com.example.space.domain.models.Item
import com.example.space.domain.models.Link
import com.example.space.domain.repository.Repository
import com.example.space.presentation.nasa_media_library.state.VideoDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.json.JSONArray
import javax.inject.Inject

@HiltViewModel
class VideoDataViewModel @Inject constructor (private val repository: Repository): ViewModel() {

    private val _state = mutableStateOf(VideoDataState()) // not exposed because mutable
    val state: State<VideoDataState> = _state // expose this to composable because immutable

    private fun videoDataFlow(url: String) = flow {
        emit(Resource.Loading())
        val response = repository.getVideoData(url)
        Log.d("Response Video or Audio", "${response.body()}")
        emit(Resource.Success(response))
    }.catch { throwable ->
        emit(Resource.Error(throwable.toString()))
    }

    fun getVideoData(url: String) {
        videoDataFlow(url).onEach { response ->
            val videoData = response.data?.body()
            val message = response.message
            val error = response.data?.errorBody()

            when(response) {
                is Resource.Success -> {
                    _state.value = VideoDataState(data = videoData)
                    Log.d("SUCCESS", "$videoData")
                }
                is Resource.Error -> {
                    _state.value = VideoDataState(error = error.toString())
                    Log.d("ITEM ERROR", "$error")
                }
                is Resource.Loading -> {
                    _state.value = VideoDataState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    /**
     *   When the media type is image we need to go through the list of uri's from the response
     *   to retrieve an image.
     *
     *   If it is a video or an audio type, we just need to use the .json link from the response
     *   to get the metadata for the video or audio.
     *
     *   For some reason, the API does not initially provide the data for "video" or "audio" types.
     *   It expects you to use the .json links to request the metadata.
     *
     *   "image" types can also use the .json links to retrieve its image data, but jpeg files are
     *   already provided so I'm just using those. I decided not to use them though.
     *   That way I don't have to make additional requests for the image types.
     *
     */
    fun processLinks(links: List<Link>?, mediaType: String?, url: MutableState<String>, item: Item) {
        mediaType?.let { type ->
            val linkFromResponseItem = item.href
            when (type) {
                "video" -> {
                    url.value = linkFromResponseItem ?: ""
                    return
                }
                "audio" -> {
                    url.value = linkFromResponseItem ?: ""
                    return
                }
                "image" -> {
                    url.value = getImageLink(links = links)
                }
            }
        }
    }

    /**
     *  Some Items do not contain the same type of images
     *  - to be safe this method just gets any jpeg that is available
     */
    private fun findImageLink(links: List<Link>): String {
        links.forEach { url ->
            url.href?.let { nonNullUrl ->
                if (nonNullUrl.contains(".jpg")) {
                    return url.href
                }
            }
        }
        return ""
    }
    fun getImageLink(links: List<Link>?): String {
        return links?.let { findImageLink(it) } ?: ""
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
        }
        file = file.replace("http://", "https://") // --- http:// won't work

        return file
    }

    /**
        The response from the video data api call is in the form of a json string
        so we must convert the json string to a JSON Array.

        Then add all of the items from that JSON Array to a new ArrayList and return it.
     */
    fun getUrlList(state: String): ArrayList<String> {
        Log.d("STATE for getUrlLIst", state)
        val jsonArray = JSONArray(state)
        val urls = ArrayList<String>()
        for (i in 0 until jsonArray.length()) {
            val url = jsonArray.getString(i)
            urls.add(url)
        }
        urls.forEach {
            Log.d("Urls:", it)
        }
        return urls
    }
}


