package com.samm.space.nasa_media_library_page.presentation.library_search_screen.components.cards

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
import com.samm.space.core.MediaType
import com.samm.space.nasa_media_library_page.domain.models.Link

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListCard(
    navController: NavController,
    metaDataUrl: String?,
    color: Color,
    links: List<Link>?,
    title: String?,
    date: String?,
    description: String?,
    mediaType: MediaType,
    imageScaleType: ContentScale
){
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
            navController.navigate(
                "cardDetails/$metaDataUrl/$description/${mediaType.type}/$title/$date"
            )
        },
        shape = AbsoluteRoundedCornerShape(roundedCornerAmount),
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevationAmount)
    ) {

        Box(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {

            CardImage(
                imageLink = cardImageLink,
                scale = imageScaleType,
                mediaType = mediaType
            )

            Box(
                modifier = Modifier
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
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
                    CardTitle(
                        title = title,
                        color = color
                    )
                }
            }
        }
    }
}