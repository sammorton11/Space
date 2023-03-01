package com.example.space.nasa_media_library.presentation.library_search_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.space.core.WindowInfo
import com.example.space.core.rememberWindowInfo
import com.example.space.nasa_media_library.presentation.components.other.LibraryList
import com.example.space.nasa_media_library.presentation.components.other.SearchField
import com.example.space.nasa_media_library.presentation.view_models.MediaLibraryViewModel
import com.example.space.nasa_media_library.presentation.view_models.VideoDataViewModel
import com.example.space.presentation.ErrorText
import com.example.space.presentation.ProgressBar

@OptIn(ExperimentalFoundationApi::class)
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

    val lazyGridState = rememberLazyStaggeredGridState()
    val scrollState = remember { derivedStateOf { lazyGridState.firstVisibleItemIndex }}

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