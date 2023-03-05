package com.example.space.picture_of_the_day.presentation.components

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.space.presentation.Title
import com.example.space.presentation.util.DownloadFile

@Composable
fun ApodComponentsForLargeScreen(
    title: String?,
    context: Context,
    hdImage: String?,
    explanation: String?,
    date: String?, copyright: String?,
    modifier: Modifier
) {
    PictureOfTheDay(imageLink = hdImage, modifier = modifier)
    LazyColumn(modifier = Modifier.padding(25.dp).fillMaxSize()) {
        item {
            title?.let { Title(text = it, paddingValue = 15.dp) }
            ApodExplanation(explanation)
            hdImage?.let { image ->
                DownloadFile(
                    url = image,
                    context = context,
                    filename = hdImage,
                    mimeType = "image/jpeg",
                    subPath = "image.jpeg"
                )
            }
            date?.let { Text(text = date, modifier = Modifier.padding(5.dp)) }
            copyright?.let { Text(text = it, modifier = Modifier.padding(5.dp)) }
        }
    }
}