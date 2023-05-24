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
    encodeText: (text: String?) -> String,
    navController: NavController,
    data: ListCardData
){
    val roundedCornerAmount = 10
    val encodedUrl = encodeText(data.url)
    val encodedDescription = encodeText(data.description)
    val encodedDateText = encodeText(data.dateText)

    Card(
        modifier = Modifier
            .padding(8.dp)
            .semantics { testTag = "List Card" },
        onClick = {
            sendEvent(
                LibraryUiEvent.OnCardClick(
                    route = "cardDetails/${encodedUrl}/${encodedDescription}/" +
                            "${data.mediaTypeString.type}/${encodeText(data.itemTitle)}/$encodedDateText",
                    navController = navController
                ))
        },
        shape = AbsoluteRoundedCornerShape(roundedCornerAmount)
    ) {

        Box {
            CardImage(
                imageLink = data.image,
                scale = data.imageScaleType,
                mediaType = data.mediaTypeString
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                FavoritesButton(
                    item = data.item!!,
                    favorites = data.favorites,
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
                CardTitle(title = data.itemTitle)
            }
        }
    }
}

data class ListCardData(
    var favorites: List<Item>,
    var item: Item?,
    var url: String?,
    var image: String?,
    var itemTitle: String?,
    var dateText: String?,
    var description: String?,
    var mediaTypeString: MediaType,
    var imageScaleType: ContentScale
)