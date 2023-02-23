package com.example.space.presentation.nasa_media_library.details_screen

import android.util.Log
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.space.presentation.nasa_media_library.library_search_screen.components.cards.CardImage
import com.example.space.presentation.nasa_media_library.library_search_screen.components.cards.CardVideo
import com.example.space.presentation.nasa_media_library.library_search_screen.components.other.AudioPlayer
import com.example.space.presentation.view_model.VideoDataViewModel
import java.net.URLDecoder
import java.net.URLEncoder
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

                    if(url.contains(".json")){
                        CardVideo(videoViewModel = viewModel)
                        Text(text = decodedDescription)
                    }
                }
                "audio" -> {

                    Log.d("Putting this url into Audio Player Comp", url)
                    AudioPlayer(url, viewModel)
                    Text(text = decodedDescription)
                }
                "image" -> {
                    if (url.contains(".jpg")) {
                        CardImage(imageLink = url, 450.dp, 480.dp)
                        Text(text = decodedDescription)
                    }
                }
            }
            
            
        }
    }
}