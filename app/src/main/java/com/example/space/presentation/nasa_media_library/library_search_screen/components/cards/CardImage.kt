package com.example.space.presentation.nasa_media_library.library_search_screen.components.cards

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun CardImage(imageLink: String?, height: Dp, width: Dp) {
    AsyncImage(
        model = imageLink,
        contentDescription = "",
        modifier = Modifier
            .height(height)
            .width(width),
        contentScale = ContentScale.FillBounds,
        colorFilter = ColorFilter.colorMatrix(ColorMatrix())
    )
}