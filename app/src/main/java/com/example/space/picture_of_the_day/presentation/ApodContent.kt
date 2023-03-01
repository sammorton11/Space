package com.example.space.picture_of_the_day.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.space.picture_of_the_day.presentation.components.ApodExplantation
import com.example.space.picture_of_the_day.presentation.components.PictureOfTheDay
import com.example.space.presentation.ErrorText
import com.example.space.presentation.ProgressBar
import com.example.space.presentation.Title

@Composable
fun ApodContent(viewModel: ApodViewModel) {

    val state = viewModel.state
    val data = state.value.data
    val hdImage = data?.hdurl
    val explanation = data?.explanation
    val title = data?.title
    val copywrite = data?.copyright
    val date = data?.date

    val imageHeight = 550.dp
    val imageWidth = 400.dp

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(15.dp)) {
        item {
            when {
                state.value.isLoading -> {
                    ProgressBar()
                }
                state.value.data?.url?.isNotEmpty() == true -> {
                    Title(text = title.toString(), paddingValue = 15.dp)
                    PictureOfTheDay(imageLink = hdImage)
                    ApodExplantation(explanation)
                    Text(text = date.toString(), modifier = Modifier.padding(5.dp))
                }
                state.value.error.isNotBlank() -> {
                    ErrorText(error = state.value.error)
                }
            }
        }
    }
}