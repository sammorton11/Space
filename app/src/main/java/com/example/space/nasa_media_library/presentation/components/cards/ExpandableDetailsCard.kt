package com.example.space.nasa_media_library.presentation.components.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableDetailsCard(
    content: String,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .clickable { isExpanded = !isExpanded }
                .padding(16.dp)
        ) {

            CompositionLocalProvider() {
                Text(
                    text = content,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 25,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            if (!isExpanded) {
                Text(
                    text = "Read more...",
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}