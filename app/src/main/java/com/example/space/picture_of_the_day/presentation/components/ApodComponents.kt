package com.example.space.picture_of_the_day.presentation.components

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.space.core.Constants
import com.example.space.presentation.Title
import com.example.space.presentation.buttons.ShareButton
import com.example.space.presentation.util.DownloadFile

@Composable
fun ApodComponents(
    title: String?,
    context: Context,
    hdImage: String?,
    explanation: String?,
    date: String?, copyright: String?,
    modifier: Modifier
) {
    title?.let { Title(text = it, paddingValue = 15.dp) }
    PictureOfTheDay(imageLink = hdImage, modifier = modifier)
    ApodExplanation(explanation)
    hdImage?.let { image ->
        DownloadFile(
            url = image,
            context = context,
            filename = hdImage,
            mimeType = "image/jpeg",
            subPath = "image.jpeg"
        )
        ShareButton(uri = image.toUri(), type = Constants.IMAGE_JPEG_MIME_TYPE)
    }
    date?.let { Text(text = date, modifier = Modifier.padding(5.dp)) }
    copyright?.let { Text(text = it, modifier = Modifier.padding(5.dp)) }
}