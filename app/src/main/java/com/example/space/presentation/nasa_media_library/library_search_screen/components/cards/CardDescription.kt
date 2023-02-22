package com.example.space.presentation.nasa_media_library.library_search_screen.components.cards

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CardDescription(description: String?) {
    description?.let { text ->
        Text(
            text = text,
            modifier = Modifier.padding(5.dp),
            softWrap = true,
            maxLines = 5
        )
    }
}