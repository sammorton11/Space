package com.example.space.nasa_media_library.presentation.details_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.space.core.Constants.AUDIO_WAV_MIME_TYPE
import com.example.space.core.Constants.AUDIO_WAV_SUB_PATH
import com.example.space.core.Constants.IMAGE_JPEG_SUB_PATH
import com.example.space.core.Constants.IMAGE_JPEG_MIME_TYPE
import com.example.space.core.Constants.VIDEO_MIME_TYPE
import com.example.space.core.Constants.VIDEO_SUB_PATH
import com.example.space.core.Constants.stockImage
import com.example.space.nasa_media_library.presentation.components.cards.*
import com.example.space.nasa_media_library.presentation.view_models.VideoDataViewModel
import com.example.space.presentation.ProgressBar
import com.example.space.presentation.buttons.ShareButton
import com.example.space.presentation.util.DownloadFile


/**
 *
 * Todo:
 *      - There is a lot of duplicated code in the when statement for different media types.
 *        This code can be extracted out into separate composable functions or utility classes for better readability
 *        and maintainability.
 *      - Also, the naming of the getUri function is vague and its purpose is unclear.
 *      - Further, the item composable contains logic for both the view and the viewmodel,
 *        which violates the separation of concerns principle.
 *
 *
 */


@Composable
fun DetailsScreenContent(
    url: String, 
    description: String, 
    mediaType: String,
    viewModel: VideoDataViewModel
) {
    val decodedDescription = viewModel.decodeText(description)
    val context = LocalContext.current
    val backgroundColor = MaterialTheme.colorScheme.background

    LazyColumn (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        viewModel.getVideoData(url)

        item {
            if (viewModel.state.value.isLoading) { ProgressBar() }
            when (mediaType) {
                "video" -> {
                    val mUri = getUri(viewModel, mediaType)
                    CardMediaPlayer(videoViewModel = viewModel, uri = mUri)
                    ExpandableDetailsCard(content = decodedDescription, color = backgroundColor)
                    DownloadFile(
                        url = mUri,
                        context = context,
                        filename = mUri,
                        mimeType = VIDEO_MIME_TYPE,
                        subPath = VIDEO_SUB_PATH
                    )
                    ShareButton(
                        uri = mUri.toUri(),
                        type = VIDEO_MIME_TYPE
                    )
                }
                "audio" -> {
                    val mUri = getUri(viewModel, mediaType)
                    CardImage(
                        imageLink = stockImage,
                        height = 220.dp,
                        width = 400.dp,
                        scale = ContentScale.FillBounds,
                        mediaType = mediaType
                    )
                    AudioPlayer(
                        viewModel = viewModel,
                        mediaType = mediaType
                    )
                    ExpandableDetailsCard(
                        content = decodedDescription,
                        color = backgroundColor
                    )
                    DownloadFile(
                        url = mUri,
                        context = context,
                        filename = mUri,
                        mimeType = AUDIO_WAV_MIME_TYPE,
                        subPath = AUDIO_WAV_SUB_PATH
                    )
                    ShareButton(
                        uri = mUri.toUri(),
                        type = AUDIO_WAV_MIME_TYPE
                    )
                }
                "image" -> {
                    val mUri = getUri(viewModel, mediaType)
                    CardImage(
                        imageLink = mUri,
                        height = 400.dp,
                        width = 450.dp,
                        scale = ContentScale.FillBounds,
                        mediaType = mediaType
                    )
                    ExpandableDetailsCard(
                        content = decodedDescription,
                        color = backgroundColor
                    )
                    DownloadFile(
                        url = url,
                        context = context,
                        filename = url,
                        mimeType = IMAGE_JPEG_MIME_TYPE,
                        subPath = IMAGE_JPEG_SUB_PATH
                    )
                    ShareButton(uri = url.toUri(), type = IMAGE_JPEG_MIME_TYPE)
                }
            }
        }
    }
}