package com.samm.space.features.nasa_media_library_page.presentation.library_search_screen.components.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.samm.space.R
import com.samm.space.core.Constants.fastInAnimation
import com.samm.space.core.Constants.imageScaleType
import com.samm.space.core.MediaType

/**
 *  This card can be of 3 type: Image, Video, or Audio
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardImage(
    imageLink: String?,
    mediaType: MediaType,
) {

    Card(
        modifier = fastInAnimation,
        shape = AbsoluteRoundedCornerShape(10),
    ) {

        when(mediaType) {
            MediaType.AUDIO -> {
                SubcomposeAsyncImage(
                    model = Image(
                        painter = painterResource(id = R.drawable.tipper_space_man),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxSize()
                            .semantics { testTag = "Card Image" },
                        contentScale = ContentScale.Inside,
                    ),
                    contentDescription = "",
                    colorFilter = ColorFilter.colorMatrix(ColorMatrix())
                )
            }
            MediaType.IMAGE -> {
                SubcomposeAsyncImage(
                    model = imageLink,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .sizeIn(minHeight = 125.dp)
                        .semantics { testTag = "Card Image" },
                    contentScale = imageScaleType,
                    colorFilter = ColorFilter.colorMatrix(ColorMatrix()),
                )
            }
            MediaType.VIDEO -> {
                SubcomposeAsyncImage(
                    model = imageLink,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .sizeIn(minHeight = 125.dp)
                        .semantics { testTag = "Card Image" },
                    contentScale = imageScaleType,
                    colorFilter = ColorFilter.colorMatrix(ColorMatrix()),
                )
            }
        }
    }
}