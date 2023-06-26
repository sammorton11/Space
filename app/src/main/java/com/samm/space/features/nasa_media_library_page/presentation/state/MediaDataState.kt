package com.samm.space.features.nasa_media_library_page.presentation.state

data class MediaDataState(
    val isLoading: Boolean = false,
    val data: String? = "",
    val error: String = ""
)
