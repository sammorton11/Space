package com.example.space.presentation.mars_rover.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.space.presentation.mars_rover.MarsRoverScreen
import com.example.space.ui.theme.SpaceTheme


@Composable
fun PhotoList(list: List<MarsImages>) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Text(
            text = "Title",
            modifier = Modifier.padding(15.dp),
            style = TextStyle(
                fontSize = 24.sp,
                shadow = Shadow(
                    color = Color.Gray,
                    blurRadius = 13f
                )
            ),
            color = MaterialTheme.colorScheme.onBackground
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp)
        ) {
            items(list) {
                list.forEach { marsImage ->
                    PhotoItem(image = marsImage.image)
                }
            }
        }
    }
}

data class MarsImages(
    val image: String
)

@Preview(showBackground = true)
@Composable
fun MarsRoverScreenPreview() {
    SpaceTheme {
        val list = listOf(MarsImages("https://mars.nasa.gov/msl-raw-images/msss/01000/mcam/1000MR0044631170503677E03_DXXX.jpg"))
        PhotoList(list)
    }
}