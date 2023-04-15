package com.samm.space.picture_of_the_day_page.presentation.state

import com.samm.space.picture_of_the_day_page.domain.models.Apod

data class ApodState(
    val isLoading: Boolean = false,
    val data: Apod? = null,
    val error: String? = ""
)
