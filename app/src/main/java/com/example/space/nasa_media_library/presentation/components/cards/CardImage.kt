package com.example.space.nasa_media_library.presentation.components.cards

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.space.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardImage(imageLink: String?, height: Dp? = null, width: Dp? = null, scale: ContentScale, mediaType: String) {
    imageLink?.let { Log.d("Image Link", it) }

    Card(
        modifier = Modifier
            .animateContentSize(
                animationSpec = tween(durationMillis = 1500,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = AbsoluteRoundedCornerShape(10),
    ) {

        when (mediaType) {
            "audio" -> {
                SubcomposeAsyncImage(
                    model = Image(
                        painter = painterResource(id = R.drawable.tipper_space_man),
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Inside,
                    ),
                    contentDescription = "",
                    colorFilter = ColorFilter.colorMatrix(ColorMatrix())
                )
            }
            else -> {
                SubcomposeAsyncImage(
                    model = imageLink,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .sizeIn(minHeight = 125.dp),
                    contentScale = scale,
                    colorFilter = ColorFilter.colorMatrix(ColorMatrix()),
                )
            }
        }
    }
}