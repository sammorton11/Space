package com.example.space.presentation.mars_rover.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun PhotoItem(image: String) {
    AsyncImage(
        model = image,
        contentDescription = "Mars Image"
    )
}