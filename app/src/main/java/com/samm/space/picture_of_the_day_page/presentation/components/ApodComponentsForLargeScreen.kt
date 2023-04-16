package com.samm.space.picture_of_the_day_page.presentation.components

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.samm.space.core.Constants.mimeTypeForDownload
import com.samm.space.core.Constants.subPathForDownload
import com.samm.space.presentation_common.buttons.DownloadFile
import com.samm.space.presentation_common.buttons.ShareButton
import com.samm.space.presentation_common.labels.Title

@Composable
fun ApodComponentsForLargeScreen(
    context: Context,
    hdImage: String?,
    explanation: String?,
    date: String?, copyright: String?
) {

    PictureOfTheDay(imageLink = hdImage)

    LazyColumn(modifier = Modifier
        .padding(25.dp)
        .fillMaxSize()
    ) {

        item {

            Title(text = "Picture of the Day", paddingValue = 15.dp)

            ApodExplanation(explanation)

            hdImage?.let { image ->

                DownloadFile(
                    url = image,
                    context = context,
                    filename = hdImage,
                    mimeType = mimeTypeForDownload,
                    subPath = subPathForDownload
                )

                ShareButton(
                    uri = image.toUri(),
                    mediaType = mimeTypeForDownload
                )
            }

            date?.let { Text(text = date, modifier = Modifier.padding(5.dp)) }
            copyright?.let { Text(text = it, modifier = Modifier.padding(5.dp)) }
        }
    }
}