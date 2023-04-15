package com.samm.space.presentation_common.buttons

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
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

@Composable
fun OpenChromeButton(
    context: Context,
    uri: String,
) {
    OutlinedButton(
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.setPackage("com.android.chrome")
            try {
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                // Chrome is not installed, open the default browser
                intent.setPackage(null)
                context.startActivity(intent)
            }
        },
        modifier = Modifier
            .padding(15.dp)
            .width(Constants.buttonWidth)
            .semantics { testTag = "Open Chrome Button" }

    ) {
        Text(text = "Open in Chrome")
    }
}