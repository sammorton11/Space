package com.example.space.nasa_media_library.presentation.details_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.space.nasa_media_library.presentation.components.cards.*
import com.example.space.nasa_media_library.presentation.view_models.VideoDataViewModel
import com.example.space.presentation.DownloadFile
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun DetailsScreenContent(
    url: String, 
    description: String, 
    mediaType: String,
    viewModel: VideoDataViewModel
) {
    val decodedDescription = URLDecoder.decode(description, StandardCharsets.US_ASCII.toString())
    val context = LocalContext.current

    LazyColumn (
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        viewModel.getVideoData(url)

        item {
            when (mediaType) {
                "video" -> {
                    val mUri = getUri(viewModel, mediaType)
                    CardVideo(videoViewModel = viewModel, uri = mUri)
                    CardDescription(decodedDescription)
                    DownloadFile(
                        url = mUri,
                        context = context,
                        filename = mUri,
                        mimeType = "video/mp4",
                        subPath = "video.mp4"
                    )
                }
                "audio" -> {
                    val mUri = getUri(viewModel, mediaType)
                    AudioPlayer(viewModel = viewModel, mediaType = mediaType)
                    CardDescription(decodedDescription)
                    DownloadFile(
                        url = mUri,
                        context = context,
                        filename = mUri,
                        mimeType = "audio/x-wav",
                        subPath = "audio.wav"
                    )
                }
                "image" -> {
                    val mUri = getUri(viewModel, mediaType)
                    if (mUri.contains(".jpg")) {
                        CardImage(imageLink = mUri, 300.dp, 480.dp, ContentScale.Fit)
                        CardDescription(decodedDescription)
                        DownloadFile(
                            url = mUri,
                            context = context,
                            filename = mUri,
                            mimeType = "image/jpeg",
                            subPath = "image.jpeg"
                        )
                    }
                }
            }
        }
    }
}