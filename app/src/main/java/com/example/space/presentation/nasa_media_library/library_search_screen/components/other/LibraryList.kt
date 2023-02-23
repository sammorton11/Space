package com.example.space.presentation.nasa_media_library.library_search_screen.components.other

import android.graphics.drawable.shapes.Shape
import android.util.Log
import android.webkit.URLUtil
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.space.domain.models.Item
import com.example.space.domain.models.Link
import com.example.space.presentation.NasaLibraryState
import com.example.space.presentation.nasa_media_library.library_search_screen.components.cards.CardImage
import com.example.space.presentation.nasa_media_library.library_search_screen.components.cards.CardTitle
import com.example.space.presentation.navigation.CardData
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/*
    Todo: 
        - Details navigation arguments - not sure how to pass multiple values to details screen
        - This is a mess lol. FIX IT..

 */

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
            val dateCreated = itemData.date_created
            val secondaryCreator = itemData.secondary_creator
            val description508 = itemData.description_508
            val center = itemData.center

            var url = remember { mutableStateOf("") }

            Card(
                modifier = Modifier.padding(8.dp),
                onClick = {
                    Log.d("url value", url.value)
                    var encodedUrl = URLEncoder.encode(url.value, StandardCharsets.UTF_8.toString())
                    val encodedDescription = URLEncoder.encode(description, StandardCharsets.UTF_8.toString())
                    navController.navigate("cardDetails/$encodedUrl/$encodedDescription")
                },
                shape = AbsoluteRoundedCornerShape(10)
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var imageLink: String? = null

                    for (link in links) {
                        Log.d("LINKS!", link.toString())
                        when {
                            link.href?.contains(".jpg") == true -> {
                                imageLink = link.href
                                url.value = imageLink
                            }
                            mediaType == "video" -> {
                                url.value = item.href.toString()
                            }
                        }
                    }
                    Card(
                        modifier = Modifier.padding(10.dp),
                        shape = AbsoluteRoundedCornerShape(10)
                    ) {
                        CardImage(imageLink = imageLink, height = 110.dp, width = 150.dp)
                    }

                    CardTitle(title = title)
                    if (mediaType != null) {
                        Spacer(modifier = Modifier.padding(10.dp))
                        Text(text = mediaType)
                    }
                }
            }
        }
    }
}

fun encodeUrl(url: String): String {
    var encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
    if (encodedUrl.endsWith("/")) {
        encodedUrl = encodedUrl.substring(0, encodedUrl.length - 1)
    }
    if (URLUtil.isValidUrl(url)) {
        return encodedUrl
    }
    Log.d("encodedUrl", encodedUrl)
    return "Not Found"
}