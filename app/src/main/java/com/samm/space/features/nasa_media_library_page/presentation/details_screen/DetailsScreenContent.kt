package com.samm.space.features.nasa_media_library_page.presentation.details_screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.samm.space.core.MediaType
import com.samm.space.core.MediaType.Companion.toMediaType
import com.samm.space.features.nasa_media_library_page.presentation.details_screen.details_types.audio.AudioDetails
import com.samm.space.features.nasa_media_library_page.presentation.details_screen.details_types.image.ImageDetails
import com.samm.space.features.nasa_media_library_page.presentation.details_screen.details_types.video.VideoDetails
import com.samm.space.features.nasa_media_library_page.presentation.state.DetailsScreenState

@Composable
fun DetailsScreenContent(
    state: DetailsScreenState,
    getUri: (String?, MediaType) -> String
) {

    val mediaType = state.type?.toMediaType()
    val data = state.data
    val mediaFileUri = mediaType?.let { getUri(data, it) }

    when (mediaType) {
        MediaType.VIDEO -> {
            VideoDetails(
                mediaType = mediaType.type,
                uri = mediaFileUri,
                title = state.title,
                date = state.date,
                description = state.description!!
            )
        }
        MediaType.AUDIO -> {
            AudioDetails(
                audioPlayerUri = mediaFileUri ?: "",
                uri = mediaFileUri!!,
                mediaType = mediaType.type,
                title = state.title,
                date = state.date,
                description = state.description!!
            )
        }
        MediaType.IMAGE -> {
            ImageDetails(
                uri = mediaFileUri!!,
                mediaType = mediaType.type,
                title = state.title,
                date = state.date,
                description = state.description!!
            )
        }
        else -> {
            Text(text = "Missing Media")
        }
    }
}