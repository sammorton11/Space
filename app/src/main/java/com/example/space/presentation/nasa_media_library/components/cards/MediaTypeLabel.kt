package com.example.space.presentation.nasa_media_library.components.cards

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MediaTypeLabel(mediaType: String?, color: Color) {
    if (mediaType != null) {
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = mediaType,
            color = color
        )
    }
}