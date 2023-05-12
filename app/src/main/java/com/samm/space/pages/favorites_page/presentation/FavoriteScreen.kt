package com.samm.space.pages.favorites_page.presentation

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samm.space.common.presentation.buttons.FavoritesButton
import com.samm.space.common.presentation.util.WindowInfo
import com.samm.space.common.presentation.util.rememberWindowInfo
import com.samm.space.core.MediaType
import com.samm.space.pages.favorites_page.presentation.state.LibraryFavoriteState
import com.samm.space.pages.nasa_media_library_page.presentation.library_search_screen.components.cards.CardImage
import com.samm.space.pages.nasa_media_library_page.presentation.library_search_screen.components.cards.CardTitle
import com.samm.space.pages.nasa_media_library_page.util.LibraryUiEvent

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun FavoriteScreen(
    libraryFavoriteState: LibraryFavoriteState,
    sendEvent: (LibraryUiEvent) -> Unit,
    isFavorite: Boolean,
    navController: NavController,
    encodeText: (text: String?) -> String
) {
    val libraryFavoritesList = libraryFavoriteState.libraryFavorites
    val lazyGridState = rememberLazyStaggeredGridState()
    val window = rememberWindowInfo()
    val imageScaleType = ContentScale.FillBounds

    val gridCells = when (window.screenWidthInfo) {
        is WindowInfo.WindowType.Compact -> 2
        is WindowInfo.WindowType.Medium -> 3
        is WindowInfo.WindowType.Expanded -> 4
    }

    val context = LocalContext.current.applicationContext

    LazyVerticalStaggeredGrid(
        modifier = Modifier.semantics {
            testTag = "Favorites List"
        },
        columns = StaggeredGridCells.Fixed(gridCells),
        state = lazyGridState
    ) {
        libraryFavoritesList?.let { list ->
            items(list.size) { index ->
                val item = list[index]

                val itemListOfLinks = item.links
                val image = itemListOfLinks.first()?.href
                val itemMetaDataUrl = item.href
                val itemData = item.data.first()
                val itemDescription = itemData?.description
                val itemTitle = itemData?.title
                val itemDate = itemData?.date_created

                val mediaTypeString = MediaType.fromString(itemData?.media_type?: "image")
                    ?: MediaType.IMAGE
                val encodedUrl = encodeText(itemMetaDataUrl)
                val encodedDescription = encodeText(itemDescription)

                val roundedCornerAmount = 10
                val cardElevationAmount = 55.dp

                val routeToDetailsScreen =
                    "cardDetails/$encodedUrl/$encodedDescription/" +
                            "${mediaTypeString.type}/$itemTitle/$itemDate"

                Card(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(15.dp)
                        .animateContentSize(
                            animationSpec = tween(
                                durationMillis = 25,
                                easing = FastOutSlowInEasing
                            )
                        )
                        .semantics { testTag = "List Card" },
                    onClick = {
                        navController.navigate(routeToDetailsScreen)
                    },
                    shape = AbsoluteRoundedCornerShape(roundedCornerAmount),
                    elevation = cardElevation(defaultElevation = cardElevationAmount)
                ) {

                    Box(modifier = Modifier.fillMaxSize()) {


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
                            var favorite by remember { mutableStateOf(isFavorite) }
                            FavoritesButton(
                                item = item,
                                favorites = libraryFavoritesList,
                                onFavoriteClick = {
                                    sendEvent(LibraryUiEvent.RemoveFavorite(item))
                                    Toast.makeText(context, "Removed from Favorites", Toast.LENGTH_LONG ).show()
                                    favorite = !favorite
                                }
                            )
                        }


                        Column(modifier = Modifier.align(Alignment.BottomCenter)){
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





//    Column(modifier = Modifier.fillMaxSize()) {
//        Title(text = "Favorites", paddingValue = 15.dp)
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(15.dp)
//        ) {
//            libraryFavoritesList?.let { list ->
//                items(list.size) { index ->
//
//                }
//            }
//
//        }
//    }