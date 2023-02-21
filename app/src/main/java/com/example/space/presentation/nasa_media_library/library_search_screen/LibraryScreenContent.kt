package com.example.space.presentation.nasa_media_library.library_search_screen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.space.presentation.nasa_media_library.library_search_screen.components.*
import com.example.space.presentation.nasa_media_library.library_search_screen.components.other.ErrorText
import com.example.space.presentation.nasa_media_library.library_search_screen.components.other.LibraryList
import com.example.space.presentation.nasa_media_library.library_search_screen.components.other.ProgressBar
import com.example.space.presentation.view_model.NasaLibraryViewModel
import com.example.space.presentation.view_model.VideoDataViewModel

@Composable
fun LibraryScreenContent(viewModel: NasaLibraryViewModel, videoViewModel: VideoDataViewModel, navController: NavController) {
    val state = viewModel.state

    Column {
        Title("Image Video Library", 15.dp)
        SearchField(onSearch = { query ->
            viewModel.getData(query)
        })
        if (state.value.error.isNotBlank()) {
            ErrorText(state = state)
        }
        if (state.value.isLoading) {
            ProgressBar()
        }
        LibraryList(
            navController = navController,
            state = state
        )
    }
}