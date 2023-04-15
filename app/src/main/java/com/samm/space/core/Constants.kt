package com.samm.space.core

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.nio.charset.StandardCharsets

object Constants {

    const val BASE_URL_MARS_DATA = "https://api.nasa.gov/"
    const val API_KEY = "qGqHQYIgYmjxAKCaFJHaN9I3XTvpbHQHS8N7yMNO"
    const val NO_BACKGROUND = 0

    val utf8Encoding = StandardCharsets.UTF_8.toString()
    var BASE_URL = "https://images-api.nasa.gov/"
    val mimeTypeForDownload = MediaDownloadType.IMAGE_JPEG.mimeType
    val subPathForDownload = MediaDownloadType.IMAGE_JPEG.subPath
    val buttonWidth = 150.dp

    val cubicAnimation = Modifier.animateContentSize(
        animationSpec = tween(durationMillis = 1500,
            easing = CubicBezierEasing(5f, 10f, 5f, 10f)
        )
    )
    val fastInAnimation = Modifier.animateContentSize(
        animationSpec = tween(durationMillis = 1500,
            easing = FastOutSlowInEasing
        )
    )
}