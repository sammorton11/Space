package com.samm.space.pages.favorites_page.domain.models

import com.samm.space.pages.nasa_media_library_page.domain.models.Item
import com.samm.space.pages.picture_of_the_day_page.domain.models.Apod

data class ApodFavorites(
    val apodFavorites: List<Apod>? = null
)

data class LibraryFavorites(
    val libraryFavorites: List<Item>? = null
)

