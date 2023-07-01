package com.samm.space.features.nasa_media_library_page.presentation.details_screen.details_types.video

import androidx.compose.runtime.Composable
import com.samm.space.common.presentation.util.WindowInfo
import com.samm.space.common.presentation.util.rememberWindowInfo
import com.samm.space.core.MediaType
import com.samm.space.features.nasa_media_library_page.presentation.state.DetailsScreenState
import com.samm.space.features.nasa_media_library_page.util.LibraryUiEvent

@Composable
fun VideoDetails(
    state: DetailsScreenState,
    event: (LibraryUiEvent) -> Unit,
    getUri: (String?, MediaType) -> String
) {

    val window = rememberWindowInfo()

    when(window.screenWidthInfo) {

        is WindowInfo.WindowType.Compact -> {
            VideoDetailsCompact(
                state = state,
                event = event,
                getUri = getUri
            )
        }

        is WindowInfo.WindowType.Medium -> {
             VideoDetailsMedium(
                 state = state,
                 event = event,
                 getUri = getUri
            )
        }

        is WindowInfo.WindowType.Expanded -> {
            VideoDetailsExpanded(
                state = state,
                event = event,
                getUri = getUri
            )
        }
    }
}