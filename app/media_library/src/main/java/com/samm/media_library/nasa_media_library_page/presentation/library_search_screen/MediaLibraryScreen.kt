package com.samm.media_library.nasa_media_library_page.presentation.library_search_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.samm.shared_ui_module.presentation.ProgressBar
import com.samm.core.domain.library_models.Item
import com.samm.media_library.nasa_media_library_page.presentation.library_search_screen.components.other.SearchField
import com.samm.media_library.nasa_media_library_page.presentation.state.MediaLibraryState
import com.samm.media_library.nasa_media_library_page.util.LibraryUiEvent
import com.samm.shared_resources.util.Constants.NO_BACKGROUND
import com.samm.shared_resources.util.WindowInfo
import com.samm.shared_resources.util.rememberWindowInfo
import com.samm.shared_ui_module.presentation.labels.ErrorText
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MediaLibraryScreen(
    event: (LibraryUiEvent) -> Unit,
    favorites: List<Item>,
    state: MediaLibraryState,
    navController: NavController,
    getSavedSearchText: () -> Flow<String>,
    backgroundType: Int,
    listFilterType: String,
    filteredList: (data: List<Item?>, filterType: String) -> List<Item?>,
    encodeText: (text: String?) -> String
) {

    val lazyGridState = rememberLazyStaggeredGridState()
    val scrollState = remember { derivedStateOf { lazyGridState.firstVisibleItemIndex } }
    val window = rememberWindowInfo()
    val savedSearchTextState = getSavedSearchText().collectAsStateWithLifecycle("")
    val imageScaleType = ContentScale.FillBounds

    val gridCells = when (window.screenWidthInfo) {
        is WindowInfo.WindowType.Compact -> 2
        is WindowInfo.WindowType.Medium -> 3
        is WindowInfo.WindowType.Expanded -> 4
    }

    val modifier = Modifier.then(
        if (backgroundType == NO_BACKGROUND) {
            Modifier.fillMaxSize()
        } else {
            Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = backgroundType),
                    contentScale = imageScaleType
                )
        }
    )

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        if (scrollState.value == 0 || state.data.size <= 2) {
            SearchField(
                onSearch = { query ->
                    event(LibraryUiEvent.SearchLibrary(query))
                    event(LibraryUiEvent.UpdateFilterType(""))
                },
                savedQuery = savedSearchTextState.value
            )
        }

        when {
            state.isLoading -> {
                ProgressBar()
            }
            state.data.isNotEmpty() -> {
                LibraryListContent(
                    favorites = favorites,
                    sendEvent = event,
                    navController = navController,
                    filterType = listFilterType,
                    data = state.data,
                    scrollState = lazyGridState,
                    gridCells = gridCells,
                    imageScaleType = imageScaleType,
                    filteredList = filteredList,
                    encodeText = encodeText
                )
            }
            state.error.isNotBlank() -> {
                ErrorText(error = state.error)
                Button(
                    onClick = {
                        event(LibraryUiEvent.SearchLibrary(savedSearchTextState.value))
                    }
                ) {
                    Text(text = "Refresh")
                }
            }
        }
    }
}