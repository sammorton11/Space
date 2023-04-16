package com.samm.space.nasa_media_library_page.presentation.library_search_screen.components.other

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.navigation.NavController
import com.samm.space.core.MediaType
import com.samm.space.nasa_media_library_page.domain.models.Item
import com.samm.space.nasa_media_library_page.presentation.library_search_screen.components.cards.ListCard
import com.samm.space.nasa_media_library_page.presentation.view_models.MediaLibraryViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LibraryListContent(
    navController: NavController,
    viewModel: MediaLibraryViewModel,
    filterType: State<String>,
    data: List<Item?>,
    scrollState: LazyStaggeredGridState,
    gridCells: Int,
    imageScaleType: ContentScale
) {
    val secondaryColor = MaterialTheme.colorScheme.secondary

    LazyVerticalStaggeredGrid(
        modifier = Modifier.semantics {
            testTag = "Library List"
        },
        columns = StaggeredGridCells.Fixed(gridCells),
        state = scrollState
    ) {

        items(
            // Todo: move the null check and type check into a separate function.

            /**
             *  Filter out items containing the filter type value - image, video, or audio
             */
            data.filter { item ->
                val dataList = item?.data?.first()
                val mediaType = dataList?.media_type
                filterType.value.let { mediaType?.contains(it) } ?: false
            }
        ) { item ->

            val itemListOfLinks = item?.links
            val itemMetaDataUrl = item?.href
            val itemData = item?.data?.first()
            val itemDescription = itemData?.description
            val itemTitle = itemData?.title
            val itemDate = itemData?.date_created
            val mediaTypeString = MediaType.fromString(itemData?.media_type?: "image")
                ?: MediaType.IMAGE

            val encodedUrl = viewModel.encodeText(itemMetaDataUrl) // pass this as an arg instead of vm
            val encodedDescription = viewModel.encodeText(itemDescription) // pass this as an arg instead of vm

            ListCard(
                navController = navController,
                metaDataUrl = encodedUrl,
                color = secondaryColor,
                links = itemListOfLinks,
                title = itemTitle,
                date = itemDate,
                description = encodedDescription,
                mediaType = mediaTypeString,
                imageScaleType = imageScaleType
            )
        }
    }
}