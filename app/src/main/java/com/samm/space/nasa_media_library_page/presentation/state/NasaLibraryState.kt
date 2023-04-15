package com.samm.space.nasa_media_library_page.presentation.state

import com.samm.space.nasa_media_library_page.domain.models.Item

data class NasaLibraryState(
    val isLoading: Boolean = false,
    val data: List<Item?> = emptyList(),
    val error: String = ""
)
