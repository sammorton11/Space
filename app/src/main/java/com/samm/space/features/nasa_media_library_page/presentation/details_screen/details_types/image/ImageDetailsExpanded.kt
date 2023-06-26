package com.samm.space.features.nasa_media_library_page.presentation.details_screen.details_types.image

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
fun ImageDetailsExpanded(
    mUri: String,
    mediaType: String,
    context: Context,
    url: String,
    title: String?,
    date: String?,
    description: String
) {
    Row (
        modifier = Modifier
            .semantics { testTag = "Image Details Screen" }
            .padding(15.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        DetailsImage(
            imageLink = mUri,
            scale = ContentScale.None
        )

        LazyColumn(
            modifier = Modifier.padding(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            item {
                Title(
                    text = title,
                    paddingValue = 15.dp
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
}