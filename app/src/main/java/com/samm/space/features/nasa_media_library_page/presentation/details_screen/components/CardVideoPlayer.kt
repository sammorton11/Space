package com.samm.space.features.nasa_media_library_page.presentation.details_screen.components

import android.util.Log
import android.webkit.URLUtil
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.samm.space.features.nasa_media_library_page.presentation.view_models.ExoPlayerViewModel

@Composable
fun CardVideoPlayer(
    state: String?,
    uri: String,
    aspectRatio: Float
) {

    val context = LocalContext.current
    val viewModel = viewModel<ExoPlayerViewModel>()

    if (!state.isNullOrBlank() && URLUtil.isValidUrl(uri)) {
        val exoPlayer = ExoPlayer.Builder(context).build()

        LaunchedEffect(exoPlayer) {
            try {
                val mediaItem = MediaItem.Builder()
                    .setUri(uri)
                    .build()
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                viewModel.restorePlaybackPosition(exoPlayer)
                exoPlayer.playWhenReady = viewModel.playerState

            } catch (e: Exception) {
                Log.d("ExoPlayer Error", exoPlayer.playerError.toString())
            }
        }

        AndroidView(
            factory = {
                StyledPlayerView(context).apply {
                    player = exoPlayer
                }
            },
            modifier = Modifier
                .aspectRatio(aspectRatio)
                .semantics { testTag = "Card Media Player" }
        )

        DisposableEffect(exoPlayer) {
            onDispose {
                viewModel.savePlaybackPosition(exoPlayer)
                viewModel.playerState = exoPlayer.playWhenReady
                exoPlayer.stop()
                exoPlayer.release()
            }
        }
    }
}
