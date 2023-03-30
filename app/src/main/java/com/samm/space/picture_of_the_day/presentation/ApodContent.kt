package com.samm.space.picture_of_the_day.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.samm.space.picture_of_the_day.presentation.components.ApodComponents
import com.samm.space.picture_of_the_day.presentation.components.ApodComponentsForLargeScreen
import com.samm.space.presentation.ProgressBar
import com.samm.space.presentation.labels.ErrorText
import com.samm.space.presentation.util.DateConverter
import com.samm.space.presentation.util.WindowInfo
import com.samm.space.presentation.util.rememberWindowInfo

@Composable
fun ApodContent(viewModel: ApodViewModel) {

    val state = viewModel.state
    val data = state.value.data
    val hdImage = data?.hdurl
    val explanation = data?.explanation
    val title = data?.title
    val copyright = data?.copyright
    val date = data?.date?.let { DateConverter.formatDisplayDate(it) }
    val context = LocalContext.current
    val window = rememberWindowInfo()
    var modifier: Modifier = Modifier

    when(window.screenWidthInfo) {
        is WindowInfo.WindowType.Compact -> {
            modifier = Modifier
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    when {
                        state.value.isLoading -> {
                            ProgressBar()
                        }
                        state.value.data?.url?.isNotEmpty() == true -> {
                            ApodComponents(
                                title = title,
                                context = context,
                                hdImage = hdImage,
                                explanation = explanation,
                                date = date,
                                copyright = copyright,
                                modifier = modifier
                            )
                        }
                        state.value.error?.isNotBlank() == true -> {
                            state.value.error?.let { ErrorText(error = it) }
                        }
                    }
                }
            }
        }
        is WindowInfo.WindowType.Medium -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    when {
                        state.value.isLoading -> {
                            ProgressBar()
                        }
                        state.value.data?.url?.isNotEmpty() == true -> {
                            ApodComponents(
                                title = title,
                                context = context,
                                hdImage = hdImage,
                                explanation = explanation,
                                date = date,
                                copyright = copyright,
                                modifier = modifier
                            )
                        }
                        state.value.error?.isNotBlank() == true -> {
                            state.value.error?.let { ErrorText(error = it) }
                        }
                    }
                }
            }
        }

        is WindowInfo.WindowType.Expanded -> {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                when {
                    state.value.isLoading -> {
                        ProgressBar()
                    }
                    state.value.data?.url?.isNotEmpty() == true -> {
                        ApodComponentsForLargeScreen(
                            title = title,
                            context = context,
                            hdImage = hdImage,
                            explanation = explanation,
                            date = date,
                            copyright = copyright,
                            modifier = modifier
                        )
                    }
                    state.value.error?.isNotBlank() == true -> {
                        state.value.error?.let { ErrorText(error = it) }
                    }
                }
            }
        }
    }
}
