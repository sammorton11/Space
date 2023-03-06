package com.example.space.core

import java.nio.charset.StandardCharsets

object Constants {
    val utf8Encoding = StandardCharsets.UTF_8.toString()
    const val BASE_URL = "https://images-api.nasa.gov/"
    const val BASE_URL_MARS_DATA = "https://api.nasa.gov/"
    const val API_KEY = "qGqHQYIgYmjxAKCaFJHaN9I3XTvpbHQHS8N7yMNO"

    const val stockImage = "https://images-assets.nasa.gov/image/ARC-2003-AC03-0036-9/ARC-2003-AC03-0036-9~thumb.jpg"
    const val NO_BACKGROUND = 0

    const val IMAGE_JPEG_MIME_TYPE = "image/jpeg"
    const val IMAGE_JPEG_SUB_PATH = "image.jpeg"
    const val AUDIO_WAV_MIME_TYPE = "audio/x-wav"
    const val AUDIO_WAV_SUB_PATH = "audio.wav"
    const val VIDEO_MIME_TYPE = "audio/x-wav"
    const val VIDEO_SUB_PATH = "audio.wav"
}