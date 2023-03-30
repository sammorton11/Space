package com.samm.space.nasa_media_library.presentation.details_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.samm.space.presentation.buttons.ExpandButton

@Composable
fun ExpandableDetailsCard(content: String, color: Color) {

    var isExpanded = remember { mutableStateOf(false) }
    val lines = content.length
    val lineLimit = 50
    val overLineLimit = lines >= lineLimit

    Column(
        modifier = Modifier
            .semantics { testTag = "Expandable Details Card" }
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CompositionLocalProvider {
            SelectionContainer {
                Text(
                    text = content,
                    maxLines = if (isExpanded.value) Int.MAX_VALUE else lineLimit,
                    softWrap = true,
                    modifier = Modifier
                        .semantics { testTag = "Details Text" }
                        .padding(top = 8.dp),
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        if (!overLineLimit) {
            ExpandButton(isExpanded)
        }
    }
}
