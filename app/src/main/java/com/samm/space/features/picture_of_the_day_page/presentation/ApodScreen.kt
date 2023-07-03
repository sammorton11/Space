package com.samm.space.features.picture_of_the_day_page.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.samm.space.common.presentation.util.WindowInfo
import com.samm.space.common.presentation.util.rememberWindowInfo
import com.samm.space.features.picture_of_the_day_page.domain.models.Apod
import com.samm.space.features.picture_of_the_day_page.presentation.components.ApodComponents
import com.samm.space.features.picture_of_the_day_page.presentation.components.ApodComponentsForLargeScreen
import com.samm.space.features.picture_of_the_day_page.presentation.components.MyDatePicker
import com.samm.space.features.picture_of_the_day_page.presentation.state.ApodState

@Composable
fun ApodScreen(
    state: ApodState,
    getData: (String) -> Unit,
    insert: (item: Apod) -> Unit,
    delete: (item: Apod) -> Unit,
    refresh: () -> Unit
) {

    val window = rememberWindowInfo()

    when(window.screenWidthInfo) {

        is WindowInfo.WindowType.Compact -> {
            ApodComponents(
                state = state,
                insert = insert,
                delete = delete,
                refresh = refresh
            )
        }
        is WindowInfo.WindowType.Medium -> {
            ApodComponents(
                state = state,
                insert = insert,
                delete = delete,
                refresh = refresh
            )
        }

        is WindowInfo.WindowType.Expanded -> {
            ApodComponentsForLargeScreen(
                state = state,
                insert = insert,
                refresh = refresh
            )
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        MyDatePicker(getData = getData)
    }
}