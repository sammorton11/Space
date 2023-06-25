package com.samm.space.pages.nasa_media_library_page.presentation.view_models

import android.media.MediaPlayer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MediaPlayerViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val KEY_PLAYER_STATE = "player_state"
        private const val KEY_PLAYBACK_POSITION = "playback_position"
    }

    var playerState: Boolean
        get() = savedStateHandle[KEY_PLAYER_STATE] ?: false
        set(value) = savedStateHandle.set(KEY_PLAYER_STATE, value)

    private var playbackPosition: Long
        get() = savedStateHandle[KEY_PLAYBACK_POSITION] ?: 0L
        set(value) = savedStateHandle.set(KEY_PLAYBACK_POSITION, value)

    fun savePlaybackPosition(player: MediaPlayer) {
        playbackPosition = player.currentPosition.toLong()
    }

    fun restorePlaybackPosition(player: MediaPlayer) {
        player.seekTo(playbackPosition.toInt())
    }
}