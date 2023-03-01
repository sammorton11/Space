package com.example.space.nasa_media_library.presentation.state

import com.example.space.nasa_media_library.domain.models.nasa_media_library_models.Item

data class NasaLibraryState(
    val isLoading: Boolean = false,
    val data: List<Item?> = emptyList(),
    val error: String = ""
)
