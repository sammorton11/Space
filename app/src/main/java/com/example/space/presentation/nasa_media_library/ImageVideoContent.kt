package com.example.space.presentation.nasa_media_library

import android.net.Uri
import android.util.Log
import android.webkit.URLUtil
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import com.example.space.domain.models.Metadata
import com.example.space.presentation.view_model.NasaLibraryViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import retrofit2.Response

@Composable
fun ImageVideoContent(viewModel: NasaLibraryViewModel) {
    val state = viewModel.state
    Column {
        Title("Image Video Library", 15.dp)
        SearchField(onSearch = { query ->
            viewModel.getData(query)
        })

        if (state.value.error.isNotBlank()) {
            // Error Text Component
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
        if (state.value.isLoading) {
            // Progress Bar Component
            CircularProgressIndicator(modifier = Modifier
                .semantics {
                    contentDescription = "Progress Bar"
                    testTag = "Progress Bar Tag"
                })
        }

        // List Component - Image, Title, Description Components
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.value.data) { item ->
                val links = item.links
                val data = item.data.first()
                val title = item.data.first().title
                val description = item.data.first().description
                var isImage = false

                Column(modifier = Modifier.padding(10.dp)) {
                    var videoLink: String? = null
                    var imageLink: String? = null
                    for (link in links) {
                        when (item.data.first().media_type) {
                            "image" -> {
                                isImage = true
                                imageLink = link.href
                            }
                            "video" -> {
//                                if (link.href?.endsWith(".mp4") == true) {
//                                    videoLink = getVideo(viewModel, link.href)
//                                }

                                item.href?.let { url ->
                                    runBlocking {
                                        videoLink = getVideo(viewModel, url).body()
                                    }
                                }
//                                videoLink = link.href?.let { getVideo(viewModel, it) }
//                                if (videoLink != null) {
//                                    MyVideo(videoUri = videoLink)
//                                }
                            }
                        }
                    }

                    if (isImage) {
                        AsyncImage(
                            model = imageLink,
                            contentDescription = "",
                            modifier = Modifier.padding(25.dp)
                        )
                    } else if (videoLink != null){
                        MyVideo(videoUri = videoLink.toString())
                    }
//                    if (videoLink != null) {
//                        MyVideo(videoUri = videoLink)
//                    }
//
//                    if (imageLink != null) {
//                        AsyncImage(
//                            model = imageLink,
//                            contentDescription = "",
//                            modifier = Modifier.padding(25.dp)
//                        )
//                    }

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
            }
        }
    }
}
@Composable
fun MyVideo(videoUri: String) {
    val context = LocalContext.current

    val exoPlayer = ExoPlayer.Builder(LocalContext.current)
        .build()
        .also { exoPlayer ->
            val uri = "https://images-assets.nasa.gov/video/Space-Exploration-Video-1/Space-Exploration-Video-1~mobile.mp4"
            val extractorsFactory: ExtractorsFactory = DefaultExtractorsFactory()
            //val videoUrl = videoUri.replace("http://", "https://")

            var mobileVideo: String = ""
            val jsonArray = JSONArray(videoUri)
            val urls = ArrayList<String>()
            for (i in 0 until jsonArray.length()) {
                val url = jsonArray.getString(i)
                urls.add(url)
            }
            urls.forEach {
                Log.d("Urls:", it)
            }

            for (i in 0 until urls.size) {
                when {
                    urls[i].contains("~mobile.mp4") -> {
                        mobileVideo = urls[i]
                    }
                }
            }

            val videoUrlHTTPS = mobileVideo.replace("http://", "https://")
            Log.d("videoUrlHTTPS", videoUrlHTTPS)
            if (URLUtil.isValidUrl(uri)) {
                val mediaItem = MediaItem.Builder()
                    .setUri(videoUrlHTTPS)
                    .build()
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
            }
        }

    DisposableEffect(
        AndroidView(factory = {
            StyledPlayerView(context).apply {
                player = exoPlayer
            }
        },
        modifier = Modifier.height(500.dp))
    ) {
        onDispose { exoPlayer.release() }
    }

//    // Fetching the Local Context
//    val mContext = LocalContext.current
//
//    // Declaring a string value
//    // that stores raw video url
//    val mVideoUrl = "http://images-assets.nasa.gov/video/Space-Exploration-Video-1/Space-Exploration-Video-1~mobile.mp4"
//
//    // Declaring ExoPlayer
//    val mExoPlayer = remember(mContext) {
//
//        val mediaItem = MediaItem.Builder().setUri(videoUri)
//
//        ExoPlayer.Builder(mContext).build().apply {
//            val dataSourceFactory = DefaultDataSourceFactory(mContext, Util.getUserAgent(mContext, mContext.packageName))
//            val source = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem.build())
//            this.setMediaSource(source)
//            this.prepare()
//        }
//    }
//
////    // Implementing ExoPlayer
////    AndroidView(factory = { context ->
////        PlayerView(context).apply {
////            player = mExoPlayer
////        }
////    })
//
//        DisposableEffect(
//        AndroidView(factory = {
//            StyledPlayerView(context).apply {
//                player = mExoPlayer
//            }
//        },
//        modifier = Modifier.height(500.dp))
//    ) {
//        onDispose { mExoPlayer.release() }
//    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchField(onSearch: (query: String) -> Unit) {
    var query by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = query,
        onValueChange = { query = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        label = { Text("Search") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            onSearch(query)
            keyboardController?.hide()
        }),
    )
}

@Composable
fun Title(text: String, paddingValue: Dp) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValue)
    )
}

suspend fun getVideo(viewModel: NasaLibraryViewModel, url: String): Response<String> {
    return viewModel.getVideoData(url)
}

data class VideoData(
    var captions: String? = null,
    var mobile: String? = null,
    var imagePreview: String? = null,
    var original: String? = null,
)