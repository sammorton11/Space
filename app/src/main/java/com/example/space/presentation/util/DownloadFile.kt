package com.example.space.presentation.util

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.space.nasa_media_library.util.downloadFile

@Composable
fun DownloadFile(
    url: String,
    filename: String,
    context: Context,
    mimeType: String,
    subPath: String
) {
    Button(
        onClick = {
            downloadFile(context, url, filename, mimeType, subPath)
            Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier.padding(15.dp)
    ) {
        Text("Download")
    }
}