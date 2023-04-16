package com.samm.space.nasa_media_library_page.presentation.details_screen

import androidx.compose.runtime.Composable
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

    val state = viewModel.state.value

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