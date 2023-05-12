package com.samm.space.pages.nasa_media_library_page.presentation.library_search_screen

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samm.space.common.presentation.buttons.FavoritesButton
import com.samm.space.core.MediaType
import com.samm.space.pages.nasa_media_library_page.domain.models.Item
import com.samm.space.pages.nasa_media_library_page.presentation.library_search_screen.components.cards.CardImage
import com.samm.space.pages.nasa_media_library_page.presentation.library_search_screen.components.cards.CardTitle
import com.samm.space.pages.nasa_media_library_page.util.LibraryUiEvent

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LibraryListContent(
    sendEvent: (LibraryUiEvent) -> Unit,
    favorites: List<Item>,
    navController: NavController,
    filterType: String,
    data: List<Item?>,
    scrollState: LazyStaggeredGridState,
    gridCells: Int,
    imageScaleType: ContentScale,
    filteredList: (data: List<Item?>, filterType: String) -> List<Item?>,
    encodeText: (text: String?) -> String
) {

    val context = LocalContext.current.applicationContext

    LazyVerticalStaggeredGrid(
        modifier = Modifier.semantics {
            testTag = "Library List"
        },
        columns = StaggeredGridCells.Fixed(gridCells),
        state = scrollState
    ) {

        items(filteredList(data, filterType)) { item ->

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
                    .padding(8.dp)
                    .animateContentSize(
                        animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
                    )
                    .semantics { testTag = "List Card" },
                onClick = {
                    sendEvent(LibraryUiEvent.OnCardClick(
                        route = "cardDetails/$encodedUrl/$encodedDescription/${mediaTypeString.type}/$itemTitle/$itemDate",
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
                        mediaType = mediaTypeString
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopEnd)
                    ) {
                        FavoritesButton(
                            item = item!!,
                            favorites = favorites,
                            onFavoriteClick = {
                                sendEvent(LibraryUiEvent.AddLibraryFavorite(item))
                                Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }

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