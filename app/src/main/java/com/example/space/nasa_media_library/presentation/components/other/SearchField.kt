package com.example.space.nasa_media_library.presentation.components.other

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchField(onSearch: (query: String) -> Unit) {
    var query by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val primaryColor = MaterialTheme.colorScheme.primary

    OutlinedTextField(
        value = query,
        onValueChange = { query = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp),
        label = { Text("Search") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            onSearch(query)
            keyboardController?.hide()
        }),
        colors = myTextFieldColors(primaryColor)
    )
}

// Todo: Move this to a different file
@Composable
fun myTextFieldColors(color: Color): TextFieldColors {
    return TextFieldDefaults.outlinedTextFieldColors(
        cursorColor = color,
        focusedBorderColor = color,
        unfocusedBorderColor = color
    )
}