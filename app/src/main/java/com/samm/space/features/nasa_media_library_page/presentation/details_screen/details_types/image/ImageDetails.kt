package com.samm.space.features.nasa_media_library_page.presentation.details_screen.details_types.image

import androidx.compose.runtime.Composable
import com.samm.space.common.presentation.util.WindowInfo
import com.samm.space.common.presentation.util.rememberWindowInfo
import com.samm.space.core.MediaType
import com.samm.space.features.nasa_media_library_page.presentation.state.DetailsScreenState
import com.samm.space.features.nasa_media_library_page.util.LibraryUiEvent

@Composable
fun ImageDetails(
    state: DetailsScreenState,
    event: (LibraryUiEvent) -> Unit,
    getUri: (String?, MediaType) -> String
) {
    val window = rememberWindowInfo()

    when (window.screenWidthInfo) {

        is WindowInfo.WindowType.Compact -> {
            ImageDetailsCompact(
                state = state,
                event = event,
                getUri = getUri
            )
        }
        is WindowInfo.WindowType.Medium -> {
            ImageDetailsMedium(
                state = state,
                event = event,
                getUri = getUri
            )
        }
        is WindowInfo.WindowType.Expanded -> {
            ImageDetailsExpanded(
                state = state,
                event = event,
                getUri = getUri
            )
        }
    }
}