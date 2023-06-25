package com.samm.space.pages.nasa_media_library_page.presentation.details_screen.details_types.image

import android.content.Context
import androidx.compose.runtime.Composable
import com.samm.space.common.presentation.util.WindowInfo
import com.samm.space.common.presentation.util.rememberWindowInfo

@Composable
fun ImageDetails(
    mUri: String,
    mediaType: String,
    context: Context,
    url: String,
    title: String?,
    date: String?,
    description: String
) {
    val window = rememberWindowInfo()

    when (window.screenWidthInfo) {

        is WindowInfo.WindowType.Compact -> {
            ImageDetailsCompact(
                mUri = mUri,
                mediaType = mediaType,
                context = context,
                url = url,
                title = title,
                date = date,
                description = description
            )
        }
        is WindowInfo.WindowType.Medium -> {
            ImageDetailsMedium(
                mUri = mUri,
                mediaType = mediaType,
                context = context,
                url = url,
                title = title,
                date = date,
                description = description
            )
        }
        is WindowInfo.WindowType.Expanded -> {
            ImageDetailsExpanded(
                mUri = mUri,
                mediaType = mediaType,
                context = context,
                url = url,
                title = title,
                date = date,
                description = description
            )
        }
    }
}