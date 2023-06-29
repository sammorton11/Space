package com.samm.space.features.nasa_media_library_page.presentation.state

data class DetailsScreenState(
    val isLoading: Boolean = false,
    val data: String? = null,
    val error: String = "",
    val uriStringList: String? = null,
    val metaDataUrl: String? = null,
    val description: String? = null,
    val type: String? = null,
    val title: String? = null,
    val date: String? = null
)

