package com.example.space.nasa_media_library.presentation.details_screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.core.net.toUri
import com.example.space.R
import com.example.space.core.Constants
import com.example.space.nasa_media_library.presentation.components.cards.*
import com.example.space.nasa_media_library.presentation.view_models.VideoDataViewModel
import com.example.space.nasa_media_library.util.ViewModelUtils
import com.example.space.presentation.buttons.ShareButton
import com.example.space.presentation.util.DownloadFile

@Composable
fun AudioDetails(
    viewModel: VideoDataViewModel,
    mediaType: String,
    context: Context,
    description: String,
    backgroundColor: Color
) {
    val utils = ViewModelUtils()
    val mUri = utils.getUri(viewModel, mediaType)

    DetailsImage(
        id = R.drawable.earth_from_moon,
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
        mimeType = Constants.AUDIO_WAV_MIME_TYPE,
        subPath = Constants.AUDIO_WAV_SUB_PATH
    )
    ShareButton(
        uri = mUri.toUri(),
        type = Constants.AUDIO_WAV_MIME_TYPE
    )
}