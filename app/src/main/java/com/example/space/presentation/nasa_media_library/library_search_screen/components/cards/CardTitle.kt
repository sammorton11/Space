package com.example.space.presentation.nasa_media_library.library_search_screen.components.cards

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CardTitle(title: String?, color: Color) {
    title?.let { text ->
        Text(
            text = text,
            modifier = Modifier.padding(5.dp),
            color = color
        )
    }
}