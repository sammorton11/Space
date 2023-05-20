package com.samm.favorites.favorites_page.state

import com.samm.core.domain.apod_models.Apod

data class ApodFavoriteState(
    var isLoading: Boolean = false,
    var apodFavorites: List<Apod>?  = null,
    var error: String? = null
)
