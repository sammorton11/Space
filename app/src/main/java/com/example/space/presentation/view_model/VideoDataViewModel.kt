package com.example.space.presentation.view_model

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.space.VideoDataState
import com.example.space.core.Resource
import com.example.space.data.repository.RepositoryImpl
import com.example.space.domain.models.Item
import com.example.space.domain.models.Link
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class VideoDataViewModel @Inject constructor (private val repository: RepositoryImpl): ViewModel() {

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
            when(response) {
                is Resource.Success -> {
                    _state.value = VideoDataState(
                            data = response.data?.body()
                    )
                    Log.d("SUCCESS", "${response.data?.body()}")
                }
                is Resource.Error -> {
                    _state.value = VideoDataState(
                        error = response.data?.errorBody().toString()
                    )
                    Log.d("ITEM ERROR", "${response.message}")
                }
                is Resource.Loading -> {
                    _state.value = VideoDataState(isLoading = true)
                    Log.d("ITEM LOADING VIDEO", "true")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun processLinks(links: List<Link>?, mediaType: String?, url: MutableState<String>, item: Item) {

        mediaType?.let {
            when (it) {
                "video" -> {
                    url.value = item.href ?: ""
                    return
                }
                "audio" -> {
                    url.value = item.href ?: ""
                    return
                }
                "image" -> {
                    url.value = getImageLink(links = links)
                }
            }
        }
    }

    fun getImageLink(links: List<Link>?): String {
        return links?.let { findImageLink(it) } ?: ""
    }

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
}


