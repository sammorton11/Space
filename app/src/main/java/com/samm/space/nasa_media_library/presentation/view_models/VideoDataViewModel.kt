package com.samm.space.nasa_media_library.presentation.view_models

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samm.space.core.Resource
import com.samm.space.nasa_media_library.domain.repository.MediaLibraryRepository
import com.samm.space.nasa_media_library.presentation.state.VideoDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class VideoDataViewModel @Inject constructor (private val mediaLibraryRepository: MediaLibraryRepository): ViewModel() {

    private val _state = mutableStateOf(VideoDataState())
    val state: State<VideoDataState> = _state

    fun getVideoData(url: String, context: Context) {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
            mediaLibraryRepository.videoDataFlow(url).onEach { response ->
                val videoData = response.data?.body()
                val error = response.data?.errorBody()

                when (response) {
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
        } else {
            _state.value = VideoDataState(error = "Internet Connection Failure")
        }
    }
}


