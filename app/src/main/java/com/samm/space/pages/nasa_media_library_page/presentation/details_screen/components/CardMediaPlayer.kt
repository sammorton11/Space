package com.samm.space.pages.nasa_media_library_page.presentation.details_screen.components

import android.util.Log
import android.webkit.URLUtil
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView

@Composable
fun CardMediaPlayer(
    state: String?,
    uri: String,
    aspectRatio: Float
) {

    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val exoPlayer = ExoPlayer.Builder(context).build()


    require(!state.isNullOrBlank())
    require(URLUtil.isValidUrl(uri))
    // Todo: No ^^^^^^

    LaunchedEffect(exoPlayer) {
        try {
            val mediaItem = MediaItem.Builder()
                .setUri(uri)
                .build()
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()

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
            exoPlayer.stop()
            exoPlayer.release()
        }
    }
}
