package com.samm.space.common.presentation.buttons

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import com.samm.space.core.Constants.buttonWidth
import com.samm.space.features.nasa_media_library_page.util.LibraryUiEvent

@Composable
fun DownloadFile(
    event: (LibraryUiEvent) -> Unit,
    url: String?,
    filename: String?,
    mimeType: String,
    subPath: String
) {

    val context = LocalContext.current

    OutlinedButton(
        onClick = {
            event(LibraryUiEvent.DownloadFile(
                context = context,
                url = url,
                filename = filename,
                mimeType = mimeType,
                subPath = subPath
            ))
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