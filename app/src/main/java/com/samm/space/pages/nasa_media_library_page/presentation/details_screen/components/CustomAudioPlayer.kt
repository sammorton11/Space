package com.samm.space.pages.nasa_media_library_page.presentation.details_screen.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samm.space.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAudioPlayer(start: () -> Unit, pause: () -> Unit, restart: () -> Unit) {

    val paused = remember { mutableStateOf(true) }

    val primary = MaterialTheme.colorScheme.primary
    val secondary = MaterialTheme.colorScheme.secondary
    val onPrimary = MaterialTheme.colorScheme.onPrimary
    val onSecondary = MaterialTheme.colorScheme.onSecondary

    Card(modifier = Modifier.wrapContentSize().padding(35.dp)) {
        Canvas(modifier = Modifier
            .width(400.dp)
            .height(300.dp)
            .padding(16.dp)) {

            drawRoundRect(
                color = secondary,
                cornerRadius = CornerRadius(20f, 20f),
                blendMode = BlendMode.ColorBurn
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            horizontalArrangement = Arrangement.End
        ) {

            val iconSize = 100.dp

            // IconButton for Play Action
            IconButton(
                onClick = {
                    paused.value = false
                    start()
                },
                modifier = Modifier.semantics {
                    testTag = "Play Button"
                }
                    .size(iconSize)
            ) {
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
        CustomAudioPlayer({}, {}, {})
    }
}