package com.example.space.presentation.nasa_media_library.components.other

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun GridCellButtons(gridCells: MutableState<Int>) {
    
    Text(text = "Column Amount")
    Row {
        Button(onClick = { gridCells.value = 1 }) {
            Text(text = "1")
        }
        Button(onClick = { gridCells.value = 2 }) {
            Text(text = "2")
        }
        Button(onClick = { gridCells.value = 3 }) {
            Text(text = "3")
        }
        Button(onClick = { gridCells.value = 4 }) {
            Text(text = "4")
        }
    }
}