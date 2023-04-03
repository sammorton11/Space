package com.samm.space.nasa_media_library.presentation.details_screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import com.samm.space.core.MediaType
import com.samm.space.core.MediaType.Companion.toMediaType
import com.samm.space.nasa_media_library.presentation.details_screen.details_types.VideoDetails
import com.samm.space.nasa_media_library.presentation.view_models.VideoDataViewModel
import com.samm.space.nasa_media_library.util.ViewUtils
import com.samm.space.presentation.ProgressBar

// Todo: Add title to the rest of the details screens

@Composable
fun DetailsScreenContent(
    url: String,
    description: String,
    type: String,
    title: String?,
    date: String?,
    viewModel: VideoDataViewModel
) {
    val utils = ViewUtils()
    val decodedDescription = utils.decodeText(description)
    val context = LocalContext.current
    val backgroundColor = MaterialTheme.colorScheme.background
    val mediaType = type.toMediaType() // converting the media type string to a type from the Media Type Enum
    viewModel.getVideoData(url = url)

    // Todo: this is not setting the orientation
    val configuration = LocalConfiguration.current
    configuration.orientation = Configuration.ORIENTATION_PORTRAIT

    LazyColumn (
        modifier = Modifier
            .padding(0.dp)
            .fillMaxSize()
            .semantics { testTag = "Details Screen" },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            if (viewModel.state.value.isLoading) {
                ProgressBar()
            }
            when (mediaType) {
                MediaType.VIDEO -> {
                    VideoDetails(
                        context = context,
                        viewModel = viewModel,
                        mediaType = mediaType,
                        title = title,
                        date = date,
                        description = decodedDescription,
                        backgroundColor = backgroundColor
                    )
                }
                MediaType.AUDIO -> {
                    AudioDetails(
                        viewModel = viewModel,
                        mediaType = mediaType,
                        context = context,
                        title = title,
                        date = date,
                        description = decodedDescription,
                        backgroundColor = backgroundColor
                    )
                }
                MediaType.IMAGE -> {
                    ImageDetails(
                        viewModel = viewModel,
                        mediaType = mediaType,
                        context = context,
                        url = url,
                        title = title,
                        date = date,
                        description = decodedDescription,
                        backgroundColor = backgroundColor,
                    )
                }
            }
        }
    }
}