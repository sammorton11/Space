package com.example.space.nasa_media_library.util

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri

fun downloadFile(context: Context, url: String, fileName: String, mimeType: String, subPath: String) {
    val downloadManager = context.getSystemService(DownloadManager::class.java)
    val request = DownloadManager.Request(url.toUri())
        .setTitle(fileName)
        .setMimeType(mimeType)
        .setDescription("Downloading...")
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, subPath)
        .setAllowedOverMetered(true)
        .setAllowedOverRoaming(true)
    //val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    downloadManager.enqueue(request)
}

