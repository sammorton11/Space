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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.samm.space.core.Constants.mimeTypeForDownload
import com.samm.space.core.Constants.subPathForDownload
import com.samm.space.nasa_media_library_page.presentation.details_screen.components.DescriptionText
import com.samm.space.nasa_media_library_page.presentation.details_screen.components.DetailsImage
import com.samm.space.presentation_common.buttons.DownloadFile
import com.samm.space.presentation_common.buttons.OpenChromeButton
import com.samm.space.presentation_common.buttons.ShareButton
import com.samm.space.presentation_common.labels.DateLabel
import com.samm.space.presentation_common.labels.Title
import com.samm.space.presentation_common.util.WindowInfo
import com.samm.space.presentation_common.util.rememberWindowInfo

@Composable
fun ImageDetails(
    mUri: String,
    context: Context,
    url: String,
    title: String?,
    date: String?,
    description: String
) {
    val window = rememberWindowInfo()

    when (window.screenWidthInfo) {

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

                    DetailsImage(
                        imageLink = mUri,
                        scale = ContentScale.Fit
                    )
                    DescriptionText(
                        content = description
                    )
                    OpenChromeButton(
                        context = context,
                        uri = mUri
                    )
                    DownloadFile(
                        url = url,
                        context = context,
                        filename = url,
                        mimeType = mimeTypeForDownload,
                        subPath = subPathForDownload
                    )
                    ShareButton(
                        uri = url.toUri(),
                        mediaType = mimeTypeForDownload
                    )
                    DateLabel(date = date)
                }
            }
        }
        is WindowInfo.WindowType.Medium -> {

            LazyColumn (
                modifier = Modifier
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

                    DetailsImage(
                        imageLink = mUri,
                        scale = ContentScale.Fit
                    )
                    DescriptionText(
                        content = description
                    )
                    OpenChromeButton(
                        context = context,
                        uri = mUri
                    )
                    DownloadFile(
                        url = url,
                        context = context,
                        filename = url,
                        mimeType = mimeTypeForDownload,
                        subPath = subPathForDownload
                    )
                    ShareButton(
                        uri = url.toUri(),
                        mediaType = mimeTypeForDownload
                    )
                    DateLabel(date = date)
                }
            }
        }
        is WindowInfo.WindowType.Expanded -> {

            Row (
                modifier = Modifier
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
                        OpenChromeButton(
                            context = context,
                            uri = mUri
                        )
                        DownloadFile(
                            url = url,
                            context = context,
                            filename = url,
                            mimeType = mimeTypeForDownload,
                            subPath = subPathForDownload
                        )
                        ShareButton(
                            uri = url.toUri(),
                            mediaType = mimeTypeForDownload
                        )
                        DateLabel(date = date)
                    }
                }
            }
        }
    }
}