package com.samm.space.presentation.buttons

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp

@Composable
fun ShareButton(uri: Uri, type: String) {
    val shareLauncher: ActivityResultLauncher<Intent> =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

    Button(
        onClick = { shareFile(uri, shareLauncher, type) },
        modifier = Modifier
            .padding(bottom = 25.dp)
            .semantics {
                testTag = "Share Button"
            }
    ) {
        Text(text = "Share")
    }
}

fun shareFile(uri: Uri, shareLauncher: ActivityResultLauncher<Intent>, type: String) {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = type
    shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
    shareLauncher.launch(Intent.createChooser(shareIntent, "Share file"))
}