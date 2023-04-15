package com.samm.space.nasa_media_library_page.presentation.details_screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.samm.space.core.MediaType
import com.samm.space.core.MediaType.Companion.toMediaType
import com.samm.space.nasa_media_library_page.presentation.details_screen.details_types.AudioDetails
import com.samm.space.nasa_media_library_page.presentation.details_screen.details_types.ImageDetails
import com.samm.space.nasa_media_library_page.presentation.details_screen.details_types.VideoDetails
import com.samm.space.nasa_media_library_page.presentation.view_models.MediaDataViewModel

@Composable
fun DetailsScreenContent(
    metaDataUrl: String,
    description: String,
    type: String,
    title: String?,
    date: String?,
    viewModel: MediaDataViewModel
) {

    val decodedDescription = viewModel.decodeText(description)
    val context = LocalContext.current
    val mediaType = type.toMediaType()

    val state = viewModel.state.value.data
    val mUri = viewModel.getUri(state, mediaType)

    var audioPlayerUri = ""
    state?.let {
        if (it.isNotEmpty()) {
            val uriList = viewModel.extractUrlsFromJsonArray(it)
            audioPlayerUri = viewModel.fileTypeCheck(uriList, mediaType)
        }
    }

    when (mediaType) {
        MediaType.VIDEO -> {
            VideoDetails(
                context = context,
                state = state,
                mUri = mUri,
                title = title,
                date = date,
                description = decodedDescription
            )
        }
        MediaType.AUDIO -> {
            AudioDetails(
                audioPlayerUri = audioPlayerUri,
                mUri = mUri,
                context = context,
                title = title,
                date = date,
                description = decodedDescription
            )
        }
        MediaType.IMAGE -> {
            ImageDetails(
                mUri = mUri,
                context = context,
                url = metaDataUrl,
                title = title,
                date = date,
                description = decodedDescription
            )
        }
    }
}