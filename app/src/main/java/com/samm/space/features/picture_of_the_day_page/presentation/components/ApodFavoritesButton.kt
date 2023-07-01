package com.samm.space.features.picture_of_the_day_page.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import com.samm.space.features.picture_of_the_day_page.domain.models.Apod
import kotlinx.coroutines.delay

@Composable
fun ApodFavoritesButton(
    item: Apod,
    favorites: List<Apod>?,
    insert: (item: Apod) -> Unit,
    delete: (item: Apod) -> Unit
) {

    val inFavoritesList = favorites?.any { it.url == item.url }
    var isFavorite by remember { mutableStateOf(inFavoritesList) }
    var isAnimated by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    val iconColor by animateColorAsState(
        targetValue =
        if (isFavorite == true)
            MaterialTheme.colorScheme.secondary
        else
            MaterialTheme.colorScheme.onSurface
    )

    val scale by animateFloatAsState(
        targetValue = if (isAnimated) 1.2f else 1.0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    LaunchedEffect(isAnimated) {
        if (isAnimated) {
            delay(600) // Delay to hold the enlarged state
            isAnimated = false // Shrink back to regular size
        }
    }

    val icon = if ( isFavorite == true) {
        Icons.Default.Favorite
    } else {
        Icons.Default.FavoriteBorder
    }

    IconButton(
        onClick = {
            if (isFavorite == false) {
                insert(item)
            } else {
                delete(item)
            }
            isFavorite = !isFavorite!!
            isAnimated = true
        },
        interactionSource = interactionSource,
        modifier = Modifier
            .scale(scale)
            .semantics { testTag = "Favorite Button" }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Favorite",
            tint = iconColor
        )
    }
}


