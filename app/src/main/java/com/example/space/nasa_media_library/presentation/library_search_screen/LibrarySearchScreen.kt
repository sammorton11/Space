package com.example.space.nasa_media_library.presentation.library_search_screen

import android.annotation.SuppressLint
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.space.nasa_media_library.presentation.view_models.MediaLibraryViewModel
import com.example.space.nasa_media_library.presentation.view_models.VideoDataViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LibrarySearchScreen(
    viewModel: MediaLibraryViewModel = hiltViewModel(),
    videoViewModel: VideoDataViewModel = hiltViewModel(),
    navController: NavController,
    filterType: MutableState<String>,
    backgroundType: MutableState<Int>,
    backgroundList: List<Int>
) {
    LibraryScreenContent(
        viewModel = viewModel,
        videoViewModel = videoViewModel,
        navController = navController,
        filterType = filterType,
        backgroundType = backgroundType,
        backgroundList = backgroundList
    )
}