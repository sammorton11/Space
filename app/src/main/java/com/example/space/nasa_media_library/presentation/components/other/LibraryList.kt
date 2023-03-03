package com.example.space.nasa_media_library.presentation.components.other

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.space.nasa_media_library.domain.models.nasa_media_library_models.Item
import com.example.space.nasa_media_library.presentation.library_search_screen.ListCard

/*
    Todo:
        - Animations aren't working with staggered grid
        - Make a separate
 */

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun LibraryList(
    navController: NavController,
    data: List<Item?>,
    scrollState: LazyStaggeredGridState,
    filterType: MutableState<String>,
    gridCells: Int,
    imageScaleType: ContentScale
) {

    val primaryColor = MaterialTheme.colorScheme.primary
    val backgroundColor = MaterialTheme.colorScheme.background
    val videoCardHeight= 110.dp
    val videoCardWidth = 150.dp


    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(gridCells),
        state = scrollState
    ) {
        items(
            data.filter { it!!.data.first().media_type?.contains(filterType.value) ?: true }
        ) { item ->

            val links = item?.links
            val itemData = item?.data?.first()
            val title = itemData?.title
            val description = itemData?.description
            val mediaType = itemData?.media_type

            ListCard (
                navController = navController,
                item = item,
                primaryColor = primaryColor,
                backgroundColor = backgroundColor,
                links = links,
                title = title,
                description = description,
                mediaType = mediaType,
                imageScaleType = imageScaleType,
                videoCardHeight = videoCardHeight,
                videoCardWidth = videoCardWidth
            )
        }
    }
}
