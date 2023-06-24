package com.samm.space.pages.nasa_media_library_page.presentation.details_screen.details_types

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
import com.samm.space.common.presentation.util.WindowInfo
import com.samm.space.common.presentation.util.rememberWindowInfo
import com.samm.space.core.Constants.videoMimeTypeForDownload
import com.samm.space.core.Constants.videoSubPathForDownload
import com.samm.space.pages.nasa_media_library_page.presentation.details_screen.components.CardVideoPlayer
import com.samm.space.pages.nasa_media_library_page.presentation.details_screen.components.DescriptionText

@Composable
fun VideoDetails(
    context: Context,
    mediaType: String,
    state: String?,
    mUri: String,
    title: String?,
    date: String?,
    description: String,
) {

    val window = rememberWindowInfo()

    when(window.screenWidthInfo) {

        is WindowInfo.WindowType.Compact -> {
            LazyColumn (
                modifier = Modifier
                    .padding(0.dp)
                    .fillMaxSize()
                    .semantics { testTag = "Video Details Screen" },
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Title(
                        text = title,
                        paddingValue = 15.dp
                    )
                    CardVideoPlayer(
                        state = state,
                        uri = mUri,
                        aspectRatio = 1f
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
                        mimeType = videoMimeTypeForDownload,
                        subPath = videoSubPathForDownload
                    )
                    ShareButton(
                        uri = mUri.toUri(),
                        mimeType = videoMimeTypeForDownload,
                        mediaType = mediaType
                    )
                    DateLabel(date = date)
                }
            }
        }

        is WindowInfo.WindowType.Medium -> {
            LazyColumn (
                modifier = Modifier
                    .padding(0.dp)
                    .fillMaxSize()
                    .semantics { testTag = "Video Details Screen" },
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Title(
                        text = title,
                        paddingValue = 15.dp
                    )
                    CardVideoPlayer(
                        state = state,
                        uri = mUri,
                        aspectRatio = 1f
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
                        mimeType = videoMimeTypeForDownload,
                        subPath = videoSubPathForDownload
                    )
                    ShareButton(
                        uri = mUri.toUri(),
                        mimeType = videoMimeTypeForDownload,
                        mediaType = mediaType
                    )
                    DateLabel(date = date)
                }
            }
        }

        is WindowInfo.WindowType.Expanded -> {

            Row (
                modifier = Modifier
                    .padding(15.dp)
                    .semantics { testTag = "Video Details Screen" }
            ) {

                CardVideoPlayer(
                    state = state,
                    uri = mUri,
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
                            context = context,
                            uri = mUri
                        )
                        DownloadFile(
                            url = mUri,
                            context = context,
                            filename = mUri,
                            mimeType = videoMimeTypeForDownload,
                            subPath = videoSubPathForDownload
                        )
                        ShareButton(
                            uri = mUri.toUri(),
                            mimeType = videoMimeTypeForDownload,
                            mediaType = mediaType
                        )
                        DateLabel(date = date)
                    }
                }
            }
        }
    }
}