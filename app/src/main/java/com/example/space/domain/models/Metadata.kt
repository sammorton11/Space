package com.example.space.domain.models

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
