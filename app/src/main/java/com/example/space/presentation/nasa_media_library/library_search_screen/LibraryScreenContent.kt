package com.example.space.presentation.nasa_media_library.library_search_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.space.presentation.nasa_media_library.components.other.*
import com.example.space.presentation.nasa_media_library.view_models.NasaLibraryViewModel
import com.example.space.presentation.nasa_media_library.view_models.VideoDataViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreenContent(
    viewModel: NasaLibraryViewModel,
    videoViewModel: VideoDataViewModel,
    navController: NavController,
    gridCells: MutableState<Int>,
    drawerState: DrawerState
){
    val list = viewModel.state.value.data
    val error = viewModel.state.value.error
    val isLoading = viewModel.state.value.isLoading

    val lazyGridState = rememberLazyGridState()
    val scrollState = derivedStateOf { lazyGridState.firstVisibleItemIndex }

    Column(Modifier.fillMaxSize()) {
        if ( scrollState.value == 0) {
            SearchField(onSearch = { query ->
                viewModel.getData(query)
            })
        }
        when {
            error.isNotBlank() -> { ErrorText(error = error) }
            isLoading -> { ProgressBar() }
            list.isNotEmpty() -> {
                LibraryList(
                    navController = navController,
                    data = list,
                    scrollState = lazyGridState,
                    viewModel = videoViewModel,
                    gridCells = gridCells.value
                )
            }
        }
    }
}