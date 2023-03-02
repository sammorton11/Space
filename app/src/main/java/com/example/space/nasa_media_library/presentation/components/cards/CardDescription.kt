package com.example.space.nasa_media_library.presentation.components.cards

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
            modifier = Modifier.padding(20.dp),
            softWrap = true
        )
    }
}