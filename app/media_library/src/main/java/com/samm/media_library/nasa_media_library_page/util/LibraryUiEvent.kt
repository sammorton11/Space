package com.samm.media_library.nasa_media_library_page.util

import androidx.navigation.NavController
import com.samm.core.domain.library_models.Item

sealed class LibraryUiEvent {
    data class SearchLibrary(val query: String): LibraryUiEvent()
    data class OnCardClick(val route: String, val navController: NavController): LibraryUiEvent()
    data class UpdateFilterType(val type: String): LibraryUiEvent()
    data class FilterList(val data: List<Item?>, val type: String): LibraryUiEvent()
    data class ChangeBackground(val id: Int): LibraryUiEvent()
    data class AddLibraryFavorite(val item: Item): LibraryUiEvent()
    data class RemoveFavorite(val item: Item): LibraryUiEvent()

    data class ToggleFavorite(val item: Item): LibraryUiEvent()
    data class UpdateFavorite(val id: Int, val isFavorite: Boolean): LibraryUiEvent()
}


