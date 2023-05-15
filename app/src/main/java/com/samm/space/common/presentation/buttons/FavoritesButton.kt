package com.samm.space.common.presentation.buttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.samm.space.pages.nasa_media_library_page.domain.models.Item
import com.samm.space.pages.nasa_media_library_page.presentation.view_models.MediaLibraryViewModel
import com.samm.space.pages.nasa_media_library_page.util.LibraryUiEvent


/*
    Todo:
     - Redo the favorite page and button logic I'm not happy with the quality.
 */

@Composable
fun FavoritesButton(
    item: Item,
    favorites: List<Item>,
    event: (LibraryUiEvent) -> Unit,
) {

    var icon by remember {
        mutableStateOf(Icons.Default.FavoriteBorder)
    }

    if (favorites.any { it.href == item.href }) {
        icon = Icons.Default.Favorite
    }


    IconButton(
        onClick = {
            event(LibraryUiEvent.ToggleFavorite(item))
            if (icon == Icons.Default.Favorite) {
                icon = Icons.Default.FavoriteBorder
            }
        }
    ) {
        Icon(
            imageVector = icon, contentDescription = null
        )
    }
}

