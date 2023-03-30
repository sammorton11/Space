package com.samm.space.nasa_media_library.presentation.details_screen

import androidx.compose.runtime.Composable
import com.samm.space.nasa_media_library.presentation.view_models.VideoDataViewModel

@Composable
fun DetailsScreen(
    url: String,
    description: String,
    mediaType: String,
    title: String?,
    date: String?,
    viewModel: VideoDataViewModel
) {
    DetailsScreenContent(
        url = url,
        description = description,
        type = mediaType,
        title = title,
        date = date,
        viewModel = viewModel
    )
}