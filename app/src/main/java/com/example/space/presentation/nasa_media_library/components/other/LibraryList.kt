package com.example.space.presentation.nasa_media_library.components.other

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
import com.example.space.presentation.nasa_media_library.components.cards.CardImage
import com.example.space.presentation.nasa_media_library.components.cards.CardTitle
import com.example.space.presentation.view_model.VideoDataViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

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
    val imageCardHeight= 115.dp
    val imageCardWidth = 165.dp
    val videoCardHeight= 110.dp
    val videoCardWidth = 150.dp

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = scrollState
    ) {
        items(data) { item ->
            val links = item?.links
            val itemData = item?.data?.first()
            val title = itemData?.title
            val description = itemData?.description
            val mediaType = itemData?.media_type
            val url = remember { mutableStateOf("") }

            Card(
                modifier = Modifier.padding(8.dp),
                onClick = {
                    Log.d("url value", url.value)
                    val encodedUrl = URLEncoder.encode(url.value, StandardCharsets.UTF_8.toString())
                    val encodedDescription = URLEncoder.encode(description, StandardCharsets.UTF_8.toString())
                    navController.navigate("cardDetails/$encodedUrl/$encodedDescription/$mediaType")
                },
                border = BorderStroke(1.dp, Brush.sweepGradient(listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.background), Offset.Zero)),
                shape = AbsoluteRoundedCornerShape(10),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 15.dp
                )
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

//fun processLinks(links: List<Link>?, mediaType: String?, url: MutableState<String>, item: Item) {
//
//    mediaType?.let {
//        when (it) {
//            "video" -> {
//                url.value = item.href ?: ""
//                return
//            }
//            "audio" -> {
//                url.value = item.href ?: ""
//                return
//            }
//            "image" -> {
//                url.value = getImageLink(links = links)
//            }
//        }
//    }
//}
//
//fun getImageLink(links: List<Link>?): String {
//    return links?.let { findImageLink(it) } ?: ""
//}
//
//fun findImageLink(links: List<Link>): String {
//    links.forEach { url ->
//        url.href?.let { nonNullUrl ->
//            if (nonNullUrl.contains(".jpg")) {
//                return url.href
//            }
//        }
//    }
//    return ""
//}

