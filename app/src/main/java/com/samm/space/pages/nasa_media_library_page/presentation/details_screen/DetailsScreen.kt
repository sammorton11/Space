package com.samm.space.pages.nasa_media_library_page.presentation.details_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.exoplayer2.ExoPlayer
import com.samm.space.common.presentation.ProgressBar
import com.samm.space.common.presentation.labels.ErrorText
import com.samm.space.core.MediaType
import com.samm.space.pages.nasa_media_library_page.presentation.state.MediaDataState

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


@Preview
@Composable
fun DetailsScreenPreview() {
    DetailsScreen(
        metaDataUrl = "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY",
        description = "This is a description",
        type = "image",
        title = "This is a title",
        date = "2022-04-04",
        state = MediaDataState(
            isLoading = false,
            data = "Im not empty",
            error = ""
        ),
        getMediaData = {  },
        decodeText = { "" },
        getUri = { _, _ -> "" },
        extractUrlsFromJsonArray = { arrayListOf("") },
        fileTypeCheck = {
            array, _ ->
            array.first()
        }
    )
}