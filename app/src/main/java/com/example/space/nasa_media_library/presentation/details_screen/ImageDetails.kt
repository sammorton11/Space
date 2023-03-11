package com.example.space.nasa_media_library.presentation.details_screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.core.net.toUri
import com.example.space.core.MediaDownloadType
import com.example.space.core.MediaType
import com.example.space.nasa_media_library.presentation.components.cards.DetailsImage
import com.example.space.nasa_media_library.presentation.components.cards.ExpandableDetailsCard
import com.example.space.nasa_media_library.presentation.view_models.VideoDataViewModel
import com.example.space.nasa_media_library.util.ViewModelUtils
import com.example.space.presentation.buttons.ShareButton
import com.example.space.presentation.util.DownloadFile

@Composable
fun ImageDetails(
    viewModel: VideoDataViewModel,
    mediaType: MediaType,
    context: Context,
    url: String,
    description: String,
    backgroundColor: Color,
) {
    val utils = ViewModelUtils()
    val mUri = utils.getUri(viewModel, mediaType)
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