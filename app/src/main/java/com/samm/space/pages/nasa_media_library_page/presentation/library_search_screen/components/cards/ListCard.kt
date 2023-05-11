package com.samm.space.pages.nasa_media_library_page.presentation.library_search_screen.components.cards

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samm.space.core.MediaType
import com.samm.space.pages.nasa_media_library_page.util.LibraryUiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListCard(
    sendEvent: (LibraryUiEvent) -> Unit,
    navController: NavController,
    mediaData: String?,
    image: String?,
    title: String?,
    date: String?,
    description: String?,
    mediaType: MediaType,
    imageScaleType: ContentScale
){
    val roundedCornerAmount = 10
    val cardElevationAmount = 55.dp

    Card(
        modifier = Modifier
            .wrapContentWidth()
            .padding(8.dp)
            .animateContentSize(
                animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
            )
            .semantics { testTag = "List Card" },
        onClick = {
            sendEvent(LibraryUiEvent.OnCardClick(
                route = "cardDetails/$mediaData/$description/${mediaType.type}/$title/$date",
                navController = navController
            ))
        },
        shape = AbsoluteRoundedCornerShape(roundedCornerAmount),
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevationAmount)
    ) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            CardImage(
                imageLink = image,
                scale = imageScaleType,
                mediaType = mediaType
            )

            Column(
                modifier = Modifier.align(Alignment.BottomCenter)
            ){
                CardTitle(
                    title = title
                )
            }
        }
    }
}