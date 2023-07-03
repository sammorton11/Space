package com.samm.space.features.picture_of_the_day_page.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.samm.space.common.presentation.ProgressBar
import com.samm.space.common.presentation.buttons.ShareButton
import com.samm.space.common.presentation.labels.ErrorText
import com.samm.space.common.presentation.labels.Title
import com.samm.space.common.presentation.util.DateConverter
import com.samm.space.core.MediaType
import com.samm.space.features.picture_of_the_day_page.domain.models.Apod
import com.samm.space.features.picture_of_the_day_page.presentation.state.ApodState

@Composable
fun ApodComponentsForLargeScreen(
    state: ApodState,
    insert: (Apod) -> Unit,
    refresh: () -> Unit
) {

    val data = state.data
    val hdImage = data?.hdurl
    val explanation = data?.explanation
    val copyright = data?.copyright
    val date = data?.date?.let { DateConverter.formatDisplayDate(data.date) }


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

                PictureOfTheDay(imageLink = hdImage)

                LazyColumn(
                    modifier = Modifier
                        .padding(25.dp)
                        .fillMaxHeight()
                        .width(1000.dp)
                ) {
                    item {

                        Title(text = "Picture of the Day", paddingValue = 15.dp)
                        ApodExplanation(explanation)
                        AddFavoriteButton(insert = insert, state = state)
                        hdImage?.let { image ->
                            ShareButton(
                                uri = image.toUri(),
                                mediaType = MediaType.IMAGE.type
                            )
                        }
                        date?.let { Text(text = date, modifier = Modifier.padding(5.dp)) }
                        copyright?.let { Text(text = it, modifier = Modifier.padding(5.dp)) }
                    }
                }
            }
            state.error?.isNotBlank() == true -> {
                ErrorText(error = state.error)
                Button(onClick = { refresh() }) {
                    Text(text = "Refresh")
                }
            }
        }
    }
}