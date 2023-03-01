package com.example.space.nasa_media_library.presentation.details_screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.space.nasa_media_library.presentation.components.cards.AudioPlayer
import com.example.space.nasa_media_library.presentation.components.cards.CardDescription
import com.example.space.nasa_media_library.presentation.components.cards.CardImage
import com.example.space.nasa_media_library.presentation.components.cards.CardVideo
import com.example.space.nasa_media_library.presentation.view_models.VideoDataViewModel
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
    LazyColumn (
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Log.d("Details Screen: URL", url)
        viewModel.getVideoData(url)

        item {
            when (mediaType) {
                "video" -> {
                    CardVideo(videoViewModel = viewModel, mediaType = mediaType)
                    CardDescription(decodedDescription)
                }
                "audio" -> {
                    AudioPlayer(viewModel = viewModel, mediaType = mediaType)
                    CardDescription(decodedDescription)
                }
                "image" -> {
                    if (url.contains(".jpg")) {
                        CardImage(imageLink = url, 300.dp, 480.dp, ContentScale.Fit)
                        CardDescription(decodedDescription)
                    }
                }
            }
        }
    }
}