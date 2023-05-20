package com.samm.apod.picture_of_the_day_page.presentation.state

import com.samm.core.domain.apod_models.Apod

data class ApodState(
    val isLoading: Boolean = false,
    val data: Apod? = null,
    val error: String? = ""
)
