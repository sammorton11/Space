package com.example.space.nasa_media_library.presentation.components.other

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.space.core.Constants.utf8Encoding
import com.example.space.nasa_media_library.domain.models.nasa_media_library_models.Item
import com.example.space.nasa_media_library.presentation.components.cards.CardImage
import com.example.space.nasa_media_library.presentation.components.cards.CardTitle
import com.example.space.nasa_media_library.presentation.components.cards.MediaTypeLabel
import com.example.space.nasa_media_library.presentation.view_models.VideoDataViewModel
import java.net.URLEncoder

/*
    Todo:
        - processLinks is giving the wrong urls
        - could be something else
        - list screen is displaying the wrong images for the cards ; some are duplicates
        - Image details screen is not getting image data
        - Audio details screen probably isnt either
        - encoding issue?
        - How to be sure that i am using the correct data for the correct card
        - May need to do the same thing for the image "items" that i am doing for the videos;
            - use the .json to request the image data instead of using the inages from the links
            - might be cleaner that way
 */

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun LibraryList(
    navController: NavController,
    data: List<Item?>,
    scrollState: LazyStaggeredGridState,
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
    var url by remember { mutableStateOf("") }

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(gridCells),
        state = scrollState
    ) {
        items(data.filter {
            it!!.data.first().media_type?.contains(filterType.value) ?: true
        }) { item ->
            val links = item?.links
            val itemData = item?.data?.first()
            val title = itemData?.title
            val description = itemData?.description
            val mediaType = itemData?.media_type
            val cardImage = links?.first()?.href
            item?.let {
                url = viewModel.processLinks(
                    links = links,
                    mediaType = mediaType,
                    item = it
                ) ?: ""
            }

            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                onClick = {
                    val encodedUrl = URLEncoder.encode(url, utf8Encoding)
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
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    when (mediaType) {
                        "image" -> {
                            CardImage(
                                imageLink = cardImage,
                                height = imageCardHeight,
                                width = imageCardWidth,
                                scale = imageScaleType
                            )
                        }
                        "video" -> {
                            CardImage(
                                imageLink = cardImage,
                                height = videoCardHeight,
                                width = videoCardWidth,
                                scale = imageScaleType
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CardTitle(title = title, color = primaryColor)
                    MediaTypeLabel(mediaType = mediaType, color = primaryColor)
                }
            }
        }
    }
}
