package com.example.space.nasa_media_library.presentation.details_screen

import android.util.Log
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
import com.example.space.nasa_media_library.presentation.components.cards.*
import com.example.space.nasa_media_library.presentation.view_models.VideoDataViewModel
import com.example.space.presentation.ProgressBar
import com.example.space.presentation.ShareButton
import com.example.space.presentation.util.DownloadFile

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
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Log.d("URL passed to details composable", url)
        viewModel.getVideoData(url)

        item {
            if (viewModel.state.value.isLoading) { ProgressBar() }
            when (mediaType) {
                "video" -> {

                    val mUri = getUri(viewModel, mediaType)
                    Log.d("URL into CardMediaPlayer details screen", mUri)
                    CardMediaPlayer(videoViewModel = viewModel, uri = mUri)
                    ExpandableDetailsCard(content = decodedDescription, color = backgroundColor)
                    DownloadFile(
                        url = mUri,
                        context = context,
                        filename = mUri,
                        mimeType = "video/mp4",
                        subPath = "video.mp4"
                    )
                    ShareButton(uri = mUri.toUri(), type = "video/mp4")
                }
                "audio" -> {
                    val mUri = getUri(viewModel, mediaType)
                    val image = "https://images-assets.nasa.gov/image/ARC-2003-AC03-0036-9/ARC-2003-AC03-0036-9~thumb.jpg"
                    CardImage(
                        imageLink = image,
                        height = 220.dp,
                        width = 400.dp,
                        scale = ContentScale.FillBounds,
                        mediaType = mediaType
                    )
                    AudioPlayer(viewModel = viewModel, mediaType = mediaType)
                    ExpandableDetailsCard(content = decodedDescription, color = backgroundColor)
                    DownloadFile(
                        url = mUri,
                        context = context,
                        filename = mUri,
                        mimeType = "audio/x-wav",
                        subPath = "audio.wav"
                    )
                    ShareButton(uri = mUri.toUri(), type = "audio/x-wav")
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
                    ExpandableDetailsCard(content = decodedDescription, color = backgroundColor)
                    DownloadFile(
                        url = url,
                        context = context,
                        filename = url,
                        mimeType = "image/jpeg",
                        subPath = "image.jpeg"
                    )
                    ShareButton(uri = url.toUri(), type = "image/jpeg")
                }
            }
        }
    }
}