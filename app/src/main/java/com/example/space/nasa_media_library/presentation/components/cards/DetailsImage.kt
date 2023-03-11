package com.example.space.nasa_media_library.presentation.components.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.space.nasa_media_library.util.ViewUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsImage(
    imageLink: String? = null,
    id: Int? = null,
    scale: ContentScale,
) {

    val context = LocalContext.current
    val viewUtil = ViewUtils()
    Card(
        modifier = Modifier
            .padding(25.dp)
            .wrapContentWidth()
            .semantics { testTag = "Details Image Card" },
        shape = RoundedCornerShape(10.dp)
    ) {
        imageLink?.let {
            SubcomposeAsyncImage(
                model = imageLink,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .sizeIn(
                        minHeight = 125.dp,
                        minWidth = 125.dp,
                        maxHeight = 265.dp,
                        maxWidth = 200.dp
                    )
                    .clickable {
                        viewUtil.openWithChrome(imageLink, context)
                    }
                    .semantics {
                        testTag = "Details Image - Image Url"
                    },
                contentScale = scale,
                colorFilter = ColorFilter.colorMatrix(ColorMatrix()),
            )
        }
        id?.let {
            SubcomposeAsyncImage(
                model = id,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .sizeIn(
                        minHeight = 125.dp,
                        minWidth = 125.dp,
                        maxHeight = 265.dp,
                        maxWidth = 200.dp
                    )
                    .semantics {
                        testTag = "Details Image - ID"
                    },
                contentScale = scale,
                colorFilter = ColorFilter.colorMatrix(ColorMatrix()),
            )
        }
    }
}