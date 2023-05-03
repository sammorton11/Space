package com.samm.space.pages.nasa_media_library_page.presentation.details_screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.samm.space.core.MediaType
import com.samm.space.core.MediaType.Companion.toMediaType
import com.samm.space.pages.nasa_media_library_page.presentation.details_screen.details_types.AudioDetails
import com.samm.space.pages.nasa_media_library_page.presentation.details_screen.details_types.ImageDetails
import com.samm.space.pages.nasa_media_library_page.presentation.details_screen.details_types.VideoDetails
import com.samm.space.pages.nasa_media_library_page.presentation.state.MediaDataState

@Composable
fun DetailsScreenContent(
    metaDataUrl: String,
    description: String,
    type: String,
    title: String?,
    date: String?,
    state: MediaDataState,
    decodeText: (String) -> String,
    getUri: (String?, MediaType) -> String,
    extractUrlsFromJsonArray: (String) -> ArrayList<String>,
    fileTypeCheck:(array: ArrayList<String>, mediaType: MediaType) -> String
) {

    val context = LocalContext.current
    val decodedDescription = decodeText(description)
    val mediaType = type.toMediaType()

    val data = state.data
    val mUri = getUri(data, mediaType)

    val uriList = data?.let { extractUrlsFromJsonArray(it) }
    val audioPlayerUri = uriList?.let { fileTypeCheck(it, mediaType) }


    when (mediaType) {
        MediaType.VIDEO -> {
            VideoDetails(
                context = context,
                mediaType = mediaType.type,
                state = data,
                mUri = mUri,
                title = title,
                date = date,
                description = decodedDescription
            )
        }
        MediaType.AUDIO -> {
            AudioDetails(
                audioPlayerUri = audioPlayerUri ?: "",
                mUri = mUri,
                mediaType = mediaType.type,
                context = context,
                title = title,
                date = date,
                description = decodedDescription
            )
        }
        MediaType.IMAGE -> {
            ImageDetails(
                mUri = mUri,
                mediaType = mediaType.type,
                context = context,
                url = metaDataUrl,
                title = title,
                date = date,
                description = decodedDescription
            )
        }
    }
}