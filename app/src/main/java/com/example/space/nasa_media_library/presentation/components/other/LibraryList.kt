package com.example.space.nasa_media_library.presentation.components.other

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.navigation.NavController
import com.example.space.nasa_media_library.domain.models.nasa_media_library_models.Item
import com.example.space.nasa_media_library.presentation.library_search_screen.ListCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LibraryList(
    navController: NavController,
    data: List<Item?>,
    scrollState: LazyStaggeredGridState,
    filterType: MutableState<String>,
    gridCells: Int,
    imageScaleType: ContentScale
) {
    val secondaryColor = MaterialTheme.colorScheme.secondary

    LazyVerticalStaggeredGrid(
        modifier = Modifier.semantics { testTag = "Library List" },
        columns = StaggeredGridCells.Fixed(gridCells),
        state = scrollState
    ) {
        items(
            data.filter { item ->
                item!!.data.first().media_type?.contains(filterType.value) ?: false
            }
        ) { item ->

            val links = item?.links // meta data urls
            val itemData = item?.data?.first() // json url to request meta data
            val title = itemData?.title
            val description = itemData?.description
            val mediaType = itemData?.media_type

            ListCard(
                navController = navController,
                item = item,
                color = secondaryColor,
                links = links,
                title = title,
                description = description,
                mediaType = mediaType,
                imageScaleType = imageScaleType
            )
        }
    }
}
