package com.samm.space.features.nasa_media_library_page.presentation.details_screen.details_types.video

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.samm.space.common.presentation.buttons.DownloadFile
import com.samm.space.common.presentation.buttons.OpenChromeButton
import com.samm.space.common.presentation.buttons.ShareButton
import com.samm.space.common.presentation.labels.DateLabel
import com.samm.space.common.presentation.labels.Title
import com.samm.space.core.Constants
import com.samm.space.features.nasa_media_library_page.presentation.details_screen.components.CardVideoPlayer
import com.samm.space.features.nasa_media_library_page.presentation.details_screen.components.DescriptionText

@Composable
fun VideoDetailsExpanded(
    mediaType: String,
    uri: String?,
    title: String?,
    date: String?,
    description: String
) {
    Row (
        modifier = Modifier
            .padding(15.dp)
            .semantics { testTag = "Video Details Screen" }
    ) {

        CardVideoPlayer(
            uri = uri,
            aspectRatio = 2f
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
                OpenChromeButton(
                    uri = uri
                )
                DownloadFile(
                    url = uri,
                    filename = uri,
                    mimeType = Constants.videoMimeTypeForDownload,
                    subPath = Constants.videoSubPathForDownload
                )
                ShareButton(
                    uri = uri?.toUri(),
                    mimeType = Constants.videoMimeTypeForDownload,
                    mediaType = mediaType
                )
                DateLabel(date = date)
            }
        }
    }
}