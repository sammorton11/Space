package com.example.space.presentation.nasa_media_library.library_search_screen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.space.presentation.nasa_media_library.library_search_screen.components.other.*
import com.example.space.presentation.view_model.NasaLibraryViewModel
import com.example.space.presentation.view_model.VideoDataViewModel

@Composable
fun LibraryScreenContent(
    viewModel: NasaLibraryViewModel,
    videoViewModel: VideoDataViewModel,
    navController: NavController
){
    val state = viewModel.state
    val list = viewModel.state.value.data
    val error = viewModel.state.value.error
    val isLoading = viewModel.state.value.isLoading
    val title = "NASA Media Library"

    Column {
        Title(title, 15.dp)
        SearchField(onSearch = { query ->
            viewModel.getData(query)
        })
        when {
            error.isNotBlank() -> { ErrorText(error = error) }
            isLoading -> { ProgressBar() }
            !list.isNullOrEmpty() -> {
                LibraryList(
                    navController = navController,
                    data = list
                )
            }
        }
    }
}