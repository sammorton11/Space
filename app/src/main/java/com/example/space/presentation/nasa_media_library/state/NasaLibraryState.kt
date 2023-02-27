package com.example.space.presentation.nasa_media_library.state

import com.example.space.domain.models.Item

data class NasaLibraryState(
    val isLoading: Boolean = false,
    val data: List<Item?> = emptyList(),
    val error: String = ""
)
