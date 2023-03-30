package com.samm.space.picture_of_the_day.presentation.components

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.samm.space.core.MediaDownloadType
import com.samm.space.presentation.buttons.DownloadFile
import com.samm.space.presentation.buttons.ShareButton
import com.samm.space.presentation.labels.Title

@Composable
fun ApodComponents(
    title: String?,
    context: Context,
    hdImage: String?,
    explanation: String?,
    date: String?,
    copyright: String?,
    modifier: Modifier
) {
    title?.let {
        Title(text = it, paddingValue = 15.dp)
    }
    PictureOfTheDay(
        imageLink = hdImage,
        modifier = modifier
    )
    ApodExplanation(text = explanation)

    hdImage?.let { image ->
        DownloadFile(
            url = image,
            context = context,
            filename = hdImage,
            mimeType = MediaDownloadType.IMAGE_JPEG.mimeType,
            subPath = MediaDownloadType.IMAGE_JPEG.subPath
        )
        ShareButton(
            uri = image.toUri(),
            type = MediaDownloadType.IMAGE_JPEG.mimeType
        )
    }
    date?.let {
        Text(
            text = date,
            modifier = Modifier
                .padding(5.dp)
                .semantics { testTag = "Apod Date Text" }
        )
    }
    copyright?.let {
        Text(
            text = it,
            modifier = Modifier
                .padding(5.dp)
                .semantics { testTag = "Apod Copyright Text" }
        )
    }
}