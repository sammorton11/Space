package com.samm.space.nasa_media_library_page.presentation.library_search_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.samm.space.core.Constants.NO_BACKGROUND
import com.samm.space.nasa_media_library_page.presentation.library_search_screen.components.other.LibraryListContent
import com.samm.space.nasa_media_library_page.presentation.library_search_screen.components.other.SearchField
import com.samm.space.nasa_media_library_page.presentation.view_models.MediaLibraryViewModel
import com.samm.space.presentation_common.ProgressBar
import com.samm.space.presentation_common.labels.ErrorText
import com.samm.space.presentation_common.util.WindowInfo
import com.samm.space.presentation_common.util.rememberWindowInfo

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MediaLibraryScreen(
    viewModel: MediaLibraryViewModel,
    navController: NavController
){
    val list = viewModel.state.value.data
    val error = viewModel.state.value.error
    val isLoading = viewModel.state.value.isLoading

    val lazyGridState = rememberLazyStaggeredGridState()
    val scrollState = remember { derivedStateOf { lazyGridState.firstVisibleItemIndex }}
    val window = rememberWindowInfo()

    val imageScaleType = ContentScale.FillBounds
    val savedSearchTextState = viewModel.getSavedSearchText().collectAsState(initial = "")
    val background = viewModel.backgroundType.observeAsState(initial = NO_BACKGROUND)

    val filterType = viewModel.listFilterType.observeAsState("")

    val gridCells = when (window.screenWidthInfo) {
        is WindowInfo.WindowType.Compact -> 2
        is WindowInfo.WindowType.Medium -> 3
        is WindowInfo.WindowType.Expanded -> 4
    }

    // Two modifiers for if the user does not select a background
    val modifier = if (background.value == NO_BACKGROUND) {
        Modifier.fillMaxSize()
    } else {
        Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = background.value),
                contentScale = imageScaleType
            )
    }

    Column(modifier = modifier) {
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
                    viewModel.updateListFilterType("")
                },
                savedQuery = savedSearchTextState.value
            )
        }

        when {
            isLoading -> {
                ProgressBar()
            }
            list.isNotEmpty() -> {
                LibraryListContent(
                    navController = navController,
                    viewModel = viewModel,
                    filterType = filterType,
                    data = list,
                    scrollState = lazyGridState,
                    gridCells = gridCells,
                    imageScaleType = imageScaleType
                )
            }
            error.isNotBlank() -> {
                ErrorText(error = error)
            }
        }
    }
}