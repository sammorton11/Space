package com.example.space.presentation.nasa_media_library.library_search_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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

    val lazyGridState = rememberLazyGridState()

    Column() {
        Title(title, 15.dp)
       val scrollState = derivedStateOf {
           lazyGridState.firstVisibleItemIndex
        }
        if ( scrollState.value == 0) {
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
                    scrollState = lazyGridState
                )
            }
        }
    }
}