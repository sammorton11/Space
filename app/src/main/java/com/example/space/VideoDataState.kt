package com.example.space

import com.example.space.domain.models.Item

data class VideoDataState(
    val isLoading: Boolean = false,
    val data: String = "",
    val error: String = ""
)
