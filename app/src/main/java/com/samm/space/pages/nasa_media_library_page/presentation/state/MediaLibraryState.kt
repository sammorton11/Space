package com.samm.space.pages.nasa_media_library_page.presentation.state

import com.samm.space.pages.nasa_media_library_page.domain.models.Item

data class MediaLibraryState(
    val isLoading: Boolean = false,
    val data: List<Item?> = emptyList(),
    val error: String = ""
)
