package com.example.space.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.space.R
import com.example.space.core.Constants.NO_BACKGROUND
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyToolbar(
    filterType: MutableState<String>,
    backgroundType: MutableState<Int>,
    drawerState: DrawerState,
    scope: CoroutineScope,
) {
    var expanded by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }
    var expandedChangeBackground by remember { mutableStateOf(false) }
    val title = "NASA Media Library"

    CenterAlignedTopAppBar(
        title = {
            Title(title, 15.dp)
        },
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Filled.MoreVert, contentDescription = "More")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                offset = DpOffset(0.dp, 12.dp),
                content = {
                    DropdownMenuItem(
                        text = { Text(text = "Change Background") },
                        onClick = { expandedChangeBackground = true }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Sort") },
                        onClick = {
                            expanded2 = true
                        }
                    )
                }
            )
            DropdownMenu(
                expanded = expanded2,
                onDismissRequest = { expanded2 = false },
                offset = DpOffset(0.dp, 130.dp)
            ) {
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

            DropdownMenu(
                expanded = expandedChangeBackground,
                onDismissRequest = { expandedChangeBackground = false },
                offset = DpOffset(0.dp, 130.dp)
            ) {
                DropdownMenuItem(
                    text = { Text(text = "Planets Background") },
                    onClick = {
                        backgroundType.value = R.drawable.space_background_01
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Space Man Background") },
                    onClick = {
                        backgroundType.value = R.drawable.space_background_02
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Galaxy Background") },
                    onClick = {
                        backgroundType.value = R.drawable.space_background_03
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "No Background") },
                    onClick = {
                        backgroundType.value = NO_BACKGROUND
                    }
                )
            }
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
