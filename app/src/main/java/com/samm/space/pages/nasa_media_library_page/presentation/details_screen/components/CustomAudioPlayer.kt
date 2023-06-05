
import android.media.MediaPlayer
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.SubcomposeAsyncImage
import com.samm.space.R
import com.samm.space.common.presentation.ProgressBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.PI
import kotlin.math.sin
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAudioPlayer(
    audioPlayerUri: String?
) {
    val primary = MaterialTheme.colorScheme.primary
    val secondary = MaterialTheme.colorScheme.secondary
    val coroutineScope = rememberCoroutineScope()
    val mContext = LocalContext.current
    val mMediaPlayer = remember { MediaPlayer() }
    var animationJob: Job? = null

    var paused by remember { mutableStateOf(true) }
    var progress by remember { mutableStateOf(0f) }
    var position by remember { mutableStateOf(mMediaPlayer.currentPosition.milliseconds) }

    val waveAmplitudeState = remember { MutableTransitionState(0f) }

    val animationSpec = remember {
        infiniteRepeatable<Float>(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    }

    val waveAmplitude by animateFloatAsState(
        targetValue = waveAmplitudeState.targetState,
        animationSpec = animationSpec,
        label = ""
    )

    val waveFrequency = 2 * PI.toFloat() / 100

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
            SubcomposeAsyncImage(
                model = R.drawable.earth_from_moon,
                contentDescription = "",
                modifier = Modifier
                    .sizeIn(
                        minHeight = 300.dp,
                        minWidth = 125.dp,
                    )
                    .fillMaxSize()
                    .semantics {
                        testTag = "Details Image - ID"
                    },
                contentScale = ContentScale.FillBounds,
                colorFilter = ColorFilter.colorMatrix(ColorMatrix()),
                loading = {
                    ProgressBar()
                },
                alpha = 0.4f
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 65.dp)
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {


                    val centerY = size.height / 2
                    val waveHeight = size.height / 2
                    val startX = (waveAmplitude * 6 * PI).toFloat() // Adjust the multiplier as needed for the desired wave length
                    val endX = (waveAmplitude * 2 * PI).toFloat()
                    val stepX = (endX - startX) / 400

                    for (i in 0..100) {
                        val x = startX + i * stepX
                        val y = waveHeight + waveAmplitude * sin(waveFrequency * x)

                        drawLine(
                            color = primary,
                            start = Offset(x, centerY),
                            end = Offset(x, y),
                            strokeWidth = 5f,
                            cap = StrokeCap.Round,
                            alpha = 1f
                        )
                    }
                }

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

                Text(
                    text = "$position / ${mMediaPlayer.duration.milliseconds}",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(15.dp)
                ) {
                    IconButton(
                        onClick = {
                            // Seek the media player based on the progress value
                            val seekToMs = (mMediaPlayer.duration * progress).toInt()
                            mMediaPlayer.seekTo(seekToMs)

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

    DisposableEffect(Unit) {
        onDispose {
            animationJob?.cancel()
            mMediaPlayer.stop()
            mMediaPlayer.release()
        }
    }

    LaunchedEffect(animationJob) {
        animationJob = coroutineScope.launch {
            waveAmplitudeState.targetState = 60f
        }
    }

    LaunchedEffect(paused) {
        while (!paused) {
            val currentProgress = mMediaPlayer.currentPosition.toFloat() / mMediaPlayer.duration.toFloat()
            progress = currentProgress
            position = mMediaPlayer.currentPosition.milliseconds
            delay(100) // Adjust the delay duration as needed
        }
    }

    LaunchedEffect(audioPlayerUri) {
        val uri = audioPlayerUri?.toUri()
        if (uri != null) {
            coroutineScope.launch {
                withContext(Dispatchers.IO) {
                    try {
                        mMediaPlayer.reset()
                        mMediaPlayer.setDataSource(mContext, uri)
                        mMediaPlayer.prepareAsync()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CustomAudioPlayerPreview() {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomAudioPlayer(audioPlayerUri = "")
    }
}
