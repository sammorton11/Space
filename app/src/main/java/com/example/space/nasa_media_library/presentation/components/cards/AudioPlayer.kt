package com.example.space.nasa_media_library.presentation.components.cards

import android.media.MediaPlayer
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.space.R
import com.example.space.nasa_media_library.presentation.view_models.VideoDataViewModel

@Composable
fun AudioPlayer(viewModel: VideoDataViewModel, mediaType: String) {
    val jsonArrayAsString = viewModel.state.value.data

    // Fetching the local context
    val mContext = LocalContext.current
    var uri = ""
    val iconSize = 150.dp

    jsonArrayAsString?.let {
        if (it.isNotEmpty()) {
            val uriList = viewModel.getUrlList(jsonArrayAsString)
            uri = viewModel.fileTypeCheck(uriList, mediaType)
        }
    }
    val mMediaPlayer = MediaPlayer.create(mContext, uri.toUri())
    val paused = remember { mutableStateOf(true) }
   // var position by remember { mutableStateOf(mMediaPlayer.currentPosition.toFloat()) }

    Row(
        modifier = Modifier.padding(45.dp)
    ) {

//        val cPosition = mMediaPlayer.currentPosition
//        if (cPosition != null) {
//
//        }
//

//        Slider(value = mMediaPlayer.currentPosition.toFloat(), onValueChange = {
//            position = it
//        })

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
//
//                if (mMediaPlayer.isPlaying) {
//                    Text(text = mMediaPlayer)
//                }
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