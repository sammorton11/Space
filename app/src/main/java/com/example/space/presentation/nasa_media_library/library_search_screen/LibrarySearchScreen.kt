package com.example.space.presentation.nasa_media_library.library_search_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.space.presentation.view_model.library.NasaLibraryViewModel
import com.example.space.presentation.view_model.library.VideoDataViewModel

@Composable
fun LibrarySearchScreen(
    viewModel: NasaLibraryViewModel = hiltViewModel(),
    videoViewModel: VideoDataViewModel = hiltViewModel(),
    navController: NavController
) {
    Column(modifier = Modifier.fillMaxSize()) {
        LibraryScreenContent(
            viewModel = viewModel,
            videoViewModel = videoViewModel,
            navController = navController
        )
    }
}