package com.example.space.presentation.nasa_media_library.components.cards

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardImage(imageLink: String?, height: Dp, width: Dp, scale: ContentScale) {
    Card(
        modifier = Modifier.padding(10.dp),
        shape = AbsoluteRoundedCornerShape(10)
    ) {
        AsyncImage(
            model = imageLink,
            contentDescription = "",
            modifier = Modifier
                .height(height)
                .width(width),
            contentScale = scale,
            colorFilter = ColorFilter.colorMatrix(ColorMatrix())
        )
    }
}