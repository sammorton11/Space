package com.samm.space.nasa_media_library_page.presentation.details_screen.details_types

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
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

@Composable
fun ImageDetails(
    mUri: String,
    context: Context,
    url: String,
    title: String?,
    date: String?,
    description: String
) {

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