package com.samm.space.nasa_media_library.presentation.library_search_screen.components.other

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.imeAction
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.samm.space.nasa_media_library.util.myTextFieldColors

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchField(
    onSearch: (query: String) -> Unit,
    savedQuery: String?
){

    var query by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val primaryColor = MaterialTheme.colorScheme.primary

    OutlinedTextField(
        value = query,
        onValueChange = { query = it },
        trailingIcon = {
            Icon(
                Icons.Default.Clear,
                contentDescription = "clear text",
                modifier = Modifier
                    .clickable {
                        query = ""
                    }
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 15.dp)
            .semantics {
                testTag = "Search Field"
                imeAction = ImeAction.Search
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
                Text(
                    text = it,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    )
}
