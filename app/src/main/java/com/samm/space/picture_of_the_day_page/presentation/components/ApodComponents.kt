package com.samm.space.picture_of_the_day_page.presentation.components

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.samm.space.core.Constants
import com.samm.space.presentation_common.buttons.DownloadFile
import com.samm.space.presentation_common.buttons.ShareButton
import com.samm.space.presentation_common.labels.Title

@Composable
fun ApodComponents(
    context: Context,
    hdImage: String?,
    explanation: String?,
    date: String?,
    copyright: String?
) {


    Title(text = "Picture of the Day", paddingValue = 15.dp)

    PictureOfTheDay(
        imageLink = hdImage
    )

    ApodExplanation(text = explanation)

    hdImage?.let { image ->

        DownloadFile(
            url = image,
            context = context,
            filename = hdImage,
            mimeType = Constants.mimeTypeForDownload,
            subPath = Constants.subPathForDownload
        )

        ShareButton(
            uri = image.toUri(),
            mediaType = Constants.mimeTypeForDownload
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