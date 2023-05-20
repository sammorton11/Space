package com.samm.media_library.nasa_media_library_page.presentation.details_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.samm.media_library.nasa_media_library_page.presentation.state.MediaDataState
import com.samm.shared_resources.util.MediaType
import com.samm.shared_ui_module.presentation.ProgressBar
import com.samm.shared_ui_module.presentation.labels.ErrorText

@Composable
fun DetailsScreen(
    metaDataUrl: String,
    description: String,
    type: String,
    title: String?,
    date: String?,
    state: MediaDataState,
    getMediaData: (url: String) -> Unit,
    decodeText: (String) -> String,
    getUri: (String?, MediaType) -> String,
    extractUrlsFromJsonArray: (String) -> ArrayList<String>,
    fileTypeCheck:(array: ArrayList<String>, mediaType: MediaType) -> String
) {

    LaunchedEffect(metaDataUrl) {
        getMediaData(metaDataUrl)
    }

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
                decodeText = decodeText,
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
