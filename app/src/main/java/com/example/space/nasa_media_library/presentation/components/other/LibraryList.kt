package com.example.space.nasa_media_library.presentation.components.other

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.space.R.*
import com.example.space.core.Constants.utf8Encoding
import com.example.space.nasa_media_library.domain.models.nasa_media_library_models.Item
import com.example.space.nasa_media_library.presentation.components.cards.CardImage
import com.example.space.nasa_media_library.presentation.components.cards.CardTitle
import com.example.space.nasa_media_library.presentation.components.cards.MediaTypeLabel
import com.example.space.nasa_media_library.presentation.view_models.VideoDataViewModel
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryList(
    navController: NavController,
    data: List<Item?>,
    scrollState: LazyGridState,
    viewModel: VideoDataViewModel,
    filterType: MutableState<String>,
    gridCells: Int
) {

    val imageScaleType = ContentScale.FillBounds
    val primaryColor = MaterialTheme.colorScheme.primary
    val backgroundColor = MaterialTheme.colorScheme.background
    val imageCardHeight= 115.dp
    val imageCardWidth = 165.dp
    val videoCardHeight= 110.dp
    val videoCardWidth = 150.dp


    LazyVerticalGrid(
        columns = GridCells.Fixed(gridCells),
        state = scrollState
    ) {
        items(data.filter { it!!.data.first().media_type?.contains(filterType.value) ?: true }) { item ->
            val links = item?.links
            val itemData = item?.data?.first()
            val title = itemData?.title
            val description = itemData?.description
            val mediaType = itemData?.media_type
            val url = remember { mutableStateOf("") }

            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                onClick = {
                    val encodedUrl = URLEncoder.encode(url.value, utf8Encoding)
                    val encodedDescription = URLEncoder.encode(description, utf8Encoding)
                    navController.navigate(
                        "cardDetails/$encodedUrl/$encodedDescription/$mediaType"
                    )
                },
                border = BorderStroke(
                    width = 1.dp,
                    brush = Brush.sweepGradient(
                        colors = listOf(primaryColor, backgroundColor),
                        center = Offset.Zero
                    )
                ),
                shape = AbsoluteRoundedCornerShape(10),
                elevation = CardDefaults.cardElevation(defaultElevation = 15.dp)
            ) {
                Column(
                    modifier = Modifier.padding(10.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    item?.let {
                        viewModel.processLinks(
                            links = links,
                            mediaType = mediaType,
                            url = url,
                            item = item
                        )
                    }
                    when (mediaType) {
                        "image" -> {
                            CardImage(
                                imageLink = url.value,
                                height = imageCardHeight,
                                width = imageCardWidth,
                                scale = imageScaleType
                            )
                        }
                        "video" -> {
                            CardImage(
                                imageLink = viewModel.getImageLink(links),
                                height = videoCardHeight,
                                width = videoCardWidth,
                                scale = imageScaleType
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CardTitle(title = title, color = primaryColor)
                    MediaTypeLabel(mediaType = mediaType, color = primaryColor)
                }
            }
        }
    }
}
