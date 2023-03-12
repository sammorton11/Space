package com.samm.space.nasa_media_library.presentation.state

import com.samm.space.nasa_media_library.domain.models.Item

data class NasaLibraryState(
    val isLoading: Boolean = false,
    val data: List<Item?> = emptyList(),
    val error: String = ""
)
