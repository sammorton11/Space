package com.example.space.presentation.nasa_media_library.library_search_screen.components.other

import android.util.Log
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
import com.example.space.domain.models.Item
import com.example.space.presentation.nasa_media_library.library_search_screen.components.cards.CardImage
import com.example.space.presentation.nasa_media_library.library_search_screen.components.cards.CardTitle
import com.example.space.presentation.view_model.VideoDataViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/*
    Todo: 
        - handle null pointer exceptions better
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryList(
    navController: NavController,
    data: List<Item?>,
    scrollState: LazyGridState,
    viewModel: VideoDataViewModel
) {

    val imageScaleType = ContentScale.FillBounds
    val primaryColor = MaterialTheme.colorScheme.primary
    val backgroundColor = MaterialTheme.colorScheme.background
    val sweepGradientColors = listOf(primaryColor, backgroundColor)
    val utf8 = StandardCharsets.UTF_8.toString()
    val cardElevation = 15.dp
    val imageCardHeight= 115.dp
    val imageCardWidth = 165.dp
    val videoCardHeight= 110.dp
    val videoCardWidth = 150.dp

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        state = scrollState
    ) {
        items(data) { item ->
            Log.d("Scroll State Value", scrollState.toString())
            val links = item?.links
            val itemData = item?.data?.first()
            val title = itemData?.title
            val description = itemData?.description
            val mediaType = itemData?.media_type
            val url = remember { mutableStateOf("") }

            Card(
                modifier = Modifier.padding(8.dp),
                onClick = {
                    val encodedUrl = URLEncoder.encode(url.value, utf8)
                    val encodedDescription = URLEncoder.encode(description, utf8)
                    navController.navigate(
                        "cardDetails/$encodedUrl/$encodedDescription/$mediaType"
                    )
                },
                border = BorderStroke(1.dp, Brush.sweepGradient(sweepGradientColors, Offset.Zero)),
                shape = AbsoluteRoundedCornerShape(10),
                elevation = CardDefaults.cardElevation(defaultElevation = cardElevation)
            ) {

                Column(
                    modifier = Modifier.padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    item?.let { viewModel.processLinks(links, mediaType, url, item) }

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

                    CardTitle(title = title, color = primaryColor)
                    if (mediaType != null) {
                        Spacer(modifier = Modifier.padding(10.dp))
                        Text(
                            text = mediaType,
                            color = primaryColor
                        )
                    }
                }
            }
        }
    }
}

