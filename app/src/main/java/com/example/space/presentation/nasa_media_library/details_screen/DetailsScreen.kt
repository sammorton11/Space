package com.example.space.presentation.nasa_media_library.details_screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.space.presentation.navigation.CardData
import com.example.space.presentation.view_model.VideoDataViewModel

@Composable
fun DetailsScreen(
    url: String,
    description: String,
    viewModel: VideoDataViewModel = hiltViewModel()
) {
    DetailsScreenContent(url = url, description = description, viewModel = viewModel)
}