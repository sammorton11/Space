package com.samm.space.nasa_media_library_page.presentation.details_screen.details_types

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.samm.space.R
import com.samm.space.core.Constants.mimeTypeForDownload
import com.samm.space.core.Constants.subPathForDownload
import com.samm.space.nasa_media_library_page.presentation.details_screen.components.AudioPlayer
import com.samm.space.nasa_media_library_page.presentation.details_screen.components.DescriptionText
import com.samm.space.nasa_media_library_page.presentation.details_screen.components.DetailsImage
import com.samm.space.presentation_common.buttons.DownloadFile
import com.samm.space.presentation_common.buttons.OpenChromeButton
import com.samm.space.presentation_common.buttons.ShareButton
import com.samm.space.presentation_common.labels.DateLabel
import com.samm.space.presentation_common.labels.Title

@Composable
fun AudioDetails(
    audioPlayerUri: String,
    mUri: String,
    context: Context,
    title: String?,
    date: String?,
    description: String
) {

    Title(
        text = title,
        paddingValue = 15.dp
    )

    DetailsImage(
        id = R.drawable.tipper_space_man,
        scale = ContentScale.FillBounds
    )
    AudioPlayer(
        audioPlayerUri = audioPlayerUri,
        mContext = context
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