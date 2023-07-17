package com.samm.space.features.nasa_media_library_page.presentation.details_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.samm.space.common.presentation.ProgressBar
import com.samm.space.common.presentation.labels.ErrorText
import com.samm.space.core.MediaType
import com.samm.space.features.nasa_media_library_page.presentation.state.DetailsScreenState
import com.samm.space.features.nasa_media_library_page.util.LibraryUiEvent

@Composable
fun DetailsScreen(
    state: DetailsScreenState,
    event: (LibraryUiEvent) -> Unit,
    getUri: (String?, MediaType) -> String,
    getMediaData: (url: String) -> Unit
) {
    val mediaDataUrl = state.metaDataUrl
    LaunchedEffect(mediaDataUrl) { getMediaData(mediaDataUrl ?: "") }

    when {
        state.isLoading -> {
            ProgressBar()
        }
        state.data?.isNotEmpty() == true -> {
            DetailsScreenContent(
                state = state,
                event = event,
                getUri = getUri
            )
        }
        state.error.isNotEmpty() -> {
            ErrorText(error = state.error)
        }
    }
}