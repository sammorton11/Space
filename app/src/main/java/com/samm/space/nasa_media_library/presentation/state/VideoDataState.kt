package com.samm.space.nasa_media_library.presentation.state

data class VideoDataState(
    val isLoading: Boolean = false,
    val data: String? = "",
    val error: String = ""
)
