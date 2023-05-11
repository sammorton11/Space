package com.samm.space.pages.favorites_page.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samm.space.common.presentation.labels.Title
import com.samm.space.core.MediaType
import com.samm.space.pages.favorites_page.presentation.state.ApodFavoriteState
import com.samm.space.pages.favorites_page.presentation.state.LibraryFavoriteState
import com.samm.space.pages.nasa_media_library_page.presentation.library_search_screen.components.cards.CardImage
import com.samm.space.pages.nasa_media_library_page.presentation.library_search_screen.components.cards.CardTitle
import com.samm.space.pages.nasa_media_library_page.util.LibraryUiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    libraryFavoriteState: LibraryFavoriteState,
    navController: NavController,
    encodeText: (text: String?) -> String
) {
    val libraryFavoritesList = libraryFavoriteState.libraryFavorites

    Column(modifier = Modifier.fillMaxSize()) {
        Title(text = "Favorites", paddingValue = 15.dp)
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            libraryFavoritesList?.let { list ->
                items(list.size) { index ->

                    val item = list[index]

                    val itemListOfLinks = item?.links
                    val image = itemListOfLinks?.first()?.href
                    val itemMetaDataUrl = item?.href
                    val itemData = item?.data?.first()
                    val itemDescription = itemData?.description
                    val itemTitle = itemData?.title
                    val itemDate = itemData?.date_created

                    val mediaTypeString = MediaType.fromString(itemData?.media_type?: "image")
                        ?: MediaType.IMAGE
                    val encodedUrl = encodeText(itemMetaDataUrl)
                    val encodedDescription = encodeText(itemDescription)

                    val roundedCornerAmount = 10
                    val cardElevationAmount = 55.dp

                    Card(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(15.dp)
                            .animateContentSize(
                                animationSpec = tween(durationMillis = 25, easing = FastOutSlowInEasing)
                            )
                            .semantics { testTag = "List Card" },
                        onClick = {
                            navController.navigate("cardDetails/$encodedUrl/$encodedDescription/${mediaTypeString.type}/$itemTitle/$itemDate",)
                        },
                        shape = AbsoluteRoundedCornerShape(roundedCornerAmount),
                        elevation = CardDefaults.cardElevation(defaultElevation = cardElevationAmount)
                    ) {

                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            CardImage(
                                imageLink = image,
                                scale = ContentScale.FillBounds,
                                mediaType = mediaTypeString
                            )

                            Column(
                                modifier = Modifier.align(Alignment.BottomCenter)
                            ){
                                CardTitle(
                                    title = itemTitle
                                )
                            }
                        }
                    }
                }
            }

        }
    }


}