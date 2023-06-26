package com.samm.space.features.favorites_page.presentation.state

import com.samm.space.features.nasa_media_library_page.domain.models.Item

data class LibraryFavoriteState(
    var isLoading: Boolean = false,
    var libraryFavorites: List<Item>?  = null,
    var error: String? = null
)