package com.samm.space.pages.nasa_media_library_page.presentation.view_models
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExoPlayerViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    companion object {
        private const val KEY_PLAYER_STATE = "player_state"
        private const val KEY_PLAYBACK_POSITION = "playback_position"
    }

    var playerState: Boolean
        get() = savedStateHandle.get(KEY_PLAYER_STATE) ?: false
        set(value) = savedStateHandle.set(KEY_PLAYER_STATE, value)

    var playbackPosition: Long
        get() = savedStateHandle.get(KEY_PLAYBACK_POSITION) ?: 0L
        set(value) = savedStateHandle.set(KEY_PLAYBACK_POSITION, value)

    fun savePlaybackPosition(player: ExoPlayer) {
        playbackPosition = player.currentPosition
    }

    fun restorePlaybackPosition(player: ExoPlayer) {
        player.seekTo(playbackPosition)
    }
}
