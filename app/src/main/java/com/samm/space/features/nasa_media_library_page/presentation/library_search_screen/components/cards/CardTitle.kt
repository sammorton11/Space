package com.samm.space.features.nasa_media_library_page.presentation.library_search_screen.components.cards

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardTitle(title: String?) {
    title?.let { text ->
        Text(
            text = text,
            fontSize = 13.sp,
            fontWeight = Bold,
            modifier = Modifier
                .padding(start = 10.dp, bottom = 5.dp)
                .semantics { testTag = "Card Title $title" },
            color = MaterialTheme.colorScheme.secondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            softWrap = true,
            textAlign = TextAlign.Center
        )
    }
}