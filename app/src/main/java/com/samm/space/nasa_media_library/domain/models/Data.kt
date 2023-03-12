package com.samm.space.nasa_media_library.domain.models

data class Data(
    val center: String?,
    val title: String?,
    val nasa_id: String?,
    val date_created: String?,
    val keywords: List<String?>,
    val media_type: String?,
    val description_508: String?,
    val secondary_creator: String?,
    val description: String?
)