package com.samm.space.features.nasa_media_library_page.presentation.library_search_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import com.samm.space.core.FilterType
import com.samm.space.core.MediaType
import com.samm.space.features.nasa_media_library_page.domain.models.Item
import com.samm.space.features.nasa_media_library_page.presentation.library_search_screen.components.cards.ListCard
import com.samm.space.features.nasa_media_library_page.presentation.library_search_screen.components.cards.ListCardData
import com.samm.space.features.nasa_media_library_page.presentation.state.MediaLibraryState
import com.samm.space.features.nasa_media_library_page.util.LibraryUiEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LibraryListContent(
    state: MediaLibraryState,
    sendEvent: (LibraryUiEvent) -> Unit,
    scrollState: LazyStaggeredGridState,
    gridCells: Int,
    navigate: (route: String) -> Unit,
    filteredList: (data: List<Item?>, filterType: FilterType) -> List<Item?>,
    encodeText: (text: String?) -> String
) {

    LazyVerticalStaggeredGrid(
        modifier = Modifier.semantics {
            testTag = "Library List"
        },
        columns = StaggeredGridCells.Fixed(gridCells),
        state = scrollState
    ) {

        items(filteredList(state.data, state.listFilterType)) { item ->

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
                favorites = state.favorites,
                item = item,
                url = itemMetaDataUrl,
                image = image,
                itemTitle = itemTitle,
                dateText = itemDate,
                mediaTypeString = mediaTypeString,
                description = itemDescription
            )

            ListCard(
                sendEvent = sendEvent,
                encodeText = encodeText,
                state = listCardData,
                navigate = navigate
            )
        }
    }
}