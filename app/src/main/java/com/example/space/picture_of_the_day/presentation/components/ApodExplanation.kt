package com.example.space.picture_of_the_day.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ApodExplantation(text: String?) {
    text?.let { explanation ->
        Text(
            text = explanation,
            modifier = Modifier.padding(20.dp),
            softWrap = true,
        )
    }

}