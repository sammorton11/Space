package com.example.space.nasa_media_library.domain.models.nasa_media_library_models

data class Metadata(
    var links: VideoLinks
)

data class VideoLinks(
    val srt: String,
    val mobile: String,
    val mobileThumb: String,
    val orig: String,
    val preview: String,
    val previewThumb: String
)
