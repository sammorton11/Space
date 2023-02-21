package com.example.space.presentation.nasa_media_library.library_search_screen.components.other

import android.os.Bundle
import android.provider.Settings.Global.putString
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.space.domain.models.Item
import com.example.space.domain.models.Link
import com.example.space.presentation.NasaLibraryState
import com.example.space.presentation.nasa_media_library.library_search_screen.components.cards.CardImage
import com.example.space.presentation.nasa_media_library.library_search_screen.components.cards.CardTitle
import com.example.space.presentation.nasa_media_library.library_search_screen.components.cards.CardVideo
import com.example.space.presentation.navigation.CardData
import com.example.space.presentation.view_model.VideoDataViewModel

// Todo: - Details navigation arguments - not sure how to pass multiple values to details screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryList(navController: NavController, state: State<NasaLibraryState>) {

    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {
        items(state.value.data) { item ->
            val links = item.links
            val itemData = item.data.first()
            val id = item.data.first().nasa_id

            val title = itemData.title
            val description = itemData.description
            val mediaType = itemData.media_type
            var url = remember { mutableStateOf("") }

            Card(
                modifier = Modifier.padding(15.dp),
                onClick = {
                    // Todo: open the details screen for the selected card using the nasa_id
                    val details = CardData(
                        id = id!!,
                        title = title!!,
                        url = url.toString()
                    )
                    val bundle = Bundle().apply {
                        putString("id", details.id)
                        putString("title", details.title)
                        putString("url", details.url)
                    }
                    navController.navigate("cardDetails/${bundle}")
                }
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    //val (videoLink, imageLink, isImage) = extractLinks(links, mediaType!!, item)

//                    if (isImage) {
//                        CardImage(imageLink = imageLink!!)
//                    } else if (!videoLink.isNullOrEmpty()){
//                       // videoViewModel.getVideoData(videoLink) // Todo: trying to get data here - if not try somewhere else.
////                        CardVideo(
////                            videoUri = videoLink,
////                            videoViewModel = videoViewModel
////                        )
//                        CardImage(imageLink = imageLink)
//                    }
                    var videoLink: String? = null
                    var imageLink: String? = null
                    var isImage = false
                    for (link in links) {
                        Log.d("LINKS!", link.toString())
                        when {
                            link.href?.contains(".jpg") == true -> {
                                imageLink = link.href
                            }
                            link.href?.contains(".mp4") == true -> {

                            }
                        }
                    }
                    CardImage(imageLink = imageLink)
                    CardTitle(title = title)
                    //CardDescription(description = description)
                }
            }

        }
    }
}


private fun extractLinks(links: List<Link>, mediaType: String, item: Item): Triple<String?, String?, Boolean> {
    var videoLink: String? = null
    var imageLink: String? = null
    var isImage = false
    for (link in links) {
        when (mediaType) {
            "image" -> {
                isImage = true
                imageLink = link.href
            }
            "video" -> {
                videoLink = link.href
            }
        }
    }
    return Triple(videoLink, imageLink, isImage)
}