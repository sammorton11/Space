package com.samm.space.features.favorites_page.presentation.state

import com.samm.space.features.picture_of_the_day_page.domain.models.Apod

data class ApodFavoriteState(
    var isLoading: Boolean = false,
    var apodFavorites: List<Apod>?  = null,
    var error: String? = null
)
