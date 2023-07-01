package com.samm.space.features.nasa_media_library_page.presentation.details_screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.samm.space.core.MediaType
import com.samm.space.core.MediaType.Companion.toMediaType
import com.samm.space.features.nasa_media_library_page.presentation.details_screen.details_types.audio.AudioDetails
import com.samm.space.features.nasa_media_library_page.presentation.details_screen.details_types.image.ImageDetails
import com.samm.space.features.nasa_media_library_page.presentation.details_screen.details_types.video.VideoDetails
import com.samm.space.features.nasa_media_library_page.presentation.state.DetailsScreenState
import com.samm.space.features.nasa_media_library_page.util.LibraryUiEvent

@Composable
fun DetailsScreenContent(
    state: DetailsScreenState,
    event: (LibraryUiEvent) -> Unit,
    getUri: (String?, MediaType) -> String
) {

    when (state.type?.toMediaType()) {
        MediaType.VIDEO -> {
            VideoDetails(
                state = state,
                event = event,
                getUri = getUri
            )
        }
        MediaType.AUDIO -> {
            AudioDetails(
                state = state,
                event = event,
                getUri = getUri
            )
        }
        MediaType.IMAGE -> {
            ImageDetails(
                state = state,
                event = event,
                getUri = getUri
            )
        }
        else -> {
            Text(text = "Missing Media")
        }
    }
}