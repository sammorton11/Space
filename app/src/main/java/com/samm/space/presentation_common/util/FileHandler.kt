package com.samm.space.presentation_common.util

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.activity.result.ActivityResultLauncher
import androidx.core.net.toUri

class FileHandler {

    fun downloadFile(
        context: Context,
        url: String,
        fileName: String,
        mimeType: String,
        subPath: String,
    ) {
        val downloadManager = context.getSystemService(DownloadManager::class.java)
        val request = DownloadManager.Request(url.toUri())
            .setTitle(fileName)
            .setMimeType(mimeType)
            .setDescription("Downloading...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, subPath)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)
        downloadManager.enqueue(request)
    }
    fun shareFile(uri: Uri, shareLauncher: ActivityResultLauncher<Intent>, type: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = type
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        shareLauncher.launch(Intent.createChooser(shareIntent, "Share file"))
    }
}