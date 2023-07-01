package com.samm.space.features.picture_of_the_day_page.presentation

import androidx.compose.runtime.Composable
import com.samm.space.common.presentation.util.WindowInfo
import com.samm.space.common.presentation.util.rememberWindowInfo
import com.samm.space.features.picture_of_the_day_page.presentation.components.ApodComponents
import com.samm.space.features.picture_of_the_day_page.presentation.components.ApodComponentsForLargeScreen
import com.samm.space.features.picture_of_the_day_page.presentation.state.ApodState

@Composable
fun ApodScreen(
    state: ApodState,
    refresh: () -> Unit
) {

    val window = rememberWindowInfo()

    when(window.screenWidthInfo) {

        is WindowInfo.WindowType.Compact -> {
            ApodComponents(
                state = state,
                refresh = refresh
            )
        }
        is WindowInfo.WindowType.Medium -> {
            ApodComponents(
                state = state,
                refresh = refresh
            )
        }

        is WindowInfo.WindowType.Expanded -> {
            ApodComponentsForLargeScreen(
                state = state,
                refresh = refresh
            )
        }
    }
}