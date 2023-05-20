package com.samm.media_library.nasa_media_library_page.util

import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun myTextFieldColors(color: Color): TextFieldColors {
    return TextFieldDefaults.outlinedTextFieldColors(
        cursorColor = color,
        focusedBorderColor = color,
        unfocusedBorderColor = color
    )
}