package com.example.space.nasa_media_library.presentation.library_search_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.space.core.Constants.NO_BACKGROUND
import com.example.space.nasa_media_library.presentation.components.other.LibraryList
import com.example.space.nasa_media_library.presentation.components.other.SearchField
import com.example.space.nasa_media_library.presentation.view_models.MediaLibraryViewModel
import com.example.space.presentation.ErrorText
import com.example.space.presentation.ProgressBar
import com.example.space.presentation.util.WindowInfo
import com.example.space.presentation.util.rememberWindowInfo

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LibraryScreenContent(
    viewModel: MediaLibraryViewModel,
    navController: NavController,
    filterType: MutableState<String>,
    backgroundType: MutableState<Int>
){
    val list = viewModel.state.value.data
    val error = viewModel.state.value.error
    val isLoading = viewModel.state.value.isLoading

    val lazyGridState = rememberLazyStaggeredGridState()
    val scrollState = remember { derivedStateOf { lazyGridState.firstVisibleItemIndex }}
    val window = rememberWindowInfo()
    val imageScaleType = ContentScale.FillBounds


    val mod = if (backgroundType.value == NO_BACKGROUND) {
        Modifier.fillMaxSize()
    } else {
        Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = backgroundType.value),
                contentScale = imageScaleType
            )
    }

    Column(modifier = mod) {
        if (scrollState.value == 0) {
            SearchField(
                onSearch = { query ->
                    viewModel.getData(query)
                    filterType.value = ""
                },
                savedQuery = viewModel.getSavedSearchText()
            )
        }

        when {
            error.isNotBlank() -> {
                ErrorText(error = error)
            }
            isLoading -> {
                ProgressBar()
            }
            list.isNotEmpty() -> {

                Box {
                    when(window.screenWidthInfo) {
                        is WindowInfo.WindowType.Compact -> {
                            LibraryList(
                                navController = navController,
                                data = list,
                                scrollState = lazyGridState,
                                filterType = filterType,
                                gridCells = 2,
                                imageScaleType = imageScaleType

                            )
                        }
                        is WindowInfo.WindowType.Medium -> {
                            LibraryList(
                                navController = navController,
                                data = list,
                                scrollState = lazyGridState,
                                filterType = filterType,
                                gridCells = 3,
                                imageScaleType = imageScaleType
                            )
                        }
                        is WindowInfo.WindowType.Expanded -> {
                            LibraryList(
                                navController = navController,
                                data = list,
                                scrollState = lazyGridState,
                                filterType = filterType,
                                gridCells = 4,
                                imageScaleType = imageScaleType
                            )
                        }
                    }
                }
            }
        }
    }
}