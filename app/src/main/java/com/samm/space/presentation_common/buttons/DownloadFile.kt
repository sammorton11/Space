package com.samm.space.presentation_common.buttons

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import com.samm.space.core.Constants.buttonWidth
import com.samm.space.presentation_common.util.FileHandler

@Composable
fun DownloadFile(
    url: String,
    filename: String,
    context: Context,
    mimeType: String,
    subPath: String
) {
    val utils = FileHandler()
    OutlinedButton(
        onClick = {
            utils.downloadFile(
                context = context,
                url = url,
                fileName = filename,
                mimeType = mimeType,
                subPath = subPath
            )
            Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier
            .padding(15.dp)
            .width(buttonWidth)
            .semantics {
                testTag = "Download Button"
            }
    ) {
        Text("Download")
    }
}