package com.example.space.presentation.nasa_media_library.details_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.space.presentation.nasa_media_library.library_search_screen.components.cards.CardVideo
import com.example.space.presentation.navigation.CardData
import com.example.space.presentation.view_model.VideoDataViewModel

@Composable
fun   DetailsScreenContent(data: CardData, viewModel: VideoDataViewModel) {

    Column(modifier = Modifier.fillMaxSize()) {
        viewModel.getVideoData(data.url)
        CardVideo(videoUri = data.url, videoViewModel = viewModel)
    }
}