package com.samm.space.pages.nasa_media_library_page.presentation.details_screen.details_types.audio

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.samm.space.common.presentation.util.WindowInfo
import com.samm.space.common.presentation.util.rememberWindowInfo
import com.samm.space.pages.nasa_media_library_page.presentation.view_models.MediaPlayerViewModel

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
    val viewModel: MediaPlayerViewModel = hiltViewModel()

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
                viewModel = viewModel,
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