package com.samm.space.picture_of_the_day_page.presentation

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.samm.space.picture_of_the_day_page.presentation.components.ApodComponents
import com.samm.space.picture_of_the_day_page.presentation.components.ApodComponentsForLargeScreen
import com.samm.space.picture_of_the_day_page.presentation.state.ApodState
import com.samm.space.presentation_common.ProgressBar
import com.samm.space.presentation_common.labels.ErrorText
import com.samm.space.presentation_common.util.DateConverter
import com.samm.space.presentation_common.util.WindowInfo
import com.samm.space.presentation_common.util.rememberWindowInfo
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ApodScreen(stateFlow: StateFlow<ApodState>) {

    val state = stateFlow.collectAsStateWithLifecycle().value

    val context = LocalContext.current
    val window = rememberWindowInfo()
    val data = state.data

    val hdImage = data?.hdurl
    val explanation = data?.explanation
    val copyright = data?.copyright
    val date = data?.date?.let { DateConverter.formatDisplayDate(it) }

    when(window.screenWidthInfo) {
        is WindowInfo.WindowType.Compact -> {
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
                            ApodComponents(
                                context = context,
                                hdImage = hdImage,
                                explanation = explanation,
                                date = date,
                                copyright = copyright
                            )
                        }
                        state.error?.isNotBlank() == true -> {
                            ErrorText(error = state.error)
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
                        state.isLoading -> {
                            ProgressBar()
                        }
                        state.data?.url?.isNotEmpty() == true -> {
                            ApodComponents(
                                context = context,
                                hdImage = hdImage,
                                explanation = explanation,
                                date = date,
                                copyright = copyright
                            )
                        }
                        state.error?.isNotBlank() == true -> {
                            ErrorText(error = state.error)
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
                    state.isLoading -> {
                        ProgressBar()
                    }
                    state.data?.url?.isNotEmpty() == true -> {
                        ApodComponentsForLargeScreen(
                            context = context,
                            hdImage = hdImage,
                            explanation = explanation,
                            date = date,
                            copyright = copyright
                        )
                    }
                    state.error?.isNotBlank() == true -> {
                        ErrorText(error = state.error)
                    }
                }
            }
        }
    }
}
