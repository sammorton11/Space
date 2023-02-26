package com.example.space.presentation.nasa_media_library.library_search_screen.components.other

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.space.R
import com.example.space.presentation.nasa_media_library.library_search_screen.components.cards.fileTypeCheck
import com.example.space.presentation.nasa_media_library.library_search_screen.components.cards.getUrlList
import com.example.space.presentation.view_model.VideoDataViewModel

@Composable
fun AudioPlayer(viewModel: VideoDataViewModel) {
    val state = viewModel.state.value.data

    // Fetching the local context
    val mContext = LocalContext.current
    var uri = ""
    val iconSize = 150.dp

    if (state != null) {
        if (state.isNotEmpty()) {
            val uriList = getUrlList(state)
            uri = fileTypeCheck(uriList)
        }
    }
    Log.d("audioUrlHTTPS", uri)
    val mMediaPlayer = MediaPlayer.create(mContext, uri.toUri())
    val paused = remember { mutableStateOf(true) }

    Row(
        modifier = Modifier.padding(45.dp)
    ) {

        // IconButton for Start Action
        IconButton(
            onClick = {
                paused.value = false
                mMediaPlayer?.start()
            },
        ) {
            if (paused.value) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_play_circle_24),
                    contentDescription = "",
                    Modifier.size(iconSize),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        // IconButton for Pause Action
        IconButton(
            onClick = {
                paused.value = true
                mMediaPlayer?.pause()
            }
        ) {
            if (!paused.value) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_pause_circle_outline_24),
                    contentDescription = "",
                    modifier = Modifier.size(iconSize),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        // IconButton for Pause Action
        IconButton(
            onClick = {
                paused.value = true
                mMediaPlayer?.let {
                    it.seekTo(0)
                    it.pause()
                }
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_restore_25),
                contentDescription = "",
                modifier = Modifier.size(iconSize),
                tint = MaterialTheme.colorScheme.primary
            )

        }
    }

    DisposableEffect(mMediaPlayer) {
        onDispose {
            mMediaPlayer?.stop()
            mMediaPlayer?.release()
        }
    }
}