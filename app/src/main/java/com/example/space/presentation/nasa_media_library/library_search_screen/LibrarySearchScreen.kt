package com.example.space.presentation.nasa_media_library.library_search_screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.space.presentation.view_model.NasaLibraryViewModel
import com.example.space.presentation.view_model.VideoDataViewModel

@Composable
fun LibrarySearchScreen(
    viewModel: NasaLibraryViewModel = hiltViewModel(),
    videoViewModel: VideoDataViewModel = hiltViewModel(),
    navController: NavController
) {
    LibraryScreenContent(
        viewModel = viewModel,
        videoViewModel = videoViewModel,
        navController = navController
    )
}