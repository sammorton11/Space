package com.samm.favorites.favorites_page

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samm.core.util.LibraryUiEvent
import com.samm.favorites.favorites_page.state.LibraryFavoriteState
import com.samm.shared_resources.util.MediaType
import com.samm.shared_resources.util.WindowInfo
import com.samm.shared_resources.util.rememberWindowInfo
import com.samm.shared_ui_module.presentation.ListCard
import com.samm.shared_ui_module.presentation.labels.Title


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteScreen(
    libraryFavoriteState: LibraryFavoriteState,
    sendEvent: (LibraryUiEvent) -> Unit,
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
    var filteredFavorites by remember { mutableStateOf(libraryFavoritesList ?: emptyList()) }
    var filterText by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Title(text = "Favorites", paddingValue = 8.dp)
        }

        OutlinedTextField(
            value = filterText,
            onValueChange = { newText ->
                filterText = newText
                filteredFavorites = libraryFavoritesList?.filter { item ->
                    item.data.first()?.title?.contains(newText, ignoreCase = true) == true
                } ?: emptyList()
            },
            modifier = Modifier.padding(start = 8.dp, bottom = 11.dp),
            placeholder = { Text(text = "Search Favorites...")}
        )


        LazyVerticalStaggeredGrid(
            modifier = Modifier.semantics {
                testTag = "Favorites List"
            },
            columns = StaggeredGridCells.Fixed(gridCells),
            state = lazyGridState
        ) {

            val favoritesToDisplay = if (filterText.isEmpty()) {
                libraryFavoritesList
            } else {
                filteredFavorites
            }

            favoritesToDisplay?.let { list ->
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

                    libraryFavoritesList?.let {
                        ListCard(
                            sendEvent = sendEvent,
                            navController = navController,
                            favorites = libraryFavoritesList,
                            item = item,
                            encodedUrl = encodedUrl,
                            image = image,
                            itemTitle = itemTitle,
                            itemDate = itemDate,
                            mediaTypeString = mediaTypeString,
                            encodedDescription = encodedDescription,
                            imageScaleType = imageScaleType
                        )
                    }
                }
            }
        }
    }
}