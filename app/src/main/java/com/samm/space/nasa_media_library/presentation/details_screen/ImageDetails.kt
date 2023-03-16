package com.samm.space.nasa_media_library.presentation.details_screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.samm.space.core.MediaDownloadType
import com.samm.space.core.MediaType
import com.samm.space.nasa_media_library.presentation.components.cards.DetailsImage
import com.samm.space.nasa_media_library.presentation.components.cards.ExpandableDetailsCard
import com.samm.space.nasa_media_library.presentation.view_models.VideoDataViewModel
import com.samm.space.nasa_media_library.util.ViewUtils
import com.samm.space.presentation.buttons.ShareButton
import com.samm.space.presentation.labels.Title
import com.samm.space.presentation.util.DownloadFile

@Composable
fun ImageDetails(
    viewModel: VideoDataViewModel,
    mediaType: MediaType,
    context: Context,
    url: String,
    title: String?,
    description: String,
    backgroundColor: Color,
) {
    val utils = ViewUtils()
    val mUri = utils.getUri(viewModel, mediaType)

    title?.let {
        Title(text = it, paddingValue = 15.dp)
    }

    DetailsImage(
        imageLink = mUri,
        scale = ContentScale.FillBounds
    )
    ExpandableDetailsCard(
        content = description,
        color = backgroundColor
    )
    DownloadFile(
        url = url,
        context = context,
        filename = url,
        mimeType = MediaDownloadType.IMAGE_JPEG.mimeType,
        subPath = MediaDownloadType.IMAGE_JPEG.subPath
    )
    ShareButton(
        uri = url.toUri(),
        type = MediaDownloadType.IMAGE_JPEG.mimeType
    )
}