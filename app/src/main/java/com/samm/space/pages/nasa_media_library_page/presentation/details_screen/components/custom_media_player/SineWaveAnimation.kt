package com.samm.space.pages.nasa_media_library_page.presentation.details_screen.components.custom_media_player

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.sin

@Composable
fun SineWaveAnimation(color: Color, repeatMode: RepeatMode) {
    val coroutineScope = rememberCoroutineScope()
    val waveAmplitudeState = remember { MutableTransitionState(0f) }

    val animationSpec = remember {
        infiniteRepeatable<Float>(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = repeatMode
        )
    }

    val waveAmplitude by animateFloatAsState(
        targetValue = waveAmplitudeState.targetState,
        animationSpec = animationSpec,
        label = ""
    )

    val waveFrequency = 2 * PI.toFloat() / 100

    var animationJob: Job? = null

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
                color = color,
                start = Offset(x, centerY),
                end = Offset(x, y),
                strokeWidth = 5f,
                cap = StrokeCap.Round,
                alpha = 1f
            )
        }
    }

    LaunchedEffect(animationJob) {
        animationJob = coroutineScope.launch {
            waveAmplitudeState.targetState = 60f
        }
    }

    DisposableEffect(animationJob) {
        onDispose {
            animationJob?.cancel()
        }
    }
}