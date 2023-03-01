package com.example.space.presentation.nasa_media_library.library_search_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.space.core.WindowInfo
import com.example.space.core.rememberWindowInfo
import com.example.space.presentation.nasa_media_library.components.other.ErrorText
import com.example.space.presentation.nasa_media_library.components.other.LibraryList
import com.example.space.presentation.nasa_media_library.components.other.ProgressBar
import com.example.space.presentation.nasa_media_library.components.other.SearchField
import com.example.space.presentation.nasa_media_library.view_models.MediaLibraryViewModel
import com.example.space.presentation.nasa_media_library.view_models.VideoDataViewModel

@Composable
fun LibraryScreenContent(
    viewModel: MediaLibraryViewModel,
    videoViewModel: VideoDataViewModel,
    navController: NavController,
    filterType: MutableState<String>
){
    val list = viewModel.state.value.data
    val error = viewModel.state.value.error
    val isLoading = viewModel.state.value.isLoading

    val lazyGridState = rememberLazyGridState()
    val scrollState = derivedStateOf { lazyGridState.firstVisibleItemIndex }

    val window = rememberWindowInfo()

    Column(Modifier.fillMaxSize()) {
        if ( scrollState.value == 0) {
            SearchField(onSearch = { query ->
                viewModel.getData(query)
            })
        }
        when {
            error.isNotBlank() -> {
                ErrorText(error = error)
            }
            isLoading -> {
                ProgressBar()
            }
            list.isNotEmpty() -> {
                Box(modifier = Modifier
                    .weight(1f, fill = true)) {

                    when(window.screenWidthInfo) {
                        is WindowInfo.WindowType.Compact -> {
                            LibraryList(
                                navController = navController,
                                data = list,
                                scrollState = lazyGridState,
                                viewModel = videoViewModel,
                                filterType = filterType,
                                gridCells = 2
                            )
                        }
                        is WindowInfo.WindowType.Medium -> {
                            LibraryList(
                                navController = navController,
                                data = list,
                                scrollState = lazyGridState,
                                viewModel = videoViewModel,
                                filterType = filterType,
                                gridCells = 3
                            )
                        }
                        is WindowInfo.WindowType.Expanded -> {
                            LibraryList(
                                navController = navController,
                                data = list,
                                scrollState = lazyGridState,
                                viewModel = videoViewModel,
                                filterType = filterType,
                                gridCells = 4
                            )
                        }
                    }
                }
            }
        }
    }
}