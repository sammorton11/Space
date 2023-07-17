package com.samm.space.features.nasa_media_library_page.presentation.view_models

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samm.space.core.FilterType
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

    private val _state = MutableStateFlow(MediaLibraryState())
    val state: StateFlow<MediaLibraryState> = _state

    private val _listFilterType = MutableLiveData<FilterType>()
    val listFilterType: LiveData<FilterType> = _listFilterType

    private val _backgroundType = MutableLiveData<Int>()
    val backgroundType: LiveData<Int> = _backgroundType

    private val _favoriteState = MutableStateFlow(LibraryFavoriteState())
    var favoriteState: StateFlow<LibraryFavoriteState> = _favoriteState


    private fun fetchLibraryData(query: String) {
        mediaLibraryRepository.searchImageVideoLibrary(query).onEach { response ->

            val itemsList = response.data?.collection?.items

            when(response) {
                is Resource.Success -> {
                    _state.value = MediaLibraryState(data = itemsList ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = MediaLibraryState(error = "Error: ${response.message}")
                }
                is Resource.Loading -> {
                    _state.value = MediaLibraryState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    // Event handling
    private fun handleEvent(event: LibraryUiEvent) {
        when (event) {
            is LibraryUiEvent.SearchLibrary -> fetchLibraryData(event.query)
            is LibraryUiEvent.ChangeBackground -> updateBackgroundType(event.id)
            is LibraryUiEvent.UpdateFilterType -> updateListFilterType(event.type)
            is LibraryUiEvent.FilterList -> setFilterListState(event.data, event.type)
            is LibraryUiEvent.AddLibraryFavorite -> insertFavorite(item = event.item)
            is LibraryUiEvent.RemoveFavorite -> removeFavorite(item = event.item)
            is LibraryUiEvent.ToggleFavorite -> toggleFavorite(item = event.item)
            is LibraryUiEvent.DownloadFile -> downloadFile(
                context = event.context,
                url = event.url,
                subPath = event.subPath,
                fileName = event.filename,
                mimeType = event.mimeType
            )
        }
    }
    fun sendEvent(event: LibraryUiEvent) {
        handleEvent(event)
    }

    // Handle favorites logic
    fun getAllFavorites() = viewModelScope.launch {
        val favorites = mediaLibraryRepository.getAllFavorites()

        favorites.collect { listOfFavorites ->
            _favoriteState.value.libraryFavorites = listOfFavorites
        }
    }
    private fun toggleFavorite(item: Item) {
        if (favoriteState.value.libraryFavorites?.any { it.href == item.href } == true) {
            removeFavorite(item)
        } else {
            insertFavorite(item)
        }
    }
    private fun insertFavorite(item: Item) = viewModelScope.launch  {
        mediaLibraryRepository.insertFavorite(item = item)
    }
    private fun removeFavorite(item: Item) = viewModelScope.launch(Dispatchers.IO) {
        favoriteState.value.libraryFavorites?.forEach { favoriteItem ->
            if (favoriteItem.href == item.href) {
                mediaLibraryRepository.deleteFavorite(favoriteItem)
                return@forEach
            }
        }
    }

    // Options Menu logic
    private fun setFilterListState(data: List<Item?>, type: FilterType) = viewModelScope.launch {
        _state.value = MediaLibraryState(data = filterList(data, type))
    }
    private fun updateListFilterType(filterType: FilterType) {
        _listFilterType.value = filterType
    }
    private fun updateBackgroundType(backgroundType: Int) {
        _backgroundType.value = backgroundType
    }


    private fun downloadFile(
        context: Context,
        url: String?,
        fileName: String?,
        mimeType: String?,
        subPath: String?,
    ) {
        val downloadManager = context.getSystemService(DownloadManager::class.java)
        val request = DownloadManager.Request(url?.toUri())
            .setTitle(fileName)
            .setMimeType(mimeType)
            .setDescription("Downloading...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, subPath)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)
        downloadManager.enqueue(request)
    }

    fun getSavedSearchText(): Flow<String> {
        return mediaLibraryRepository
            .savedQueryFlow()
            .map { query -> query ?: "Search" }
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
    fun filterList(data: List<Item?>, filterType: FilterType): List<Item?> {
        return data.filter { item ->
            val dataList = item?.data?.first()
            val mediaType = dataList?.media_type
            filterType.value.let { mediaType?.contains(it) } ?: false
        }
    }
}