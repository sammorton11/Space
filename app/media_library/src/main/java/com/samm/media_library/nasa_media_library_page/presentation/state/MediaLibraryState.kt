package com.samm.media_library.nasa_media_library_page.presentation.state

import com.samm.core.domain.library_models.Item

data class MediaLibraryState(
    val isLoading: Boolean = false,
    val data: List<Item?> = emptyList(),
    val error: String = ""
)
