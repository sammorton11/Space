package com.samm.space.pages.nasa_media_library_page.presentation.library_search_screen.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.samm.space.common.presentation.buttons.FavoritesButton
import com.samm.space.core.MediaType
import com.samm.space.pages.nasa_media_library_page.domain.models.Item
import com.samm.space.pages.nasa_media_library_page.util.LibraryUiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListCard(
    sendEvent: (LibraryUiEvent) -> Unit,
    navController: NavController,
    favorites: List<Item>,
    item: Item?,
    encodedUrl: String?,
    image: String?,
    itemTitle: String?,
    itemDate: String?,
    encodedDescription: String?,
    mediaTypeString: MediaType,
    imageScaleType: ContentScale
){
    val roundedCornerAmount = 10

    Card(
        modifier = Modifier
            .padding(8.dp)
            .semantics { testTag = "List Card" },
        onClick = {
            sendEvent(
                LibraryUiEvent.OnCardClick(
                    route = "cardDetails/${encodedUrl}/${encodedDescription}/" +
                            "${mediaTypeString.type}/$itemTitle/$itemDate",
                    navController = navController
                ))
        },
        shape = AbsoluteRoundedCornerShape(roundedCornerAmount)
    ) {

        Box(

        ) {
            CardImage(
                imageLink = image,
                scale = imageScaleType,
                mediaType = mediaTypeString
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                FavoritesButton(
                    item = item!!,
                    favorites = favorites,
                    event = sendEvent,
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = 100f,
                            endY = 10f
                        )
                    )
            ) {
                CardTitle(
                    title = itemTitle
                )
            }
        }
    }
}