package com.samm.space.nasa_media_library.presentation.library_search_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.samm.space.core.Constants.NO_BACKGROUND
import com.samm.space.nasa_media_library.presentation.library_search_screen.components.other.LibraryList
import com.samm.space.nasa_media_library.presentation.library_search_screen.components.other.SearchField
import com.samm.space.nasa_media_library.presentation.view_models.MediaLibraryViewModel
import com.samm.space.presentation.labels.ErrorText
import com.samm.space.presentation.ProgressBar
import com.samm.space.presentation.util.WindowInfo
import com.samm.space.presentation.util.rememberWindowInfo

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

    val gridCells = when (window.screenWidthInfo) {
        is WindowInfo.WindowType.Compact -> 2
        is WindowInfo.WindowType.Medium -> 3
        is WindowInfo.WindowType.Expanded -> 4
    }

    // Two modifiers for if the user does not select a background
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
        /*
             Show the search field if the user scrolls passed the first row or the list size only
             has two items
         */
        if (scrollState.value == 0 || list.size <= 2) {
            SearchField(
                onSearch = { query ->
                    viewModel.getData(
                        query = query
                    )
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
                LibraryList(
                    navController = navController,
                    data = list,
                    scrollState = lazyGridState,
                    filterType = filterType,
                    gridCells = gridCells,
                    imageScaleType = imageScaleType
                )
            }
        }
    }
}