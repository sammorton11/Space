package com.samm.space.features.picture_of_the_day_page.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.samm.space.core.Constants.buttonWidth
import com.samm.space.features.picture_of_the_day_page.domain.models.Apod
import com.samm.space.features.picture_of_the_day_page.presentation.state.ApodState

@Composable
fun AddFavoriteButton(insert: (Apod) -> Unit, state: ApodState) {
    OutlinedButton(
        modifier = Modifier
            .padding(15.dp)
            .width(buttonWidth),
        onClick = { state.data?.let { insert(it) } }
    ) {
        Text(text = "Add to Favorites")
    }
}