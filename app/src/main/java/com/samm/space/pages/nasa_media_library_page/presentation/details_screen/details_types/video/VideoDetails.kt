package com.samm.space.pages.nasa_media_library_page.presentation.details_screen.details_types.video

import android.content.Context
import androidx.compose.runtime.Composable
import com.samm.space.common.presentation.util.WindowInfo
import com.samm.space.common.presentation.util.rememberWindowInfo

@Composable
fun VideoDetails(
    context: Context,
    mediaType: String,
    state: String?,
    mUri: String,
    title: String?,
    date: String?,
    description: String
) {

    val window = rememberWindowInfo()

    when(window.screenWidthInfo) {

        is WindowInfo.WindowType.Compact -> {
            VideoDetailsCompact(
                context = context,
                mediaType = mediaType,
                state = state,
                mUri = mUri,
                title = title,
                date = date,
                description = description
            )
        }

        is WindowInfo.WindowType.Medium -> {
            VideoDetailsMedium(
                context = context,
                mediaType = mediaType,
                state = state,
                mUri = mUri,
                title = title,
                date = date,
                description = description
            )
        }

        is WindowInfo.WindowType.Expanded -> {

            VideoDetailsExpanded(
                context = context,
                mediaType = mediaType,
                state = state,
                mUri = mUri,
                title = title,
                date = date,
                description = description
            )
        }
    }
}