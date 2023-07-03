package com.samm.space.features.picture_of_the_day_page.presentation.components

import android.util.Log
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
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import com.samm.space.features.nasa_media_library_page.presentation.library_search_screen.components.cards.CardImage
import com.samm.space.features.nasa_media_library_page.presentation.library_search_screen.components.cards.CardTitle
import com.samm.space.features.nasa_media_library_page.presentation.library_search_screen.components.cards.ListCardData
import com.samm.space.features.picture_of_the_day_page.domain.models.Apod

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApodFavoritesListCard(
    insert: (item: Apod) -> Unit,
    delete: (item: Apod) -> Unit,
    encodeText: (text: String?) -> String,
    navigate: (route: String) -> Unit,
    state: ListCardData
){

    Log.d("Url", state.url.toString())

    val roundedCornerAmount = 10

    val encodedUrl = encodeText(state.url)
    Log.d("Url", encodedUrl)

    val encodedDescription = encodeText(state.description)
    val encodedDateText = encodeText(state.dateText)

    Card(
        modifier = Modifier
            .padding(8.dp)
            .semantics { testTag = "List Card" },
        onClick = {
            navigate(
                "cardDetails/${encodedUrl}/${encodedDescription}/" +
                    "${state.mediaTypeString.type}/${encodeText(state.itemTitle)}/$encodedDateText"
            )
        },
        shape = AbsoluteRoundedCornerShape(roundedCornerAmount)
    ) {

        Box {
            CardImage(
                imageLink = state.image,
                mediaType = state.mediaTypeString
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                Log.d("Apod data", state.apod.toString())

                state.apod?.let {
                    ApodFavoritesButton(
                        item = it,
                        favorites = state.apodFavorites,
                        insert = insert,
                        delete = delete
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Black, Color.Transparent),
                            startY = 50f,
                            endY = 0f
                        )
                    )
            ) {
                CardTitle(title = state.itemTitle)
            }
        }
    }
}
