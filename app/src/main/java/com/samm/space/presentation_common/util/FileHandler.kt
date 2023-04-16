package com.samm.space.presentation_common.util

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


//    suspend fun shareFile(
//        uri: String,
//        shareLauncher: ActivityResultLauncher<Intent>,
//        mediaType: String,
//        context: Context,
//    ) {
//        // Download the file and save it to external storage
//        val filename = "file.${mediaType.split("/").last()}"
//        val contentValues = ContentValues().apply {
//            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
//            put(MediaStore.MediaColumns.MIME_TYPE, mediaType)
//        }
//        val resolver = context.contentResolver
//        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues) ?: return
//        withContext(Dispatchers.IO) {
//            resolver.openOutputStream(uri).use { outputStream ->
//                URL(uri.toString()).openStream().use { inputStream ->
//                    if (outputStream != null) {
//                        inputStream.copyTo(outputStream)
//                    }
//                }
//            }
//        }
//
//        // Share the file
//        val shareIntent = Intent(Intent.ACTION_SEND).apply {
//            type = mediaType
//            putExtra(Intent.EXTRA_STREAM, uri)
//        }
//        shareLauncher.launch(Intent.createChooser(shareIntent, "Share file"))
//    }

}