package com.samm.space.picture_of_the_day.presentation

import com.samm.space.picture_of_the_day.domain.models.Apod

data class ApodState(
    val isLoading: Boolean = false,
    val data: Apod? = null,
    val error: String? = ""
)
