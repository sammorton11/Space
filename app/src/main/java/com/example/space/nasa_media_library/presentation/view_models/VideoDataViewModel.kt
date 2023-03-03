package com.example.space.nasa_media_library.presentation.view_models

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.space.core.Resource
import com.example.space.nasa_media_library.domain.models.nasa_media_library_models.Item
import com.example.space.nasa_media_library.domain.models.nasa_media_library_models.Link
import com.example.space.nasa_media_library.domain.repository.MediaLibraryRepository
import com.example.space.nasa_media_library.presentation.state.VideoDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.json.JSONArray
import org.json.JSONException
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
    fun processLinks(links: List<Link>?, mediaType: String?, item: Item): String? {
        mediaType?.let { type ->
            when (type) {
                "video" -> {
//                    Log.d("Item Link:", item.href.toString())
//                    url.value = item.href ?: ""
                    return item.href
                }
                "audio" -> {
//                    Log.d("Item Link:", item.href.toString())
//                    url.value =  ?: ""
                    return item.href
                }
                "image" -> {
                   // Log.d("Item Link:", item.href.toString())
                    //url.value = getImageLink(links = links)
                    return getImageLink(links = links)
                }
                else -> {}
            }
        }
        return ""
    }

    /**
     *  Some Items do not contain the same type of images
     *  - to be safe this method just gets any jpeg that is available
     */
    private fun findImageLink(links: List<Link>): String {
        links.forEach { url ->
            url.href?.let { nonNullUrl ->
                if (nonNullUrl.contains(".jpg") || nonNullUrl.contains(".png")) {
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
        The response from the video data api call is in the form of a json string
        so we must convert the json string to a JSON Array.

        Then add all of the items from that JSON Array to a new ArrayList and return it.
     */
    fun getUrlList(state: String): ArrayList<String> {
        Log.d("Can't convert to JSON Array", state.toString())
        var urls: ArrayList<String> = arrayListOf()
        try {
            val jsonArray = JSONArray(state)
            for (i in 0 until jsonArray.length()) {
                urls.add(jsonArray.getString(i))
            }
        }
        catch (e: JSONException) {
            Log.d("Can't convert to JSON Array", e.toString())
        }
        return urls
    }
}


