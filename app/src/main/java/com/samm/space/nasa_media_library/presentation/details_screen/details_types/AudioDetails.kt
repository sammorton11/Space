package com.samm.space.nasa_media_library.presentation.details_screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.samm.space.R
import com.samm.space.core.MediaDownloadType
import com.samm.space.core.MediaType
import com.samm.space.nasa_media_library.presentation.details_screen.components.AudioPlayer
import com.samm.space.nasa_media_library.presentation.details_screen.components.DetailsImage
import com.samm.space.nasa_media_library.presentation.details_screen.components.ExpandableDetailsCard
import com.samm.space.nasa_media_library.presentation.view_models.VideoDataViewModel
import com.samm.space.nasa_media_library.util.ViewUtils
import com.samm.space.presentation.buttons.DownloadFile
import com.samm.space.presentation.buttons.ShareButton
import com.samm.space.presentation.labels.DateLabel
import com.samm.space.presentation.labels.Title

@Composable
fun AudioDetails(
    viewModel: VideoDataViewModel,
    mediaType: MediaType,
    context: Context,
    title: String?,
    date: String?,
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
    DateLabel(date = date)
}