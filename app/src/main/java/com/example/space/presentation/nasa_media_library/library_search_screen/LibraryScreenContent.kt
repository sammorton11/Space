package com.example.space.presentation.nasa_media_library.library_search_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
    val list = viewModel.state.value.data
    val error = viewModel.state.value.error
    val isLoading = viewModel.state.value.isLoading
    val title = "NASA Media Library"

    val scrollState = rememberScrollState()

    Column(modifier = Modifier.verticalScroll(scrollState)) {
        Title(title, 15.dp)
        if (scrollState.value > 0) {
            SearchField(onSearch = { query ->
                viewModel.getData(query)
            })
        }

        when {
            error.isNotBlank() -> { ErrorText(error = error) }
            isLoading -> { ProgressBar() }
            !list.isNullOrEmpty() -> {
                LibraryList(
                    navController = navController,
                    data = list,
                    scrollState = scrollState
                )
            }
        }
    }
}