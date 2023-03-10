package com.example.space.nasa_media_library.presentation.components.other

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.space.nasa_media_library.util.myTextFieldColors

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchField(onSearch: (query: String) -> Unit, savedQuery: String?) {
    var query by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val primaryColor = MaterialTheme.colorScheme.primary

    OutlinedTextField(
        value = query,
        onValueChange = { query = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .semantics {
               testTag = "Search"
            },
        label = { Text("Search") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            onSearch(query)
            keyboardController?.hide()
        }),
        colors = myTextFieldColors(primaryColor),
        placeholder = {
            savedQuery?.let {
                Text(text = it)
            }
        }
    )
}
