package com.samm.space.features.nasa_media_library_page.presentation.details_screen.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun DescriptionText(content: String) {

    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .semantics { testTag = "Expandable Details Card" }
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CompositionLocalProvider {
            SelectionContainer {
                Text(
                    text = content,
                    modifier = Modifier
                        .semantics { testTag = "Details Text" }
                        .padding(top = 8.dp)
                        .animateContentSize()
                        .clickable {
                            isExpanded = !isExpanded
                        },
                    overflow = TextOverflow.Ellipsis,
                    softWrap = true,
                    maxLines = if (!isExpanded) 5 else 1000
                )
            }
        }
    }
}
