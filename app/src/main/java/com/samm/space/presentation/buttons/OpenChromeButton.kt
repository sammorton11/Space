package com.samm.space.presentation.buttons

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import com.samm.space.core.Constants
import com.samm.space.nasa_media_library.util.ViewUtils

@Composable
fun OpenChromeButton(context: Context, uri: String) {
    val viewUtil = ViewUtils()
    OutlinedButton(
        onClick = {
            viewUtil.openWithChrome(uri, context)
        },
        modifier = Modifier
            .padding(15.dp)
            .width(Constants.buttonWidth)
            .semantics { testTag = "Open Chrome Button" }

    ) {
        Text(text = "Open in Chrome")
    }
}