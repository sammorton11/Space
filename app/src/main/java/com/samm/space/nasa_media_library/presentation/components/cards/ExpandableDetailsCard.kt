package com.samm.space.nasa_media_library.presentation.components.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableDetailsCard(content: String, color: Color) {

    var isExpanded by remember { mutableStateOf(false) }
    val lines = content.lines()
    val lineLimit = 5
    val overLineLimit = lines.size >= lineLimit

    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = color
        )
    ) {

        val modifier = if (overLineLimit) {
            Modifier
                .semantics { testTag = "Expandable Details Card" }
                .padding(16.dp)
        } else {
            Modifier
                .semantics { testTag = "Expandable Details Card - Clickable" }
                .clickable { isExpanded = !isExpanded }
                .padding(16.dp)
        }

        Column(
            modifier = modifier
        ) {

            CompositionLocalProvider {
                Text(
                    text = content,
                    maxLines = if (isExpanded) Int.MAX_VALUE else lineLimit,
                    softWrap = true,
                    modifier = Modifier
                        .semantics { testTag = "Details Text" }
                        .padding(top = 8.dp),
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

    }
}
