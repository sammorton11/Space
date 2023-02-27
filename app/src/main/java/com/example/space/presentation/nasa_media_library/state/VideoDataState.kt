package com.example.space.presentation.nasa_media_library.state

data class VideoDataState(
    val isLoading: Boolean = false,
    val data: String? = "",
    val error: String = ""
)
