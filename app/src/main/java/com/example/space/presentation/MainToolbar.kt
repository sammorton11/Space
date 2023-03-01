package com.example.space.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.example.space.presentation.nasa_media_library.components.other.Title
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyToolbar(filterType: MutableState<String>, drawerState: DrawerState) {
    var expanded by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val title = "NASA Media Library"
    SmallTopAppBar(
        title = { Title(title, 15.dp) },
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Filled.MoreVert, contentDescription = "More")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                content = {
                    DropdownMenuItem(
                        text = { Text(text = "Images") },
                        onClick = { filterType.value = "image" }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Videos") },
                        onClick = { filterType.value = "video" }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Audio") },
                        onClick = { filterType.value = "audio" }
                    )
                }
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    scope.launch(Dispatchers.Main) {
                        drawerState.open()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "List Filter Types Menu"
                )
            }
        }
    )
}
