package com.samm.space.features.nasa_media_library_page.presentation.details_screen.details_types.audio

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import com.samm.space.common.presentation.util.WindowInfo
import com.samm.space.common.presentation.util.rememberWindowInfo

@Composable
fun AudioDetails(
    audioPlayerUri: String,
    mUri: String,
    mediaType: String,
    context: Context,
    title: String?,
    date: String?,
    description: String
) {

    val window = rememberWindowInfo()
    Log.d("uri", mUri)
    Log.d("uri", audioPlayerUri)

    when (window.screenWidthInfo) {

        is WindowInfo.WindowType.Compact -> {
            AudioDetailsCompact(
                audioPlayerUri = audioPlayerUri,
                mUri = mUri,
                mediaType = mediaType,
                context = context,
                title = title,
                date = date,
                description = description
            )
        }

        is WindowInfo.WindowType.Medium -> {
            AudioDetailsMedium(
                audioPlayerUri = audioPlayerUri,
                mUri = mUri,
                mediaType = mediaType,
                context = context,
                title = title,
                date = date,
                description = description
            )
        }

        is WindowInfo.WindowType.Expanded -> {
            AudioDetailsExpanded(
                audioPlayerUri = audioPlayerUri,
                mUri = mUri,
                mediaType = mediaType,
                context = context,
                title = title,
                date = date,
                description = description
            )
        }
    }
}