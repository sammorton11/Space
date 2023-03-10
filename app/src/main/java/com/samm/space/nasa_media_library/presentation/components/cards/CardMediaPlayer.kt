package com.samm.space.nasa_media_library.presentation.components.cards

import android.util.Log
import android.webkit.URLUtil
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.audio.AudioSink.UnexpectedDiscontinuityException
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.samm.space.nasa_media_library.presentation.view_models.VideoDataViewModel

@Composable
fun CardMediaPlayer(videoViewModel: VideoDataViewModel, uri: String) {

    val context = LocalContext.current
    val state = videoViewModel.state.value.data

    if (state?.isNotBlank() == true) {
        val exoPlayer = ExoPlayer.Builder(LocalContext.current).build()

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
        AndroidView(
            factory = {
                StyledPlayerView(context).apply {
                    player = exoPlayer
                }
            },
            modifier = Modifier
                .aspectRatio(1f)
                .semantics { testTag = "Card Media Player" }
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
