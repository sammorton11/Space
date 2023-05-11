package com.samm.space.pages.favorites_page.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samm.space.pages.favorites_page.domain.FavoritesRepository
import com.samm.space.pages.favorites_page.presentation.state.LibraryFavoriteState
import com.samm.space.pages.nasa_media_library_page.domain.models.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repository: FavoritesRepository): ViewModel() {

    private val _state = mutableStateOf(LibraryFavoriteState())
    var state: State<LibraryFavoriteState> = _state

//    var list = mutableListOf<Any>()

    private val favorites = repository.getAllLibraryFavorites()

    fun getFavorites() = viewModelScope.launch {
        favorites.collect {
            _state.value = LibraryFavoriteState(libraryFavorites = it)
        }
    }

//    fun getJoinedList(): List<Any> {
//        state.value.libraryFavorites?.forEach {
//            list.add(it)
//        }
//        state.value.apodFavorites?.forEach {
//            list.add(it)
//        }
//
//        return list.toList()
//    }

    fun insertLibraryFavorite(item: Item) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertFavorite(item)
    }

    fun deleteLibraryFavorite(item: Item) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteFavorite(item)
    }

}