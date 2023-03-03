package com.example.space.nasa_media_library.presentation.components.cards

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardImage(imageLink: String?, height: Dp, width: Dp, scale: ContentScale, mediaType: String) {

    imageLink?.let { Log.d("Image Link", it) }

    Card(
        modifier = Modifier
            .animateContentSize(
                animationSpec = tween(durationMillis = 2500,
                    easing = LinearOutSlowInEasing
                )
            )
            .padding(10.dp),
        shape = AbsoluteRoundedCornerShape(10),
    ) {

        when {
            mediaType == "audio" -> {
                SubcomposeAsyncImage(
                    model = Image(
                        painter = painterResource(id = com.google.android.exoplayer2.R.drawable.exo_styled_controls_play),
                        contentDescription = "",
                        modifier = Modifier.height(80.dp).width(80.dp).padding(top = 10.dp),
                        contentScale = scale,
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
                        .height(height)
                        .width(width),
                    contentScale = scale,
                    colorFilter = ColorFilter.colorMatrix(ColorMatrix()),
                )
            }
        }
    }
}