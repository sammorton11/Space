package com.samm.media_library.nasa_media_library_page.presentation.state

import com.samm.core.domain.library_models.Item

data class LibraryFavoriteState(
    var isLoading: Boolean = false,
    var libraryFavorites: List<Item>?  = null,
    var error: String? = null
)