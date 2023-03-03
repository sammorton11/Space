package com.example.space.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyToolbar(filterType: MutableState<String>, drawerState: DrawerState, scope: CoroutineScope) {
    var expanded by remember { mutableStateOf(false) }
    val title = "NASA Media Library"

    CenterAlignedTopAppBar(
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
                    DropdownMenuItem(
                        text = { Text(text = "All") },
                        onClick = { filterType.value = "" }
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
