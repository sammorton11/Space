package com.samm.space.features.nasa_media_library_page.presentation.details_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.samm.space.common.presentation.ProgressBar
import com.samm.space.common.presentation.labels.ErrorText
import com.samm.space.core.MediaType
import com.samm.space.features.nasa_media_library_page.presentation.state.MediaDataState

@Composable
fun DetailsScreen(
    metaDataUrl: String,
    description: String,
    type: String,
    title: String?,
    date: String?,
    state: MediaDataState,
    getMediaData: (url: String) -> Unit,
    getUri: (String?, MediaType) -> String,
    extractUrlsFromJsonArray: (String) -> ArrayList<String>,
    fileTypeCheck:(array: ArrayList<String>, mediaType: MediaType) -> String
) {

    LaunchedEffect(metaDataUrl) { getMediaData(metaDataUrl) }

    when {
        state.isLoading -> {
            ProgressBar()
        }
        state.data?.isNotEmpty() == true -> {
            DetailsScreenContent(
                metaDataUrl = metaDataUrl,
                description = description,
                type = type,
                title = title,
                date = date,
                state = state,
                getUri = getUri,
                extractUrlsFromJsonArray = extractUrlsFromJsonArray,
                fileTypeCheck = fileTypeCheck
            )
        }
        state.error.isNotEmpty() -> {
            ErrorText(error = state.error)
        }
    }
}

