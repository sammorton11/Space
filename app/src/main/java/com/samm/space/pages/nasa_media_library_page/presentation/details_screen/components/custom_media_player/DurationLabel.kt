package com.samm.space.pages.nasa_media_library_page.presentation.details_screen.components.custom_media_player

import android.media.MediaPlayer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun DurationLabel(modifier: Modifier, position: Duration, mMediaPlayer: MediaPlayer) {
    //DurationLabel
    Text(
        text = "$position / ${mMediaPlayer.duration.milliseconds}",
        modifier = modifier
    )
}