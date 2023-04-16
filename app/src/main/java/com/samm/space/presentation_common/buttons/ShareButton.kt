package com.samm.space.presentation_common.buttons

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import com.samm.space.core.Constants
import com.samm.space.presentation_common.util.FileHandler

@Composable
fun ShareButton(uri: Uri, mediaType: String) {

    val util = FileHandler()
    val shareLauncher: ActivityResultLauncher<Intent> =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

    OutlinedButton(
        onClick = { util.shareFile(uri, shareLauncher, mediaType) },
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