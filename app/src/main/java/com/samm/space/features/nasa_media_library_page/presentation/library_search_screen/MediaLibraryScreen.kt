package com.samm.space.features.nasa_media_library_page.presentation.library_search_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.samm.space.common.presentation.ProgressBar
import com.samm.space.common.presentation.labels.ErrorText
import com.samm.space.common.presentation.util.WindowInfo
import com.samm.space.common.presentation.util.rememberWindowInfo
import com.samm.space.core.Constants.NO_BACKGROUND
import com.samm.space.features.nasa_media_library_page.domain.models.Item
import com.samm.space.features.nasa_media_library_page.presentation.library_search_screen.components.other.SearchField
import com.samm.space.features.nasa_media_library_page.presentation.state.MediaLibraryState
import com.samm.space.features.nasa_media_library_page.util.LibraryUiEvent
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

    Column(modifier = modifier.testTag("Media Library Screen")) {
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