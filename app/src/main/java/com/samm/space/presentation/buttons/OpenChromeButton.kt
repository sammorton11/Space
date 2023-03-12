package com.samm.space.presentation.buttons

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.samm.space.nasa_media_library.util.ViewUtils

@Composable
fun OpenChromeButton(context: Context, uri: String) {
    val viewUtil = ViewUtils()
    Button(
        onClick = {
            viewUtil.openWithChrome(uri, context)
        },
        modifier = Modifier.padding(15.dp)
    ) {
        Text(text = "Open in Chrome")
    }
}