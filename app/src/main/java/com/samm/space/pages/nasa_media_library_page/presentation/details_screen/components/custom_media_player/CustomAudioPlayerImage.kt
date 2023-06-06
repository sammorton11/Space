package com.samm.space.pages.nasa_media_library_page.presentation.details_screen.components.custom_media_player

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.samm.space.R
import com.samm.space.common.presentation.ProgressBar

@Composable
fun CustomAudioPlayerImage() {
    SubcomposeAsyncImage(
        model = R.drawable.earth_from_moon,
        contentDescription = "",
        modifier = Modifier
            .sizeIn(
                minHeight = 300.dp,
                minWidth = 125.dp,
            )
            .fillMaxSize()
            .semantics {
                testTag = "Details Image - ID"
            },
        contentScale = ContentScale.FillBounds,
        colorFilter = ColorFilter.colorMatrix(ColorMatrix()),
        loading = {
            ProgressBar()
        },
        alpha = 0.4f
    )
}