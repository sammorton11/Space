package com.example.space.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun ShareButton(uri: Uri, type: String) {
    val context = LocalContext.current
    val shareLauncher: ActivityResultLauncher<Intent> =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

    Button(onClick = { shareFile(uri, context, shareLauncher, type) }) {
        Text(text = "Share")
    }
}

fun shareFile(uri: Uri, context: Context, shareLauncher: ActivityResultLauncher<Intent>, type: String) {
    val shareIntent = Intent(Intent.ACTION_SEND)
   // shareIntent.type = context.contentResolver.getType(uri)
    shareIntent.type = type
    shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
    shareLauncher.launch(Intent.createChooser(shareIntent, "Share file"))
}