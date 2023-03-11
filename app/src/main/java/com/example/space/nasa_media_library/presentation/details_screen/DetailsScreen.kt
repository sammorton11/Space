package com.example.space.nasa_media_library.presentation.details_screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.space.nasa_media_library.presentation.view_models.VideoDataViewModel

@Composable
fun DetailsScreen(
    url: String,
    description: String,
    mediaType: String,
    title: String?,
    viewModel: VideoDataViewModel = hiltViewModel()
) {
    DetailsScreenContent(
        url = url,
        description = description,
        mediaType = mediaType,
        title = title,
        viewModel = viewModel
    )
}