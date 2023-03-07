package com.example.space.nasa_media_library.presentation.components.cards

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsImage(imageLink: String? = null, id: Int? = null, scale: ContentScale) {
    Card(
        shape = RoundedCornerShape(10.dp)
    ) {
        imageLink?.let {
            SubcomposeAsyncImage(
                model = imageLink,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .height(250.dp),
                contentScale = scale,
                colorFilter = ColorFilter.colorMatrix(ColorMatrix()),
            )
        }
        id?.let {
            SubcomposeAsyncImage(
                model = id,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .height(250.dp),
                contentScale = scale,
                colorFilter = ColorFilter.colorMatrix(ColorMatrix()),
            )
        }

    }
}