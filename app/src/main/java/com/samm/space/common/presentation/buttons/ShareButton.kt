package com.samm.space.common.presentation.buttons

import android.content.Intent
import android.net.Uri
import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import com.samm.space.common.presentation.util.FileHandler
import com.samm.space.core.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ShareButton(
    uri: Uri?,
    mediaType: String,
    mimeType: String
) {

    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val util = FileHandler()
    val startActivityResult = ActivityResultContracts.StartActivityForResult()
    val shareLauncher: ActivityResultLauncher<Intent> = rememberLauncherForActivityResult(startActivityResult) { }

    OutlinedButton(
        onClick = {
            Log.d("Share button uri:  ", "$uri")
            scope.launch(Dispatchers.IO) {
                util.shareFile(
                    uri = uri.toString(),
                    mimeType = mimeType,
                    shareLauncher = shareLauncher,
                    type = mediaType,
                    context = context,
                )
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