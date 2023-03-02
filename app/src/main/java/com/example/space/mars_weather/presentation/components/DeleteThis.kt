package com.example.space.mars_weather.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.space.ui.theme.SpaceTheme

@Composable
fun DeleteThis() {
    Text(text = "Vim Commands")

}

@Preview(showBackground = true)
@Composable
fun DeleteThisPreview() {
    SpaceTheme {
        DeleteThis()
    }
}