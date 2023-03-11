package com.example.space.core

import java.nio.charset.StandardCharsets

object Constants {
    val utf8Encoding = StandardCharsets.UTF_8.toString()
    const val BASE_URL = "https://images-api.nasa.gov/"
    const val BASE_URL_MARS_DATA = "https://api.nasa.gov/"
    const val API_KEY = "qGqHQYIgYmjxAKCaFJHaN9I3XTvpbHQHS8N7yMNO"
    const val NO_BACKGROUND = 0


    // Todo: These could be moved to an enum
    const val IMAGE_JPEG_MIME_TYPE = "image/jpeg"
}