package com.samm.space.pages.favorites_page.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samm.space.pages.favorites_page.domain.FavoritesRepository
import com.samm.space.pages.favorites_page.presentation.state.LibraryFavoriteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(repository: FavoritesRepository): ViewModel() {

    private val _state = mutableStateOf(LibraryFavoriteState())
    var state: State<LibraryFavoriteState> = _state

    private val favorites = repository.getAllLibraryFavorites()

    fun getFavorites() = viewModelScope.launch {
        favorites.collect {
            _state.value = LibraryFavoriteState(libraryFavorites = it)
        }
    }
}