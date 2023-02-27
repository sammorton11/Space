package com.example.space.presentation.nasa_media_library.components.cards

import android.util.Log
import android.webkit.URLUtil
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.space.presentation.nasa_media_library.view_models.VideoDataViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.audio.AudioSink.UnexpectedDiscontinuityException
import com.google.android.exoplayer2.ui.StyledPlayerView

// Todo: fix all of this business logic

@Composable
fun CardVideo(videoViewModel: VideoDataViewModel, mediaType: String) {
    val context = LocalContext.current

    val state = videoViewModel.state.value.data

    if (state != null) {
        Log.d("STATE - video screen", state)
    }

    if (state?.isNotBlank() == true) {
        val exoPlayer = ExoPlayer.Builder(LocalContext.current).build()

        var uri = ""
        if (state.isNotEmpty()) {
            val uriList = videoViewModel.getUrlList(state)
            uri = videoViewModel.fileTypeCheck(uriList, mediaType)
        }

        Log.d("videoUrlHTTPS", uri)

        if (URLUtil.isValidUrl(uri)) {
            try {
                val mediaItem = MediaItem.Builder()
                    .setUri(uri)
                    .build()

                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.playWhenReady = true
                exoPlayer.prepare()
            } catch (e: UnexpectedDiscontinuityException) {
                Log.d("ExoPlayer Error", exoPlayer.playerError.toString())
                Log.d("AudioSink Error", e.toString())
            }
        }

        val exoPlayerView = AndroidView(
            factory = {
                StyledPlayerView(context).apply {
                    player = exoPlayer
                }
            },
            modifier = Modifier.height(650.dp)
        )

        DisposableEffect(exoPlayer) {
            onDispose {
                exoPlayer.stop()
                exoPlayer.release()
                Log.d("Is exoplayer Playing?", exoPlayer.isPlaying.toString())
            }
        }
    }

}
