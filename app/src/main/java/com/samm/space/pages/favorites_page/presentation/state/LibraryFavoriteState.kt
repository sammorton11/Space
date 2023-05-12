package com.samm.space.pages.favorites_page.presentation.state

import com.samm.space.pages.nasa_media_library_page.domain.models.Item

data class LibraryFavoriteState(
    var isLoading: Boolean = false,
    var libraryFavorites: List<Item>?  = null,
    var error: String? = null
)