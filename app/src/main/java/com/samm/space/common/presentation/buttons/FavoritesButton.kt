package com.samm.space.common.presentation.buttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.samm.space.pages.nasa_media_library_page.domain.models.Item

@Composable
fun FavoritesButton(
    item: Item,
    favorites: List<Item>,
    onFavoriteClick: () -> Unit,
) {

    var icon by remember {
        mutableStateOf(Icons.Default.FavoriteBorder)
    }

    favorites.forEach {
        if (it.href == item.href) {
            icon = Icons.Default.Favorite
        }
    }

    IconButton(onClick = onFavoriteClick) {
        // Add the favorites icon
        Icon(
            imageVector = icon,
            contentDescription = "Favorites"
        )
    }
}