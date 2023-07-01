package com.samm.space.features.favorites_page.presentation

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
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samm.space.common.presentation.labels.Title
import com.samm.space.common.presentation.util.WindowInfo
import com.samm.space.common.presentation.util.rememberWindowInfo
import com.samm.space.core.MediaType
import com.samm.space.features.favorites_page.presentation.state.ApodFavoriteState
import com.samm.space.features.nasa_media_library_page.presentation.library_search_screen.components.cards.ListCardData
import com.samm.space.features.picture_of_the_day_page.domain.models.Apod
import com.samm.space.features.picture_of_the_day_page.presentation.components.ApodFavoritesListCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ApodFavoritesScreen(
    state: ApodFavoriteState,
    insert: (item: Apod) -> Unit,
    delete: (item: Apod) -> Unit,
    navigate: (route: String) -> Unit,
    encodeText: (text: String?) -> String
) {
    val apodFavorites = state.apodFavorites?.reversed()
    val lazyGridState = rememberLazyStaggeredGridState()
    val window = rememberWindowInfo()

    val gridCells = when (window.screenWidthInfo) {
        is WindowInfo.WindowType.Compact -> 2
        is WindowInfo.WindowType.Medium -> 3
        is WindowInfo.WindowType.Expanded -> 4
    }

    var filteredFavorites by remember { mutableStateOf(apodFavorites ?: emptyList()) }
    var filterText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxSize()
            .semantics { testTag = "APOD Favorites" }
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Title(text = "APOD Favorites", paddingValue = 8.dp)
        }

        OutlinedTextField(
            value = filterText,
            onValueChange = { newText ->
                filterText = newText
                filteredFavorites = apodFavorites?.filter { item ->
                    item.title.contains(newText, ignoreCase = true)
                } ?: emptyList()
            },
            modifier = Modifier
                .padding(start = 8.dp, bottom = 11.dp)
                .semantics { testTag = "Favorites Search Field" },
            placeholder = { Text(text = "Search Favorites...") }
        )

        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .semantics {
                    testTag = "Favorites List"
                },
            columns = StaggeredGridCells.Fixed(gridCells),
            state = lazyGridState
        ) {

            val favoritesToDisplay = if (filterText.isEmpty()) {
                apodFavorites
            } else {
                filteredFavorites
            }

            favoritesToDisplay?.let { list ->
                items(list.size) { index ->
                    val item = list[index]

                    val image = item.hdurl

                    val itemDescription = item.explanation
                    val itemTitle = item.title
                    val itemDate = item.date

                    apodFavorites?.let {

                        val listCardData = ListCardData(
                            apodFavorites = it,
                            apod = item,
                            url = image,
                            image = image,
                            itemTitle = itemTitle,
                            dateText = itemDate,
                            mediaTypeString = MediaType.IMAGE,
                            description = itemDescription
                        )

                        ApodFavoritesListCard(
                            insert = insert,
                            delete = delete,
                            encodeText = encodeText,
                            state = listCardData,
                            navigate = navigate
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ApodFavoritesScreenPreview() {

    val state = ApodFavoriteState(
        apodFavorites = listOf(
            Apod(
                id = 125,
                copyright = "Copyright 2023",
                date = "2023-06-30",
                explanation = "This is a fake APOD explanation.",
                hdurl = "https://images-assets.nasa.gov/image/NHQ201905310026/NHQ201905310026~small.jpg",
                media_type = "image",
                service_version = "v1",
                title = "Fake APOD",
                url = "https://images-assets.nasa.gov/image/NHQ201905310026/NHQ201905310026~small.jpg"
            )
        )
    )
    ApodFavoritesScreen(state = state, insert = {}, delete = {}, navigate = {}, encodeText = {""})
}