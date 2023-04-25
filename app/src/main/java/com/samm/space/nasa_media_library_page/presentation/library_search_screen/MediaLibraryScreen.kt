package com.samm.space.nasa_media_library_page.presentation.library_search_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.LiveData
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.samm.space.core.Constants.NO_BACKGROUND
import com.samm.space.nasa_media_library_page.domain.models.Item
import com.samm.space.nasa_media_library_page.presentation.library_search_screen.components.other.LibraryListContent
import com.samm.space.nasa_media_library_page.presentation.library_search_screen.components.other.SearchField
import com.samm.space.nasa_media_library_page.presentation.state.NasaLibraryState
import com.samm.space.presentation_common.ProgressBar
import com.samm.space.presentation_common.labels.ErrorText
import com.samm.space.presentation_common.util.WindowInfo
import com.samm.space.presentation_common.util.rememberWindowInfo
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MediaLibraryScreen(
    state: NasaLibraryState,
    navController: NavController,
    getData: (String) -> Unit,
    updateFilterType:  (String) -> Unit,
    getSavedSearchText: () -> Flow<String>,
    backgroundType: LiveData<Int>,
    listFilterType: LiveData<String>,
    filterList: (data: List<Item?>, filterType: State<String>) -> List<Item?>,
    encodeText:(text: String?) -> String
){

    val lazyGridState = rememberLazyStaggeredGridState()
    val scrollState = remember { derivedStateOf { lazyGridState.firstVisibleItemIndex }}
    val window = rememberWindowInfo()

    val savedSearchTextState = getSavedSearchText().collectAsStateWithLifecycle("")
    val background = backgroundType.observeAsState(initial = NO_BACKGROUND)
    val filterType = listFilterType.observeAsState("")

    val imageScaleType = ContentScale.FillBounds

    val gridCells = when (window.screenWidthInfo) {
        is WindowInfo.WindowType.Compact -> 2
        is WindowInfo.WindowType.Medium -> 3
        is WindowInfo.WindowType.Expanded -> 4
    }

    val modifier =
        if (background.value == NO_BACKGROUND) {
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
        if (scrollState.value == 0 || state.data.size <= 2) {
            SearchField(
                onSearch = { query ->
                    getData(query)
                    updateFilterType("")
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
                    navController = navController,
                    filterType = filterType,
                    data = state.data,
                    scrollState = lazyGridState,
                    gridCells = gridCells,
                    imageScaleType = imageScaleType,
                    filterList = filterList,
                    encodeText = encodeText
                )
            }
            state.error.isNotBlank() -> {
                ErrorText(error = state.error)
                Button(onClick = { getData(savedSearchTextState.value) }) {
                    Text(text = "Refresh")
                }
            }
        }
    }
}