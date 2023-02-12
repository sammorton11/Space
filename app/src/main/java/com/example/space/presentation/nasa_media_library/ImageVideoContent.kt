package com.example.space.presentation.nasa_media_library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.example.space.presentation.view_model.NasaLibraryViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView

@Composable
fun ImageVideoContent(viewModel: NasaLibraryViewModel) {
    val state = viewModel.state
    Column() {
//        Button(onClick = {
//            viewModel.getData("galaxy") // fake search - todo: build search field with search action
//        }) {
//            Text(text = "Get Data")
//        }
        SearchField(onSearch = { query ->
            viewModel.getData(query)
        })

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

        LazyColumn(
            //columns = GridCells.Adaptive(minSize = 128.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.value.data) { item ->
                //val mediaType = state.value.data.first().data.first().media_type
                val itemMedia = item.links.first().href
                val title = item.data.first().title
                val description = item.data.first().description

                Column(modifier = Modifier.padding(10.dp)) {

                    AsyncImage(
                        model = itemMedia,
                        contentDescription = "",
                        modifier = Modifier.padding(25.dp)
                    )

                    title?.let { text ->
                        Text(
                            text = text,
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                    description?.let { text ->
                        Text(
                            text = text,
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }


//                if (mediaType == "video"){
//                    VideoView(videoUri = item.links.first().href)
//                }
//                if (mediaType == "image"){
//                    AsyncImage(
//                        model = item.links.first().href,
//                        contentDescription = "",
//                        modifier = Modifier.padding(25.dp)
//                    )
//                }
//                itemMedia?.let { uri ->
//                    AsyncImage(
//                        model = uri,
//                        contentDescription = "",
//                        modifier = Modifier.padding(25.dp)
//                    )
//                }
            }
        }
    }

}

@Composable
fun VideoView(videoUri: String) {
    val context = LocalContext.current

    val exoPlayer = ExoPlayer.Builder(LocalContext.current)
        .build()
        .also { exoPlayer ->
            val mediaItem = MediaItem.Builder()
                .setUri(videoUri)
                .build()
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
        }

    DisposableEffect(
        AndroidView(factory = {
            StyledPlayerView(context).apply {
                player = exoPlayer
            }
        })
    ) {
        onDispose { exoPlayer.release() }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchField(onSearch: (query: String) -> Unit) {
    var query by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = query,
        onValueChange = { query = it },
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Search") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            onSearch(query)
            keyboardController?.hide()
        }),
    )
}