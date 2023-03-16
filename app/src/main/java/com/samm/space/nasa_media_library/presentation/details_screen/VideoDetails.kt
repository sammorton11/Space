package com.samm.space.nasa_media_library.presentation.details_screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.samm.space.core.MediaDownloadType
import com.samm.space.core.MediaType
import com.samm.space.nasa_media_library.presentation.components.cards.CardMediaPlayer
import com.samm.space.nasa_media_library.presentation.components.cards.ExpandableDetailsCard
import com.samm.space.nasa_media_library.presentation.view_models.VideoDataViewModel
import com.samm.space.nasa_media_library.util.ViewUtils
import com.samm.space.presentation.buttons.OpenChromeButton
import com.samm.space.presentation.buttons.ShareButton
import com.samm.space.presentation.labels.Title
import com.samm.space.presentation.util.DownloadFile

@Composable
fun VideoDetails(
    context: Context,
    viewModel: VideoDataViewModel,
    mediaType: MediaType,
    title: String?,
    description: String,
    backgroundColor: Color,
) {
    val utils = ViewUtils()
    val mUri = utils.getUri(viewModel, mediaType)

    title?.let {
        Title(text = it, paddingValue = 15.dp)
    }
    CardMediaPlayer(
        videoViewModel = viewModel,
        uri = mUri
    )
    ExpandableDetailsCard(
        content = description,
        color = backgroundColor
    )
    OpenChromeButton(
        context = context,
        uri = mUri
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