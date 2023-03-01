package com.example.space.presentation.nasa_media_library.library_search_screen

import android.annotation.SuppressLint
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.space.presentation.nasa_media_library.view_models.MediaLibraryViewModel
import com.example.space.presentation.nasa_media_library.view_models.VideoDataViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LibrarySearchScreen(
    viewModel: MediaLibraryViewModel = hiltViewModel(),
    videoViewModel: VideoDataViewModel = hiltViewModel(),
    navController: NavController,
    filterType: MutableState<String>
) {
    LibraryScreenContent(
        viewModel = viewModel,
        videoViewModel = videoViewModel,
        navController = navController,
        filterType = filterType
    )
}