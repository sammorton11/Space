package com.samm.space.features.nasa_media_library_page.presentation.library_search_screen

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.samm.space.common.presentation.ProgressBar
import com.samm.space.common.presentation.labels.ErrorText
import com.samm.space.common.presentation.util.WindowInfo
import com.samm.space.common.presentation.util.rememberWindowInfo
import com.samm.space.core.Constants.NO_BACKGROUND
import com.samm.space.core.Constants.imageScaleType
import com.samm.space.features.nasa_media_library_page.domain.models.Item
import com.samm.space.features.nasa_media_library_page.presentation.library_search_screen.components.other.SearchField
import com.samm.space.features.nasa_media_library_page.presentation.state.MediaLibraryState
import com.samm.space.features.nasa_media_library_page.util.LibraryUiEvent
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LibraryListScreen(
    state: MediaLibraryState,
    event: (LibraryUiEvent) -> Unit,
    navigate: (route: String) -> Unit,
    getSavedSearchText: () -> Flow<String>,
    filteredList: (data: List<Item?>, filterType: String) -> List<Item?>,
    encodeText: (text: String?) -> String
) {

    val lazyGridState = rememberLazyStaggeredGridState()
    val scrollState = remember { derivedStateOf { lazyGridState.firstVisibleItemIndex } }
    val window = rememberWindowInfo()
    val savedSearchTextState = getSavedSearchText().collectAsStateWithLifecycle("").value

    val gridCells = when (window.screenWidthInfo) {
        is WindowInfo.WindowType.Compact -> 2
        is WindowInfo.WindowType.Medium -> 3
        is WindowInfo.WindowType.Expanded -> 4
    }

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

//    val isPortraitMode = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT

    val modifier = Modifier.then(
        if (state.backgroundType == NO_BACKGROUND || !isPortrait) {
            Modifier.fillMaxSize()
        } else {
            Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = state.backgroundType),
                    contentScale = imageScaleType
                )
        }
    )

    Box(modifier = modifier) {


        Column(modifier = Modifier.testTag("Media Library Screen")) {
            if (scrollState.value == 0 || state.data.size <= 2) {
                SearchField(
                    onSearch = { query ->
                        event(LibraryUiEvent.SearchLibrary(query))
                        event(LibraryUiEvent.UpdateFilterType(""))
                    },
                    savedQuery = savedSearchTextState
                )
            }

            when {
                state.isLoading -> {
                    ProgressBar()
                }

                state.data.isNotEmpty() -> {
                    LibraryListContent(
                        state = state,
                        sendEvent = event,
                        scrollState = lazyGridState,
                        gridCells = gridCells,
                        filteredList = filteredList,
                        encodeText = encodeText,
                        navigate = navigate
                    )
                }

                state.error.isNotBlank() -> {
                    ErrorText(error = state.error)
                    Button(
                        onClick = {
                            event(LibraryUiEvent.SearchLibrary(savedSearchTextState))
                        }
                    ) {
                        Text(text = "Refresh")
                    }
                }
            }
        }
    }
}