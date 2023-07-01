package com.samm.space.common.presentation.buttons

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import com.samm.space.core.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ShareButton(
    uri: Uri?,
    mediaType: String?
) {

    val scope = rememberCoroutineScope()
    val startActivityResult = ActivityResultContracts.StartActivityForResult()
    val shareLauncher: ActivityResultLauncher<Intent> = rememberLauncherForActivityResult(startActivityResult) { }

    OutlinedButton(
        onClick = {
            scope.launch(Dispatchers.IO) {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "$mediaType/*"
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
                shareLauncher.launch(Intent.createChooser(shareIntent, "Share file"))
            }
        },
        modifier = Modifier
            .padding(15.dp)
            .width(Constants.buttonWidth)
            .semantics {
                testTag = "Share Button"
            }
    ) {
        Text(text = "Share")
    }
}