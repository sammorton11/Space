package com.samm.space.features.nasa_media_library_page.presentation.details_screen.details_types.audio

import CustomAudioPlayer
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
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
import com.samm.space.features.nasa_media_library_page.presentation.details_screen.components.DescriptionText

@Composable
fun AudioDetailsMedium(
    audioPlayerUri: String,
    mUri: String,
    mediaType: String,
    context: Context,
    title: String?,
    date: String?,
    description: String,
) {

    LazyColumn (
        modifier = Modifier
            .padding(0.dp)
            .fillMaxSize()
            .semantics { testTag = "Audio Details Screen" },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {

            Title(
                text = title,
                paddingValue = 15.dp
            )

            CustomAudioPlayer(audioPlayerUri)

            DescriptionText(
                content = description
            )

            OpenChromeButton(
                context = context,
                uri = mUri
            )

            DownloadFile(
                url = mUri,
                context = context,
                filename = mUri,
                mimeType = Constants.audioMimeTypeForDownload,
                subPath = Constants.audioSubPathForDownload
            )

            ShareButton(
                uri = mUri.toUri(),
                mimeType = Constants.audioMimeTypeForDownload,
                mediaType = mediaType
            )

            DateLabel(date = date)
        }
    }
}
