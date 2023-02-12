package com.example.space.presentation.nasa_media_library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.space.presentation.view_model.NasaLibraryViewModel

@Composable
fun ImageVideoContent(viewModel: NasaLibraryViewModel) {
    val state = viewModel.state
    Column() {
        Button(onClick = {
            viewModel.getData("video") // fake search - todo: build search field with search action
        }) {
            Text(text = "Get Data")
        }
        if(state.value.error.isNotBlank()) {
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
        if(state.value.isLoading) {
            CircularProgressIndicator(modifier = Modifier
                .semantics {
                    contentDescription = "Progress Bar"
                    testTag = "Progress Bar Tag"
                })
        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.value.data) { item ->
                item.data?.first()?.title?.let {
                    Text(
                        text = it,
                        modifier = Modifier.padding(5.dp)
                    )
                }
                AsyncImage(
                    model = item.links.first().href,
                    contentDescription = "",
                    modifier = Modifier.padding(25.dp)
                )
            }
        }
    }

}