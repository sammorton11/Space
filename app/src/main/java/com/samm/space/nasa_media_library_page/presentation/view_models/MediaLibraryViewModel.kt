package com.samm.space.nasa_media_library_page.presentation.view_models

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samm.space.core.Constants
import com.samm.space.core.DataStoreManager
import com.samm.space.core.Resource
import com.samm.space.nasa_media_library_page.domain.repository.MediaLibraryRepository
import com.samm.space.nasa_media_library_page.presentation.state.NasaLibraryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.net.URLEncoder
import javax.inject.Inject

@HiltViewModel
class MediaLibraryViewModel @Inject constructor
    (private val mediaLibraryRepository: MediaLibraryRepository): ViewModel() {

    private val _state = mutableStateOf(NasaLibraryState())
    val state: State<NasaLibraryState> = _state

    private val _listFilterType = MutableLiveData<String>()
    val listFilterType: LiveData<String> = _listFilterType

    private val _backgroundType = MutableLiveData<Int>()
    val backgroundType: LiveData<Int> = _backgroundType



    fun getData(query: String) {

        mediaLibraryRepository.searchImageVideoLibrary(query).onEach { response ->
            DataStoreManager.saveLastSearchText(query) // save this search query

            val success = response.data
            val error = response.message
            val itemsList = success?.collection?.items

            when(response) {
                is Resource.Success -> {
                    _state.value = NasaLibraryState(data = itemsList ?: emptyList())
                }
                is Resource.Error -> {
                    error.let {
                        _state.value = NasaLibraryState(error = "Error! $it")
                        Log.d("Error", it.toString())
                    }
                }
                is Resource.Loading -> {
                    _state.value = NasaLibraryState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getSavedSearchText(): Flow<String> {
        return mediaLibraryRepository.savedQueryFlow()
            .map { query ->
                query?.let { savedQuery ->
                    "${savedQuery}..."
                } ?: "Search..."
            }
    }

    fun updateListFilterType(filterType: String) {
        _listFilterType.value = filterType
    }

    fun updateBackgroundType(backgroundType: Int) {
        _backgroundType.value = backgroundType
    }

    // Todo: Put this in Media Library View Model
    fun encodeText(text: String?): String {
        var encode = URLEncoder.encode(text ?: "", Constants.utf8Encoding)
        when {
            text.isNullOrBlank() -> {
                encode = URLEncoder.encode("Not Available", Constants.utf8Encoding)
            }
        }

        return encode
    }
}