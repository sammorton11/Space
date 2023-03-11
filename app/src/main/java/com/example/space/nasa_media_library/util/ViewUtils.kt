package com.example.space.nasa_media_library.util

import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.net.toUri
import com.example.space.core.Constants
import com.example.space.core.MediaType
import com.example.space.nasa_media_library.presentation.view_models.VideoDataViewModel
import org.json.JSONArray
import org.json.JSONException
import java.net.URLDecoder

class ViewUtils {

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

    /**
     *      This will grab any video or audio file that is available from the casted JSON Array
     *      The media type is already checked whether it is video or audio before this is called
     */
    fun fileTypeCheck(array: ArrayList<String>, mediaType: MediaType): String {
        var file = ""
        for (i in 0 until array.size) {

            when (mediaType) {
                MediaType.VIDEO -> {
                    when {
                        array[i].contains("mobile.mp4") -> { file = array[i] }
                        array[i].contains(".mp4") -> { file = array[i] }
                    }
                }
                MediaType.AUDIO -> {
                    when {
                        array[i].contains(".wav") -> { file = array[i] }
                        array[i].contains(".m4a") -> { file = array[i] }
                        array[i].contains(".mp3") -> { file = array[i] }
                    }
                }
                MediaType.IMAGE -> {
                    when {
                        array[i].contains(".jpg") -> { file = array[i] }
                        array[i].contains(".png") -> { file = array[i] }
                    }
                }
            }
        }
        file = file.replace("http://", "https://") // --- http:// won't work

        return file
    }

    /**
        The response from the video data api call is in string format
        so we must convert the string to a JSON Array.

        Then add all of the items from that JSON Array to a new ArrayList and return it.
     */
    fun extractUrlsFromJsonArray(stringResponse: String): ArrayList<String> {
        val arrayList: ArrayList<String> = arrayListOf()

        try {
            val jsonArray = JSONArray(stringResponse)
            for (url in 0 until jsonArray.length()) {
                arrayList.add(jsonArray.getString(url))
            }
        }
        catch (e: JSONException) {
            Log.d("Can't convert to JSON Array", e.toString())
        }

        return arrayList
    }

    fun decodeText(text: String): String {
        var decodedText = "Decoding Failed"
        try {
            decodedText = URLDecoder.decode(text, Constants.utf8Encoding)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return decodedText
    }

    fun getUri(videoViewModel: VideoDataViewModel, mediaType: MediaType): String {
        val state = videoViewModel.state.value.data
        var uri = ""

        if (state != null) {
            if (state.isNotEmpty()) {
                val uriList = this.extractUrlsFromJsonArray(state.toString())
                uri = this.fileTypeCheck(uriList, mediaType)
            }
        }
        return uri
    }

    fun openWithChrome(url: String, context: Context) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setPackage("com.android.chrome")
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // Chrome is not installed, open the default browser
            intent.setPackage(null)
            context.startActivity(intent)
        }
    }
}