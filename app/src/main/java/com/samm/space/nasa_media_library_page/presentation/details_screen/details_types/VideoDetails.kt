package com.samm.space.nasa_media_library_page.presentation.details_screen.details_types

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
import com.samm.space.core.Constants.mimeTypeForDownload
import com.samm.space.core.Constants.subPathForDownload
import com.samm.space.nasa_media_library_page.presentation.details_screen.components.CardMediaPlayer
import com.samm.space.nasa_media_library_page.presentation.details_screen.components.DescriptionText
import com.samm.space.presentation_common.buttons.DownloadFile
import com.samm.space.presentation_common.buttons.OpenChromeButton
import com.samm.space.presentation_common.buttons.ShareButton
import com.samm.space.presentation_common.labels.DateLabel
import com.samm.space.presentation_common.labels.Title
import com.samm.space.presentation_common.util.WindowInfo
import com.samm.space.presentation_common.util.rememberWindowInfo

@Composable
fun VideoDetails(
    context: Context,
    state: String?,
    mUri: String,
    title: String?,
    date: String?,
    description: String
) {

    val window = rememberWindowInfo()

    when(window.screenWidthInfo) {

        is WindowInfo.WindowType.Compact -> {
            LazyColumn (
                modifier = Modifier
                    .padding(0.dp)
                    .fillMaxSize()
                    .semantics { testTag = "Details Screen" },
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Title(
                        text = title,
                        paddingValue = 15.dp
                    )
                    CardMediaPlayer(
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
                        mimeType = mimeTypeForDownload,
                        subPath = subPathForDownload
                    )
                    ShareButton(
                        uri = mUri.toUri(),
                        mediaType = mimeTypeForDownload
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
                    .semantics { testTag = "Details Screen" },
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Title(
                        text = title,
                        paddingValue = 15.dp
                    )
                    CardMediaPlayer(
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
                        mimeType = mimeTypeForDownload,
                        subPath = subPathForDownload
                    )
                    ShareButton(
                        uri = mUri.toUri(),
                        mediaType = mimeTypeForDownload
                    )
                    DateLabel(date = date)
                }
            }
        }

        is WindowInfo.WindowType.Expanded -> {

            Row (
                modifier = Modifier
                    .padding(15.dp)
                    .semantics { testTag = "Details Screen" }
            ) {

                CardMediaPlayer(
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
                            mimeType = mimeTypeForDownload,
                            subPath = subPathForDownload
                        )
                        ShareButton(
                            uri = mUri.toUri(),
                            mediaType = mimeTypeForDownload
                        )
                        DateLabel(date = date)
                    }

                }
            }
        }
    }
}