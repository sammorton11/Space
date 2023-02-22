package com.example.space.presentation.nasa_media_library.details_screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.space.presentation.nasa_media_library.library_search_screen.components.cards.CardVideo
import com.example.space.presentation.view_model.VideoDataViewModel

@Composable
fun   DetailsScreenContent(url: String, viewModel: VideoDataViewModel) {

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Details Screen")
        Log.d("Details Screen: URL", url)
        viewModel.getVideoData(url)
        CardVideo(videoUri = url, videoViewModel = viewModel)
    }
}