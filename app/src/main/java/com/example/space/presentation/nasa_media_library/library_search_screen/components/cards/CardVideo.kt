package com.example.space.presentation.nasa_media_library.library_search_screen.components.cards

import android.util.Log
import android.webkit.URLUtil
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.space.presentation.view_model.VideoDataViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import org.json.JSONArray

// Todo: fix all of this business logic

@Composable
fun CardVideo(videoViewModel: VideoDataViewModel) {
    val context = LocalContext.current
    val state = videoViewModel.state.value.data

    if (state != null) {
        Log.d("STATE - video screen", state)
    }

    if (state?.isNotBlank() == true) {
        val exoPlayer = ExoPlayer.Builder(LocalContext.current)
            .build()
            .also { exoPlayer ->
                var uri = ""

                if (state.isNotEmpty()) {
                    val uriList = getUrlList(state)
                    uri = fileTypeCheck(uriList)
                }

               // val videoUrlHTTPS = uri.replace("http://", "https://")
                Log.d("videoUrlHTTPS", uri)

                if (URLUtil.isValidUrl(uri)) {
                    val mediaItem = MediaItem.Builder()
                        .setUri(uri)
                        .build()
                    exoPlayer.setMediaItem(mediaItem)
                    exoPlayer.playWhenReady = true
                    exoPlayer.prepare()
                }
            }

        DisposableEffect(
            AndroidView(
                factory = {
                    StyledPlayerView(context).apply {
                        player = exoPlayer
                    }
                },
                modifier = Modifier
                    .height(650.dp)
//                    .padding(top = 25.dp)
            )
        ) {
            onDispose { exoPlayer.release() }
        }
    }
}

fun fileTypeCheck(array: ArrayList<String>): String {
    var file = ""
    for (i in 0 until array.size) {
        when {
            array[i].contains("mobile.mp4") -> { file = array[i] }
            array[i].contains(".mp4") -> { file = array[i] }
            array[i].contains(".wav") -> { file = array[i] }
            array[i].contains(".mp4a") -> { file = array[i] }
            array[i].contains(".mp3") -> { file = array[i] }
        }
    }
    file = file.replace("http://", "https://")

    return file
}

fun getUrlList(state: String): ArrayList<String> {
    Log.d("STATE for getUrlLIst", state)
    val jsonArray = JSONArray(state)
    val urls = ArrayList<String>()
    for (i in 0 until jsonArray.length()) {
        val url = jsonArray.getString(i)
        urls.add(url)
    }
    urls.forEach {
        Log.d("Urls:", it)
    }
    return urls
}