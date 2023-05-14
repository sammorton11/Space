package com.samm.space.pages.picture_of_the_day_page.presentation.components

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.samm.space.core.Constants
import com.samm.space.core.MediaType
import com.samm.space.pages.picture_of_the_day_page.presentation.state.ApodState
import com.samm.space.common.presentation.ProgressBar
import com.samm.space.common.presentation.buttons.DownloadFile
import com.samm.space.common.presentation.buttons.ShareButton
import com.samm.space.common.presentation.labels.ErrorText
import com.samm.space.common.presentation.labels.Title

@Composable
fun ApodComponents(
    state: ApodState,
    context: Context,
    hdImage: String?,
    explanation: String?,
    date: String?,
    copyright: String?,
    refresh: () -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            when {
                state.isLoading -> {
                    ProgressBar()
                }
                state.data?.url?.isNotEmpty() == true -> {
                    Title(text = "Picture of the Day", paddingValue = 15.dp)
                    PictureOfTheDay(
                        imageLink = hdImage
                    )
                    ApodExplanation(text = explanation)
                    hdImage?.let { image ->
                        DownloadFile(
                            url = image,
                            context = context,
                            filename = hdImage,
                            mimeType = Constants.imageMimeTypeForDownload,
                            subPath = Constants.imageSubPathForDownload
                        )

                        ShareButton(
                            uri = image.toUri(),
                            mimeType = Constants.imageMimeTypeForDownload,
                            mediaType = MediaType.IMAGE.type
                        )
                    }
                    date?.let {
                        Text(
                            text = date,
                            modifier = Modifier
                                .padding(5.dp)
                                .semantics { testTag = "Apod Date Text" }
                        )
                    }
                    copyright?.let {
                        Text(
                            text = it,
                            modifier = Modifier
                                .padding(5.dp)
                                .semantics { testTag = "Apod Copyright Text" }
                        )
                    }
                }
                state.error?.isNotBlank() == true -> {
                    ErrorText(error = state.error)
                    Button(onClick = { refresh() }) {
                        Text(text = "Refresh")
                    }
                }
            }
        }
    }
}