package com.samm.space.pages.picture_of_the_day_page.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.samm.space.common.presentation.util.DateConverter
import com.samm.space.common.presentation.util.WindowInfo
import com.samm.space.common.presentation.util.rememberWindowInfo
import com.samm.space.pages.picture_of_the_day_page.presentation.components.ApodComponents
import com.samm.space.pages.picture_of_the_day_page.presentation.components.ApodComponentsForLargeScreen
import com.samm.space.pages.picture_of_the_day_page.presentation.state.ApodState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ApodScreen(
    stateFlow: StateFlow<ApodState>,
    refresh: () -> Unit
) {

    val state = stateFlow.collectAsStateWithLifecycle().value

    val context = LocalContext.current
    val window = rememberWindowInfo()
    val data = state.data
    val hdImage = data?.hdurl
    val explanation = data?.explanation
    val copyright = data?.copyright
    val date = data?.date?.let { DateConverter.formatDisplayDate(data.date) }

    when(window.screenWidthInfo) {

        is WindowInfo.WindowType.Compact -> {
            ApodComponents(
                state = state,
                context = context,
                hdImage = hdImage,
                explanation = explanation,
                date = date,
                copyright = copyright,
                refresh = refresh
            )
        }
        is WindowInfo.WindowType.Medium -> {
            ApodComponents(
                state = state,
                context = context,
                hdImage = hdImage,
                explanation = explanation,
                date = date,
                copyright = copyright,
                refresh = refresh
            )
        }

        is WindowInfo.WindowType.Expanded -> {
            ApodComponentsForLargeScreen(
                state = state,
                context = context,
                hdImage = hdImage,
                explanation = explanation,
                date = date,
                copyright = copyright,
                refresh = refresh
            )
        }
    }
}