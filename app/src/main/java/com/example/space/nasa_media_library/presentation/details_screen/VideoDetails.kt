package com.example.space.nasa_media_library.presentation.details_screen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.core.net.toUri
import com.example.space.core.MediaDownloadType
import com.example.space.core.MediaType
import com.example.space.nasa_media_library.presentation.components.cards.CardMediaPlayer
import com.example.space.nasa_media_library.presentation.components.cards.ExpandableDetailsCard
import com.example.space.nasa_media_library.presentation.view_models.VideoDataViewModel
import com.example.space.nasa_media_library.util.ViewModelUtils
import com.example.space.presentation.buttons.ShareButton
import com.example.space.presentation.util.DownloadFile

@Composable
fun VideoDetails(
    context: Context,
    viewModel: VideoDataViewModel,
    mediaType: MediaType,
    description: String,
    backgroundColor: Color,
) {
    val utils = ViewModelUtils()
    val mUri = utils.getUri(viewModel, mediaType)
    Log.d("Card Media player url", mUri)


    CardMediaPlayer(
        videoViewModel = viewModel,
        uri = mUri
    )
    ExpandableDetailsCard(
        content = description,
        color = backgroundColor
    )
    DownloadFile(
        url = mUri,
        context = context,
        filename = mUri,
        mimeType = MediaDownloadType.VIDEO.mimeType,
        subPath = MediaDownloadType.VIDEO.subPath
    )
    ShareButton(
        uri = mUri.toUri(),
        type = MediaDownloadType.VIDEO.mimeType
    )
}