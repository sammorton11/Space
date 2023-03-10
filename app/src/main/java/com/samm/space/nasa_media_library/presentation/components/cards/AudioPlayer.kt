package com.samm.space.nasa_media_library.presentation.components.cards

import android.media.MediaPlayer
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
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.samm.space.R
import com.samm.space.core.MediaType
import com.samm.space.nasa_media_library.presentation.view_models.VideoDataViewModel
import com.samm.space.nasa_media_library.util.ViewUtils

@Composable
fun AudioPlayer(viewModel: VideoDataViewModel, mediaType: MediaType) {

    val jsonArrayAsString = viewModel.state.value.data
    val mContext = LocalContext.current
    var uri = ""
    val iconSize = 150.dp
    val utils = ViewUtils()

    jsonArrayAsString?.let {
        if (it.isNotEmpty()) {
            val uriList = utils.extractUrlsFromJsonArray(jsonArrayAsString)
            uri = utils.fileTypeCheck(uriList, mediaType)
        }
    }
    val mMediaPlayer = MediaPlayer.create(mContext, uri.toUri())
    val paused = remember { mutableStateOf(true) }

    Row(modifier = Modifier.padding(15.dp)) {
        // IconButton for Play Action
        IconButton(
            onClick = {
                paused.value = false
                mMediaPlayer?.start()
            },
            modifier = Modifier.semantics {
                testTag = "Play Button"
            }
        ) {
            if (paused.value) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_play_circle_24),
                    contentDescription = "",
                    modifier = Modifier
                        .size(iconSize)
                        .semantics {
                            testTag = "Play Button Icon"
                        },
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        // IconButton for Pause Action
        IconButton(
            onClick = {
                paused.value = true
                mMediaPlayer?.pause()
            },
            modifier = Modifier.semantics {
                testTag = "Pause Button"
            }
        ) {
            if (!paused.value) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_pause_circle_outline_24),
                    contentDescription = "",
                    modifier = Modifier
                        .size(iconSize)
                        .semantics {
                            testTag = "Pause Button Icon"
                        },
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        // IconButton for Restart Action
        IconButton(
            onClick = {
                paused.value = true
                mMediaPlayer?.let {
                    it.seekTo(0)
                    it.pause()
                }
            },
            modifier = Modifier.semantics {
                testTag = "Restart Button"
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_restore_25),
                contentDescription = "",
                modifier = Modifier
                    .size(iconSize)
                    .semantics {
                        testTag = "Restart Button Icon"
                    },
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