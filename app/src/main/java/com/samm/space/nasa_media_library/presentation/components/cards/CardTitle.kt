package com.samm.space.nasa_media_library.presentation.components.cards

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardTitle(title: String?, color: Color) {
    title?.let { text ->
        Text(
            text = text,
            fontSize = 11.sp,
            fontWeight = Bold,
            modifier = Modifier
                .padding(10.dp)
                .semantics { testTag = "Card Title" },
            color = color,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            softWrap = true,
            textAlign = TextAlign.Center
        )
    }
}