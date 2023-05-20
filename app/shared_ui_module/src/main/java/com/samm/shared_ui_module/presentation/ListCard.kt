package com.samm.shared_ui_module.presentation

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
import com.samm.core.domain.library_models.Item
import com.samm.core.util.LibraryUiEvent
import com.samm.shared_resources.util.MediaType
import com.samm.shared_ui_module.presentation.buttons.FavoritesButton

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
                route = "cardDetails/$encodedUrl/$encodedDescription/" +
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
                modifier = Modifier.fillMaxSize().align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Black, Color.Transparent),
                            startY = 100f,
                            endY = 20f
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