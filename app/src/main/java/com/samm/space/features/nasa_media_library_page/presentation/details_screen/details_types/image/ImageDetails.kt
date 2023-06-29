package com.samm.space.features.nasa_media_library_page.presentation.details_screen.details_types.image

import androidx.compose.runtime.Composable
import com.samm.space.common.presentation.util.WindowInfo
import com.samm.space.common.presentation.util.rememberWindowInfo

@Composable
fun ImageDetails(
    uri: String,
    mediaType: String,
    title: String?,
    date: String?,
    description: String
) {
    val window = rememberWindowInfo()

    when (window.screenWidthInfo) {

        is WindowInfo.WindowType.Compact -> {
            ImageDetailsCompact(
                uri = uri,
                mediaType = mediaType,
                title = title,
                date = date,
                description = description
            )
        }
        is WindowInfo.WindowType.Medium -> {
            ImageDetailsMedium(
                uri = uri,
                mediaType = mediaType,
                title = title,
                date = date,
                description = description
            )
        }
        is WindowInfo.WindowType.Expanded -> {
            ImageDetailsExpanded(
                uri = uri,
                mediaType = mediaType,
                title = title,
                date = date,
                description = description
            )
        }
    }
}