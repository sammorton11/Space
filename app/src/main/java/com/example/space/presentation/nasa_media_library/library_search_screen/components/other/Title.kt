package com.example.space.presentation.nasa_media_library.library_search_screen.components.other

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Title(text: String, paddingValue: Dp) {
//    Text(
//        text = text,
//        style = MaterialTheme.typography.titleMedium,
//        fontWeight = FontWeight.Bold,
//        textAlign = TextAlign.Start,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(paddingValue)
//    )
    Text(
        text = text,
        fontSize = 24.sp,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValue),
        color = MaterialTheme.colorScheme.primary
    )
}