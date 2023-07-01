package com.samm.space.features.nasa_media_library_page.presentation.details_screen.details_types.image

import androidx.compose.foundation.layout.Arrangement
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
import com.samm.space.common.presentation.buttons.DownloadFile
import com.samm.space.common.presentation.buttons.OpenChromeButton
import com.samm.space.common.presentation.buttons.ShareButton
import com.samm.space.common.presentation.labels.DateLabel
import com.samm.space.common.presentation.labels.Title
import com.samm.space.core.Constants
import com.samm.space.core.MediaType
import com.samm.space.core.MediaType.Companion.toMediaType
import com.samm.space.features.nasa_media_library_page.presentation.details_screen.components.DescriptionText
import com.samm.space.features.nasa_media_library_page.presentation.details_screen.components.DetailsImage
import com.samm.space.features.nasa_media_library_page.presentation.state.DetailsScreenState
import com.samm.space.features.nasa_media_library_page.util.LibraryUiEvent

@Composable
fun ImageDetailsCompact(
    state: DetailsScreenState,
    event: (LibraryUiEvent) -> Unit,
    getUri: (String?, MediaType) -> String
) {

    val mediaType = state.type?.toMediaType()
    val data = state.data
    val uri = mediaType?.let { getUri(data, it) }
    val description = state.description
    val title = state.title
    val date = state.date


    LazyColumn (
        modifier = Modifier
            .padding(0.dp)
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
                imageLink = uri,
                scale = ContentScale.Fit
            )
            DescriptionText(
                content = description
            )
            OpenChromeButton(
                uri = uri
            )
            DownloadFile(
                event = event,
                url = uri,
                filename = uri,
                mimeType = Constants.imageMimeTypeForDownload,
                subPath = Constants.imageSubPathForDownload
            )
            ShareButton(
                uri = uri?.toUri(),
                mediaType = mediaType?.type
            )
            DateLabel(date = date)
        }
    }
}