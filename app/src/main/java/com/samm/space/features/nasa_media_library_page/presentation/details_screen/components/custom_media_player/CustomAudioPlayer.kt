
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import android.webkit.URLUtil
import androidx.compose.animation.core.RepeatMode
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.samm.space.R
import com.samm.space.features.nasa_media_library_page.presentation.details_screen.components.custom_media_player.CustomAudioPlayerImage
import com.samm.space.features.nasa_media_library_page.presentation.details_screen.components.custom_media_player.DurationLabel
import com.samm.space.features.nasa_media_library_page.presentation.details_screen.components.custom_media_player.SineWaveAnimation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@SuppressLint("SourceLockedOrientationActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAudioPlayer(
    audioPlayerUri: String?
) {
    // Locking the orientation temporarily until I find a fix
    val activity = LocalContext.current as? Activity
    activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED

    val primary = MaterialTheme.colorScheme.primary
    val secondary = MaterialTheme.colorScheme.secondary
    val mContext = LocalContext.current

    val coroutineScope = rememberCoroutineScope()
    val mMediaPlayer = remember { MediaPlayer() }
    var paused by remember { mutableStateOf(true) }
    var progress by remember { mutableStateOf(0f) }
    var position by remember { mutableStateOf(mMediaPlayer.currentPosition.milliseconds) }



    Card(
        modifier = Modifier
            .padding(25.dp)
            .sizeIn(maxWidth = 475.dp)
            .fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.CenterHorizontally)
        ) {
            CustomAudioPlayerImage()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 65.dp)
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                SineWaveAnimation(color = primary, repeatMode = RepeatMode.Reverse)

                Slider(
                    value = progress,
                    onValueChange = { newProgress ->
                        progress = newProgress
                        val seekToMs = (mMediaPlayer.duration.toFloat() * progress).toInt()
                        mMediaPlayer.seekTo(seekToMs)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 15.dp)
                )

                DurationLabel(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    position = position,
                    mMediaPlayer = mMediaPlayer
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(15.dp)
                ) {

                    // Play/Pause Button
                    IconButton(
                        onClick = {
                            paused = !paused

                            if (paused) {
                                mMediaPlayer.pause()
                            } else {
                                mMediaPlayer.start()
                            }
                        },
                        modifier = Modifier
                            .size(100.dp)
                            .semantics {
                                testTag = "Play/Pause Button"
                            }
                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (paused) R.drawable.baseline_play_circle_24
                                else R.drawable.baseline_pause_circle_outline_24
                            ),
                            contentDescription = "",
                            modifier = Modifier
                                .size(100.dp)
                                .semantics {
                                    testTag = "Play/Pause Button Icon"
                                },
                            tint = secondary
                        )
                    }
                }
            }
        }
    }

    DisposableEffect(paused) {

        val job = coroutineScope.launch {
            while (!paused) {
                position = mMediaPlayer.currentPosition.milliseconds
                delay(100)
            }
        }
        job.start()
        onDispose {
            job.cancel()
        }
    }

    LaunchedEffect(audioPlayerUri) {
        val uri = audioPlayerUri?.toUri()
        if (uri != null && URLUtil.isValidUrl(audioPlayerUri)) {
            mMediaPlayer.reset()
            mMediaPlayer.setDataSource(mContext, uri)
            mMediaPlayer.prepareAsync()
        }
    }

    DisposableEffect(mMediaPlayer) {
        onDispose {
            mMediaPlayer.reset()
            mMediaPlayer.stop()
            mMediaPlayer.release()
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }
}

@Preview
@Composable
fun CustomAudioPlayerPreview() {
    LazyColumn(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxSize()
            .semantics { testTag = "Details Screen" },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            CustomAudioPlayer(
                audioPlayerUri = "https://images-assets.nasa.gov/video/ksc_121304_comet_history/ksc_121304_comet_history~orig.mp4IO"
            )
        }
    }
}