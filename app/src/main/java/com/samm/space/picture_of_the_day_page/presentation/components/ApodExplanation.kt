package com.samm.space.picture_of_the_day_page.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp

@Composable
fun ApodExplanation(text: String?) {
    SelectionContainer {
        text?.let { explanation ->
            Text(
                text = explanation,
                modifier = Modifier
                    .semantics { testTag = "Apod Description" }
                    .padding(20.dp),
                softWrap = true
            )
        }
    }
}