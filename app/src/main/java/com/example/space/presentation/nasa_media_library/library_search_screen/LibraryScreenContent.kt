package com.example.space.presentation.nasa_media_library.library_search_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.space.presentation.nasa_media_library.components.other.*
import com.example.space.presentation.view_model.library.NasaLibraryViewModel
import com.example.space.presentation.view_model.library.VideoDataViewModel

@Composable
fun LibraryScreenContent(
    viewModel: NasaLibraryViewModel,
    videoViewModel: VideoDataViewModel,
    navController: NavController
){
    val list = viewModel.state.value.data
    val error = viewModel.state.value.error
    val isLoading = viewModel.state.value.isLoading
    val title = "NASA Media Library"

    val lazyGridState = rememberLazyGridState()

    Column() {
        Button(onClick = { navController.navigate("mars_weather_screen")}) {
            Text("Go to Weather Screen")
        }

        Title(title, 15.dp)
        val scrollState = derivedStateOf {
           lazyGridState.firstVisibleItemIndex
        }
        if ( scrollState.value == 0) {
            SearchField(onSearch = { query ->
                viewModel.getData(query)
            })
        }

        when {
            error.isNotBlank() -> { ErrorText(error = error) }
            isLoading -> { ProgressBar() }
            !list.isNullOrEmpty() -> {
                LibraryList(
                    navController = navController,
                    data = list,
                    scrollState = lazyGridState,
                    viewModel = videoViewModel
                )
            }
        }
    }
}