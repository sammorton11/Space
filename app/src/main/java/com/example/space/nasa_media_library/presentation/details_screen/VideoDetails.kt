package com.example.space.nasa_media_library.presentation.details_screen

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.core.net.toUri
import com.example.space.core.Constants
import com.example.space.nasa_media_library.presentation.components.cards.CardMediaPlayer
import com.example.space.nasa_media_library.presentation.components.cards.ExpandableDetailsCard
import com.example.space.nasa_media_library.presentation.components.cards.getUri
import com.example.space.nasa_media_library.presentation.view_models.VideoDataViewModel
import com.example.space.presentation.buttons.ShareButton
import com.example.space.presentation.util.DownloadFile

@Composable
fun VideoDetails(context: Context, viewModel: VideoDataViewModel, mediaType: String, description: String, backgroundColor: Color) {
    val mUri = getUri(viewModel, mediaType)
    CardMediaPlayer(videoViewModel = viewModel, uri = mUri)
    ExpandableDetailsCard(content = description, color = backgroundColor)
    DownloadFile(
        url = mUri,
        context = context,
        filename = mUri,
        mimeType = Constants.VIDEO_MIME_TYPE,
        subPath = Constants.VIDEO_SUB_PATH
    )
    ShareButton(
        uri = mUri.toUri(),
        type = Constants.VIDEO_MIME_TYPE
    )
}