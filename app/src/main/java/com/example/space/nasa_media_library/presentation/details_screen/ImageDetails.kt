package com.example.space.nasa_media_library.presentation.details_screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.space.core.Constants
import com.example.space.nasa_media_library.presentation.components.cards.CardImage
import com.example.space.nasa_media_library.presentation.components.cards.ExpandableDetailsCard
import com.example.space.nasa_media_library.presentation.components.cards.getUri
import com.example.space.nasa_media_library.presentation.view_models.VideoDataViewModel
import com.example.space.presentation.buttons.ShareButton
import com.example.space.presentation.util.DownloadFile

@Composable
fun ImageDetails(viewModel: VideoDataViewModel, mediaType: String, context: Context, url: String, description: String, backgroundColor: Color) {
    val mUri = getUri(viewModel, mediaType)
    CardImage(
        imageLink = mUri,
        height = 400.dp,
        width = 450.dp,
        scale = ContentScale.FillBounds,
        mediaType = mediaType
    )
    ExpandableDetailsCard(
        content = description,
        color = backgroundColor
    )
    DownloadFile(
        url = url,
        context = context,
        filename = url,
        mimeType = Constants.IMAGE_JPEG_MIME_TYPE,
        subPath = Constants.IMAGE_JPEG_SUB_PATH
    )
    ShareButton(uri = url.toUri(), type = Constants.IMAGE_JPEG_MIME_TYPE)
}