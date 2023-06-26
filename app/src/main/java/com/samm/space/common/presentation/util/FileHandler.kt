package com.samm.space.common.presentation.util

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.webkit.MimeTypeMap
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.HttpURLConnection
import java.net.URL

// Todo: This is stupid and you need to move it to the view model - completely redo this
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

    // Todo: This is stupid and you need to move it to the view model - completely redo this
    suspend fun shareFile(
        uri: String,
        mimeType: String,
        shareLauncher: ActivityResultLauncher<Intent>,
        type: String,
        context: Context,
    ) {

        val filename = "file.${MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)}"
        val directory = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        val file = File(directory, filename)

        // Download the image
        val connection = withContext(Dispatchers.IO) {
            URL(uri).openConnection()
        } as HttpURLConnection

        connection.doInput = true

        withContext(Dispatchers.IO) {
            connection.connect()
        }

        val inputStream = connection.inputStream

        file.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }

        withContext(Dispatchers.IO) {
            inputStream.close()
        }

        connection.disconnect()

        // Create a content URI for the image using FileProvider
        val contentUri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )

        // Share the image
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "$type/*"
        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
        shareLauncher.launch(Intent.createChooser(shareIntent, "Share file"))
    }
}