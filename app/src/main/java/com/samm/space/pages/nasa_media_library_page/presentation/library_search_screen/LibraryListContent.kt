package com.samm.space.pages.nasa_media_library_page.presentation.library_search_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.navigation.NavController
import com.samm.space.core.MediaType
import com.samm.space.pages.nasa_media_library_page.domain.models.Item
import com.samm.space.pages.nasa_media_library_page.presentation.library_search_screen.components.cards.ListCard
import com.samm.space.pages.nasa_media_library_page.presentation.library_search_screen.components.cards.ListCardData
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

            val listCardData = ListCardData(
                favorites = favorites,
                item = item,
                url = itemMetaDataUrl,
                image = image,
                itemTitle = itemTitle,
                dateText = itemDate,
                mediaTypeString = mediaTypeString,
                description = itemDescription,
                imageScaleType = imageScaleType
            )

            ListCard(
                sendEvent = sendEvent,
                encodeText = encodeText,
                navController = navController,
                data = listCardData
            )
        }
    }
}