package com.example.space.nasa_media_library.presentation.details_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.space.nasa_media_library.presentation.view_models.VideoDataViewModel
import com.example.space.presentation.ProgressBar

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
            .padding(10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        viewModel.getVideoData(url)

        item {
            if (viewModel.state.value.isLoading) { ProgressBar() }
            when (mediaType) {
                "video" -> {
                    VideoDetails(
                        context = context,
                        viewModel = viewModel,
                        mediaType = mediaType,
                        description = decodedDescription,
                        backgroundColor = backgroundColor
                    )
                }
                "audio" -> {
                    AudioDetails(
                        viewModel = viewModel,
                        mediaType = mediaType,
                        context = context,
                        description = decodedDescription,
                        backgroundColor = backgroundColor
                    )
                }
                "image" -> {
                    ImageDetails(
                        viewModel = viewModel,
                        mediaType = mediaType,
                        context = context,
                        description = decodedDescription,
                        backgroundColor = backgroundColor,
                        url = url
                    )
                }
            }
        }
    }
}