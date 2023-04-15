package com.samm.space.nasa_media_library_page.presentation.details_screen

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
import com.samm.space.nasa_media_library_page.presentation.view_models.MediaDataViewModel
import com.samm.space.presentation_common.ProgressBar
import com.samm.space.presentation_common.labels.ErrorText

@Composable
fun DetailsScreen(
    metaDataUrl: String,
    description: String,
    type: String,
    title: String?,
    date: String?,
    viewModel: MediaDataViewModel
) {

    viewModel.getMediaData(url = metaDataUrl)

    LazyColumn (
        modifier = Modifier
            .padding(0.dp)
            .fillMaxSize()
            .semantics { testTag = "Details Screen" },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val state = viewModel.state.value

        item {
            when {
                state.isLoading -> {
                    ProgressBar()
                }
                state.data?.isNotEmpty() == true -> {
                    DetailsScreenContent(
                        metaDataUrl = metaDataUrl,
                        description = description,
                        type = type,
                        title = title,
                        date = date,
                        viewModel = viewModel
                    )
                }
                state.error.isNotEmpty() -> {
                    ErrorText(error = state.error)
                }
            }
        }
    }
}