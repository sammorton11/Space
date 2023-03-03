package com.example.space.nasa_media_library.presentation.details_screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.space.nasa_media_library.presentation.components.cards.*
import com.example.space.nasa_media_library.presentation.view_models.VideoDataViewModel
import com.example.space.presentation.DownloadFile
import com.example.space.presentation.ShareButton
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun DetailsScreenContent(
    url: String, 
    description: String, 
    mediaType: String,
    viewModel: VideoDataViewModel
) {
    /*
        Todo: - fix this error - caused by the encoding and decoding below
        E/AndroidRuntime: FATAL EXCEPTION: main
    Process: com.example.space, PID: 23787
    java.lang.IllegalArgumentException: URLDecoder: Illegal hex characters in escape (%) pattern : %+o
     */
    val decodedDescription = decodeText(description)
    val context = LocalContext.current

    LazyColumn (
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Log.d("URL passed to details composable", url)
//        viewModel.getVideoData(url)
//        viewModel.getVideoData(url)
//        val mUri = getUri(viewModel, mediaType)
        viewModel.getVideoData(url)
        item {
            when (mediaType) {
                "video" -> {

                    val mUri = getUri(viewModel, mediaType)
                    Log.d("URL into CardVideo details screen", mUri)
                    CardVideo(videoViewModel = viewModel, uri = mUri)
                    CardDescription(decodedDescription)
                    DownloadFile(
                        url = mUri,
                        context = context,
                        filename = mUri,
                        mimeType = "video/mp4",
                        subPath = "video.mp4"
                    )
                    ShareButton(uri = mUri.toUri(), type = "video/mp4")
                }
                "audio" -> {
                    //viewModel.getVideoData(url)
                    val mUri = getUri(viewModel, mediaType)
                    AudioPlayer(viewModel = viewModel, mediaType = mediaType)
                    CardDescription(decodedDescription)
                    DownloadFile(
                        url = mUri,
                        context = context,
                        filename = mUri,
                        mimeType = "audio/x-wav",
                        subPath = "audio.wav"
                    )
                    ShareButton(uri = mUri.toUri(), type = "audio/x-wav")
                }
                "image" -> {
                    //viewModel.getVideoData(url)
                    val mUri = getUri(viewModel, mediaType)
                    CardImage(imageLink = mUri, 300.dp, 480.dp, ContentScale.Fit)
                    CardDescription(decodedDescription)
                    DownloadFile(
                        url = url,
                        context = context,
                        filename = url,
                        mimeType = "image/jpeg",
                        subPath = "image.jpeg"
                    )
                    ShareButton(uri = url.toUri(), type = "image/jpeg")
                }
            }
        }
    }
}

fun decodeText(text: String): String {
    var decodedText = "Decoding Failed"
    try {
        decodedText = URLDecoder.decode(text, StandardCharsets.UTF_8.toString())
    } catch (e: Exception) {
        //decodedText = text
        e.printStackTrace()
    }

    return decodedText
}