package com.samm.space.features.nasa_media_library_page.presentation.details_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.samm.space.common.presentation.ProgressBar
import com.samm.space.common.presentation.labels.ErrorText
import com.samm.space.core.MediaType
import com.samm.space.features.nasa_media_library_page.presentation.state.DetailsScreenState

@Composable
fun DetailsScreen(
    state: DetailsScreenState,
    getMediaData: (url: String) -> Unit,
    getUri: (String?, MediaType) -> String
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
                getUri = getUri
            )
        }
        state.error.isNotEmpty() -> {
            ErrorText(error = state.error)
        }
    }
}

