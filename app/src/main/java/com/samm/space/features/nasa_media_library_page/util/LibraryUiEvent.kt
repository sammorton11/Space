package com.samm.space.features.nasa_media_library_page.util

import android.content.Context
import com.samm.space.core.FilterType
import com.samm.space.features.nasa_media_library_page.domain.models.Item

sealed class LibraryUiEvent {
    data class SearchLibrary(val query: String): LibraryUiEvent()
    data class UpdateFilterType(val type: FilterType): LibraryUiEvent()
    data class FilterList(val data: List<Item?>, val type: FilterType): LibraryUiEvent()
    data class ChangeBackground(val id: Int): LibraryUiEvent()
    data class AddLibraryFavorite(val item: Item): LibraryUiEvent()
    data class RemoveFavorite(val item: Item): LibraryUiEvent()
    data class ToggleFavorite(val item: Item): LibraryUiEvent()
    data class UpdateFavorite(val id: Int, val isFavorite: Boolean): LibraryUiEvent()
    data class DownloadFile(
        val context: Context,
        val url: String?,
        val filename: String?,
        val mimeType: String,
        val subPath: String
    ): LibraryUiEvent()
}


