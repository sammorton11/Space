package com.example.space.presentation.nasa_media_library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.space.presentation.view_model.NasaLibraryViewModel

@Composable
fun ImageVideoScreen(viewModel: NasaLibraryViewModel = hiltViewModel()) {
    Column(modifier = Modifier.fillMaxSize()) {
        ImageVideoContent(viewModel = viewModel)
    }
}