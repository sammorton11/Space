package com.samm.space.pages.favorites_page.presentation.state

import com.samm.space.pages.picture_of_the_day_page.domain.models.Apod

data class ApodFavoriteState(
var isLoading: Boolean = false,
var apodFavorites: List<Apod>?  = null,
var error: String? = null
)
