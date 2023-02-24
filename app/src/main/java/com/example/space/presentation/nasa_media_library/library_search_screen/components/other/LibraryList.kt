package com.example.space.presentation.nasa_media_library.library_search_screen.components.other

import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.example.space.R.*
import com.example.space.domain.models.Item
import com.example.space.domain.models.Link
import com.example.space.presentation.NasaLibraryState
import com.example.space.presentation.nasa_media_library.library_search_screen.components.cards.CardImage
import com.example.space.presentation.nasa_media_library.library_search_screen.components.cards.CardTitle
import com.example.space.presentation.nasa_media_library.library_search_screen.components.cards.fileTypeCheck
import com.example.space.presentation.nasa_media_library.library_search_screen.components.cards.getUrlList
import com.example.space.presentation.view_model.VideoDataViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/*
    Todo: 
        - handle null pointer exceptions better
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryList(navController: NavController, data: List<Item?>) {

    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {
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
                    var encodedUrl = URLEncoder.encode(url.value, StandardCharsets.UTF_8.toString())
                    val encodedDescription = URLEncoder.encode(description, StandardCharsets.UTF_8.toString())
                    navController.navigate("cardDetails/$encodedUrl/$encodedDescription/$mediaType")
                },
                shape = AbsoluteRoundedCornerShape(10)
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var imageLink: String? = null

//                    for (link in links) {
//                        Log.d("LINKS!", link.toString())
//                        when {
//                            link.href?.contains(".jpg") == true -> {
//                                imageLink = link.href
//                                url.value = imageLink
//                            }
//                            mediaType == "video" -> {
//                                url.value = item.href.toString()
//                            }
//                        }
//                    }
                    item?.let { processLinks(links, mediaType, url, item) }

                    if (mediaType == "image") {
                        CardImage(imageLink = url.value, height = 115.dp, width = 165.dp, ContentScale.FillBounds)
                    }
                    if (mediaType == "video") {
                        CardImage(imageLink = getImageLink(links), height = 110.dp, width = 150.dp, ContentScale.FillBounds)
                    }

                    CardTitle(title = title)
                    if (mediaType != null) {
                        Spacer(modifier = Modifier.padding(10.dp))
                        Text(
                            text = mediaType
                        )
                    }
                }
            }
        }
    }
}

fun processLinks(links: List<Link>?, mediaType: String?, url: MutableState<String>, item: Item) {

    mediaType?.let {
//        if (it == "video" || it == "audio") {
//            url.value = item.href ?: ""
//            return
//        }
        when (it) {
            "video" -> {
                url.value = item.href ?: ""
                return
            }
            "audio" -> {
                url.value = item.href ?: ""
                return
            }
            "image" -> {
                url.value = getImageLink(links = links)
            }
        }
    }
}

fun getImageLink(links: List<Link>?): String {
    var url = ""
    if (links != null) {
        for (link in links) {
            if (!link.href.isNullOrEmpty()) {
                Log.d("Links", link.href)
                link.href.let { href ->
                    if (href.contains(".jpg")) {
                        url = href
                        Log.d("Image for Preview", url)
                    }
                }
            }
        }
    }
    return url
}

@Composable
fun AudioPlayer(url: String, viewModel: VideoDataViewModel) {
    val state = viewModel.state.value.data

    // Fetching the local context
    val mContext = LocalContext.current
    var uri = ""
    val iconSize = 300.dp

    if (state != null) {
        if (state.isNotEmpty()) {
            val uriList = getUrlList(state)
            uri = fileTypeCheck(uriList)
        }
    }
    Log.d("audioUrlHTTPS", uri)

    // Declaring and Initializing
    // the MediaPlayer to play "audio.mp3"
    val mMediaPlayer = MediaPlayer.create(mContext, uri.toUri())

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val paused = remember { mutableStateOf(true) }

        Row {
            // IconButton for Start Action
            IconButton(onClick = {
                paused.value = false
                mMediaPlayer?.let {
                    it.start()
                }
            }) {
                if (paused.value) {
                    Icon(
                        painter = painterResource(id = drawable.baseline_play_circle_24),
                        contentDescription = "",
                        Modifier.size(iconSize),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // IconButton for Pause Action
            IconButton(onClick = {
                paused.value = true
                mMediaPlayer?.let {
                    it.pause()
                }
            }) {
                if (!paused.value) {
                    Icon(
                        painter = painterResource(id = drawable.baseline_pause_circle_outline_24),
                        contentDescription = "",
                        Modifier.size(iconSize),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}