package com.samm.space.nasa_media_library_page.presentation.details_screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import coil.compose.SubcomposeAsyncImage
import com.samm.space.presentation_common.ProgressBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsImage(
    imageLink: String? = null,
    id: Int? = null,
    scale: ContentScale
) {

    Card(
        modifier = Modifier
            .padding(10.dp)
            .semantics { testTag = "Details Image Card" },
        shape = RoundedCornerShape(10.dp)
    ) {
        imageLink?.let {
            SubcomposeAsyncImage(
                model = imageLink,
                contentDescription = "",
                modifier = Modifier
                    .sizeIn(
                        minWidth = 125.dp,
                        maxWidth = 400.dp
                    )
                    .semantics {
                        testTag = "Details Image"
                    },
                contentScale = scale,
                colorFilter = ColorFilter.colorMatrix(ColorMatrix()),
                loading = {
                    ProgressBar()
                }
            )
        }

        // Audio cards have a stock image from resources not an image url
        id?.let {
            SubcomposeAsyncImage(
                model = id,
                contentDescription = "",
                modifier = Modifier
                    .sizeIn(
                        minHeight = 125.dp,
                        minWidth = 125.dp,
                        maxHeight = 265.dp,
                        maxWidth = 300.dp
                    )
                    .semantics {
                        testTag = "Details Image - ID"
                    },
                contentScale = scale,
                colorFilter = ColorFilter.colorMatrix(ColorMatrix()),
                loading = {
                    ProgressBar()
                }
            )
        }
    }
}