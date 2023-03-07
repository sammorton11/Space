package com.example.space.nasa_media_library.presentation.components.cards

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardTitle(title: String?, color: Color) {
    title?.let { text ->
        Text(
            text = text,
            fontSize = 11.sp,
            fontWeight = Bold,
            modifier = Modifier.padding(10.dp),
            color = color,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            softWrap = true,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CardTitlePreview () {
    Card(modifier = Modifier.width(100.dp).height(100.dp)) {
        CardTitle(title = "Title Test Title Test Title Test", color = Color.Unspecified)
    }

}