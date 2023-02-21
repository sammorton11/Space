package com.example.space.presentation.nasa_media_library.library_search_screen.components.other

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.space.presentation.NasaLibraryState

@Composable
fun ErrorText(state: State<NasaLibraryState>) {
    Text(
        text = state.value.error,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .semantics {
                contentDescription = "Error Text"
                testTag = "Error Tag"
            },
    )
}