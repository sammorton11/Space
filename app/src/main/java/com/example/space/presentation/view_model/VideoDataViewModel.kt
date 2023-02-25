package com.example.space.presentation.view_model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.space.VideoDataState
import com.example.space.core.Resource
import com.example.space.data.repository.RepositoryImpl
import com.example.space.presentation.NasaLibraryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.internal.wait
import retrofit2.Response
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
}