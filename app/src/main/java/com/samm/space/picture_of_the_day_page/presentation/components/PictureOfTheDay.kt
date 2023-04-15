package com.samm.space.picture_of_the_day_page.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PictureOfTheDay(imageLink: String?) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .semantics { testTag = "Apod Image Card" },
        shape = AbsoluteRoundedCornerShape(10)
    ) {

        AsyncImage(
            model = imageLink,
            contentDescription = "",
            modifier = Modifier.semantics { testTag = "Apod Picture" },
            colorFilter = ColorFilter.colorMatrix(ColorMatrix()),
            contentScale = ContentScale.Fit
        )
    }
}