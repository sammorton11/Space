package com.samm.space.pages.nasa_media_library_page.presentation.details_screen.details_types.audio

import CustomAudioPlayer
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.samm.space.common.presentation.buttons.DownloadFile
import com.samm.space.common.presentation.buttons.OpenChromeButton
import com.samm.space.common.presentation.buttons.ShareButton
import com.samm.space.common.presentation.labels.DateLabel
import com.samm.space.common.presentation.labels.Title
import com.samm.space.core.Constants
import com.samm.space.pages.nasa_media_library_page.presentation.details_screen.components.DescriptionText
import com.samm.space.pages.nasa_media_library_page.presentation.view_models.MediaPlayerViewModel

@Composable
fun AudioDetailsExpanded(
    audioPlayerUri: String,
    mUri: String,
    mediaType: String,
    context: Context,
    title: String?,
    date: String?,
    description: String
) {

    val viewModel: MediaPlayerViewModel = hiltViewModel()

    Row (
        modifier = Modifier
            .semantics { testTag = "Audio Details Screen" }
            .padding(15.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        LazyColumn(modifier = Modifier.fillMaxHeight()) {
            item {
                CustomAudioPlayer(audioPlayerUri, viewModel)
            }
        }

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

}
