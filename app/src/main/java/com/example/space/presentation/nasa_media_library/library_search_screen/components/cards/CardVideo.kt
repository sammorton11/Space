package com.example.space.presentation.nasa_media_library.library_search_screen.components.cards

import android.util.Log
import android.webkit.URLUtil
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.space.presentation.view_model.VideoDataViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import org.json.JSONArray

// Todo: fix all of this business logic

@Composable
fun CardVideo(videoUri: String, videoViewModel: VideoDataViewModel) {
    val context = LocalContext.current
    Log.d("Video URI json link?:", videoUri)
    val state = videoViewModel.state.value.data


    Log.d("STATE - video screen", state)

    if (state.isNotBlank()) {
        val exoPlayer = ExoPlayer.Builder(LocalContext.current)
            .build()
            .also { exoPlayer ->
                var mobileVideo = ""
                var preview = ""
                Log.d("Video Uri new:", state)
                if (state.isNotEmpty()) {
                    val jsonArray = JSONArray(state)
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
                            urls[i].contains("mobile.mp4") ||
                                    urls[i].contains(".mp4") -> { mobileVideo = urls[i] }
                            urls[i].contains("orig.mp4") -> {
                                mobileVideo = urls[i]
                            }
                            urls[i].contains("~preview_thumb_00001.png") -> {
                                preview = urls[i]
                            }
                        }
                    }
                }

                val videoUrlHTTPS = mobileVideo.replace("http://", "https://")
                Log.d("videoUrlHTTPS", videoUrlHTTPS)

                if (URLUtil.isValidUrl(videoUrlHTTPS)) {
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
            })
        ) {
            onDispose { exoPlayer.release() }
        }
    }
}