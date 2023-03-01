package com.example.space.presentation.nasa_media_library.library_search_screen

import android.annotation.SuppressLint
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.space.presentation.nasa_media_library.view_models.NasaLibraryViewModel
import com.example.space.presentation.nasa_media_library.view_models.VideoDataViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibrarySearchScreen(
    viewModel: NasaLibraryViewModel = hiltViewModel(),
    videoViewModel: VideoDataViewModel = hiltViewModel(),
    navController: NavController,
    drawerState: DrawerState,
    gridCells: MutableState<Int>
) {
    LibraryScreenContent(
        viewModel = viewModel,
        videoViewModel = videoViewModel,
        navController = navController,
        gridCells = gridCells,
        drawerState = drawerState
    )
}