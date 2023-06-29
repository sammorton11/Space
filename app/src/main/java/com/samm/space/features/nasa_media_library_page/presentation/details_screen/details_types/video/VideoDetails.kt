package com.samm.space.features.nasa_media_library_page.presentation.details_screen.details_types.video

import androidx.compose.runtime.Composable
import com.samm.space.common.presentation.util.WindowInfo
import com.samm.space.common.presentation.util.rememberWindowInfo

@Composable
fun VideoDetails(
    mediaType: String,
    uri: String?,
    title: String?,
    date: String?,
    description: String
) {

    val window = rememberWindowInfo()

    when(window.screenWidthInfo) {

        is WindowInfo.WindowType.Compact -> {
            VideoDetailsCompact(
                mediaType = mediaType,
                uri = uri,
                title = title,
                date = date,
                description = description
            )
        }

        is WindowInfo.WindowType.Medium -> {
             VideoDetailsMedium(
                mediaType = mediaType,
                title = title,
                uri = uri,
                date = date,
                description = description
            )
        }

        is WindowInfo.WindowType.Expanded -> {
            VideoDetailsExpanded(
                mediaType = mediaType,
                uri = uri,
                title = title,
                date = date,
                description = description
            )
        }
    }
}