package com.example.space.nasa_media_library.presentation.library_search_screen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.space.core.Constants
import com.example.space.core.MediaType
import com.example.space.core.MediaType.Companion.toBundle
import com.example.space.nasa_media_library.domain.models.Item
import com.example.space.nasa_media_library.domain.models.Link
import com.example.space.nasa_media_library.presentation.components.cards.CardImage
import com.example.space.nasa_media_library.presentation.components.cards.CardTitle
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListCard(
    navController: NavController,
    item: Item?,
    color: Color,
    links: List<Link>?,
    title: String?,
    description: String?,
    mediaType: MediaType,
    imageScaleType: ContentScale
){

    val detailsDataUrl = item?.href
    val roundedCornerAmount = 10
    val cardElevationAmount = 55.dp
    val cardImageLink = links?.first()?.href

    Card(
        modifier = Modifier
            .wrapContentWidth()
            .padding(8.dp)
            .animateContentSize(
                animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
            )
            .semantics { testTag = "List Card" },
        onClick = {
            val encodedUrl = URLEncoder.encode(detailsDataUrl ?: "", Constants.utf8Encoding)
            val encodedDescription = URLEncoder.encode(description, Constants.utf8Encoding)

            /*
                Todo:
                    - Video cards need that video type conditional in the ListCard
             */
            navController.navigate(
                "cardDetails/$encodedUrl/$encodedDescription/${mediaType.toBundle()}"
            )
        },
        shape = AbsoluteRoundedCornerShape(roundedCornerAmount),
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevationAmount)
    ) {

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            mediaType.let { type ->
                CardImage(
                    imageLink = cardImageLink,
                    scale = imageScaleType,
                    mediaType = type
                )
            }
            Box(
                modifier = Modifier
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = -10f
                        ),
                    )
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    CardTitle(title = title, color = color)
                }
            }
        }
    }
}