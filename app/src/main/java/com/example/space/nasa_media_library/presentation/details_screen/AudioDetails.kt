package com.example.space.nasa_media_library.presentation.details_screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.space.R
import com.example.space.core.MediaDownloadType
import com.example.space.core.MediaType
import com.example.space.nasa_media_library.presentation.components.cards.AudioPlayer
import com.example.space.nasa_media_library.presentation.components.cards.DetailsImage
import com.example.space.nasa_media_library.presentation.components.cards.ExpandableDetailsCard
import com.example.space.nasa_media_library.presentation.view_models.VideoDataViewModel
import com.example.space.nasa_media_library.util.ViewUtils
import com.example.space.presentation.Title
import com.example.space.presentation.buttons.ShareButton
import com.example.space.presentation.util.DownloadFile

@Composable
fun AudioDetails(
    viewModel: VideoDataViewModel,
    mediaType: MediaType,
    context: Context,
    title: String?,
    description: String,
    backgroundColor: Color
) {
    val utils = ViewUtils()
    val mUri = utils.getUri(viewModel, mediaType)

    title?.let {
        Title(text = it, paddingValue = 15.dp)
    }

    DetailsImage(
        id = R.drawable.tipper_space_man,
        scale = ContentScale.FillBounds
    )
    AudioPlayer(
        viewModel = viewModel,
        mediaType = mediaType
    )
    ExpandableDetailsCard(
        content = description,
        color = backgroundColor
    )
    DownloadFile(
        url = mUri,
        context = context,
        filename = mUri,
        mimeType = MediaDownloadType.AUDIO_WAV.mimeType,
        subPath = MediaDownloadType.AUDIO_WAV.subPath
    )
    ShareButton(
        uri = mUri.toUri(),
        type = MediaDownloadType.AUDIO_WAV.mimeType
    )
}