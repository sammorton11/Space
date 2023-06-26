package com.samm.space.features.nasa_media_library_page.presentation.details_screen.details_types.image

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.samm.space.common.presentation.buttons.DownloadFile
import com.samm.space.common.presentation.buttons.ShareButton
import com.samm.space.common.presentation.labels.DateLabel
import com.samm.space.common.presentation.labels.Title
import com.samm.space.core.Constants
import com.samm.space.features.nasa_media_library_page.presentation.details_screen.components.DescriptionText
import com.samm.space.features.nasa_media_library_page.presentation.details_screen.components.DetailsImage

@Composable
fun ImageDetailsMedium(
    mUri: String,
    mediaType: String,
    context: Context,
    url: String,
    title: String?,
    date: String?,
    description: String
) {
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .semantics { testTag = "Image Details Screen" },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Title(
                text = title,
                paddingValue = 15.dp
            )
            DetailsImage(
                imageLink = mUri,
                scale = ContentScale.Fit
            )
            DescriptionText(
                content = description
            )
            DownloadFile(
                url = url,
                context = context,
                filename = url,
                mimeType = Constants.imageMimeTypeForDownload,
                subPath = Constants.imageSubPathForDownload
            )
            ShareButton(
                uri = url.toUri(),
                mimeType = Constants.imageMimeTypeForDownload,
                mediaType = mediaType
            )
            DateLabel(date = date)
        }
    }
}