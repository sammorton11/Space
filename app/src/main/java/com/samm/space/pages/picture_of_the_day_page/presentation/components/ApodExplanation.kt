package com.samm.space.pages.picture_of_the_day_page.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun ApodExplanation(text: String?) {

    var expanded by remember { mutableStateOf(false) }

    SelectionContainer {
        text?.let { explanation ->
            Text(
                text = explanation,
                modifier = Modifier
                    .semantics { testTag = "Apod Description" }
                    .padding(20.dp)
                    .animateContentSize()
                    .clickable {
                        expanded = !expanded
                    },
                overflow = TextOverflow.Ellipsis,
                maxLines = if (!expanded) 15 else 50,
                softWrap = true
            )
        }
    }
}