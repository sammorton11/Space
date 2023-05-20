package com.samm.media_library.nasa_media_library_page.presentation.library_search_screen

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
import com.samm.core.domain.library_models.Item
import com.samm.media_library.nasa_media_library_page.util.LibraryUiEvent
import com.samm.shared_resources.util.MediaType
import com.samm.shared_ui_module.presentation.ListCard

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
        userScrollEnabled = true,
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

            ListCard(
                sendEvent = sendEvent,
                navController = navController,
                favorites = favorites,
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