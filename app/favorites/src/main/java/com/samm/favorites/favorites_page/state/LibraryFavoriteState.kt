package com.samm.favorites.favorites_page.state

import com.samm.core.domain.library_models.Item

data class LibraryFavoriteState(
    var isLoading: Boolean = false,
    var libraryFavorites: List<Item>?  = null,
    var error: String? = null
)