package com.samm.space.features.nasa_media_library_page.presentation.view_models

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samm.space.core.Resource
import com.samm.space.features.favorites_page.presentation.state.LibraryFavoriteState
import com.samm.space.features.nasa_media_library_page.domain.models.Item
import com.samm.space.features.nasa_media_library_page.domain.repository.MediaLibraryRepository
import com.samm.space.features.nasa_media_library_page.presentation.state.MediaLibraryState
import com.samm.space.features.nasa_media_library_page.util.LibraryUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaLibraryViewModel @Inject constructor (
    private val mediaLibraryRepository: MediaLibraryRepository
): ViewModel() {

    private val _state = mutableStateOf(MediaLibraryState())
    val state: State<MediaLibraryState> = _state

    private val _listFilterType = MutableLiveData<String>()
    val listFilterType: LiveData<String> = _listFilterType

    private val _backgroundType = MutableLiveData<Int>()
    val backgroundType: LiveData<Int> = _backgroundType

    private val _favorites = MutableStateFlow<List<Item>>(emptyList())
    val favorites: StateFlow<List<Item>> = _favorites

    private val _favoriteState = mutableStateOf(LibraryFavoriteState())
    var favoriteState: State<LibraryFavoriteState> = _favoriteState

    private val allFavorites = mediaLibraryRepository.getAllFavorites()
    fun getFavorites() = viewModelScope.launch {
        allFavorites.collect {
            _favoriteState.value = LibraryFavoriteState(libraryFavorites = it)
        }
    }

    private fun handleEvent(event: LibraryUiEvent) {
        when (event) {
            is LibraryUiEvent.SearchLibrary -> getData(event.query)
            is LibraryUiEvent.OnCardClick -> event.navController.navigate(event.route)
            is LibraryUiEvent.ChangeBackground -> updateBackgroundType(event.id)
            is LibraryUiEvent.UpdateFilterType -> updateListFilterType(event.type)
            is LibraryUiEvent.FilterList -> setFilterListState(event.data, event.type)
            is LibraryUiEvent.AddLibraryFavorite -> insertFavorite(item = event.item)
            is LibraryUiEvent.RemoveFavorite -> removeFavorite(item = event.item)
            is LibraryUiEvent.UpdateFavorite -> updateFavorite(event.id, event.isFavorite)
            is LibraryUiEvent.ToggleFavorite -> toggleFavorite(item = event.item)
        }
    }

    fun sendEvent(event: LibraryUiEvent) {
        handleEvent(event)
    }

    private fun toggleFavorite(item: Item) {
        if (favorites.value.any { it.href == item.href }) {
            removeFavorite(item)
        } else {
            insertFavorite(item)
        }
    }

    private fun updateFavorite(id: Int, isFavorite: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        mediaLibraryRepository.updateFavorite(id, isFavorite)
    }

    private fun insertFavorite(item: Item) = viewModelScope.launch  {
        mediaLibraryRepository.insertFavorite(item = item)
    }


    fun getAllFavorites() {
        viewModelScope.launch {
            mediaLibraryRepository.getAllFavorites().collect { favorites ->
                _favorites.value = favorites
            }
        }
    }

    private fun removeFavorite(item: Item) = viewModelScope.launch(Dispatchers.IO) {
        favorites.value.forEach { favoriteItem ->
            if (favoriteItem.href == item.href) {
                mediaLibraryRepository.deleteFavorite(favoriteItem)
                return@forEach
            }
        }
    }

    private fun setFilterListState(data: List<Item?>, type: String) = viewModelScope.launch {
        _state.value = MediaLibraryState(data = filterList(data, type))
    }

    fun getData(query: String) {
        mediaLibraryRepository.searchImageVideoLibrary(query).onEach { response ->

            val success = response.data
            val error = response.message
            val itemsList = success?.collection?.items

            when(response) {
                is Resource.Success -> {
                    _state.value = MediaLibraryState(data = itemsList ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = MediaLibraryState(error = "Error: $error")
                }
                is Resource.Loading -> {
                    _state.value = MediaLibraryState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getSavedSearchText(): Flow<String> {
        return mediaLibraryRepository
            .savedQueryFlow()
            .map { query -> query ?: "Search" }
    }

    private fun updateListFilterType(filterType: String) {
        _listFilterType.value = filterType
    }

    private fun updateBackgroundType(backgroundType: Int) {
        _backgroundType.value = backgroundType
    }

    fun encodeText(text: String?): String {
        val encodedText = text?.let {
            Uri.encode(it)
        } ?: "Not Available"

        return Uri.Builder()
            .encodedPath(encodedText)
            .build()
            .toString()
    }

    // Filters out items containing the filter type value - image, video, or audio
    fun filterList(data: List<Item?>, filterType: String): List<Item?> {
        return data.filter { item ->
            val dataList = item?.data?.first()
            val mediaType = dataList?.media_type
            filterType.let { mediaType?.contains(it) } ?: false
        }
    }
}