package com.samm.space.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.*
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.samm.space.R
import com.samm.space.core.Constants.NO_BACKGROUND
import com.samm.space.presentation.labels.Title
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
    title: MutableState<String>
) {
    var expandedOptionsMenu by remember { mutableStateOf(false) }
    var expandedSortingMenu by remember { mutableStateOf(false) }
    var expandedChangeBackground by remember { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        modifier = Modifier
            .semantics {
                testTag = "Toolbar"
            },
        title = {
            Title(title.value, 15.dp)
        },
        actions = {
            IconButton(
                onClick = { expandedOptionsMenu = true }
            ){
                Icon(Icons.Filled.MoreVert, contentDescription = "More")
            }
            DropdownMenu(
                expanded = expandedOptionsMenu,
                onDismissRequest = { expandedOptionsMenu = false },
                offset = DpOffset(0.dp, 12.dp),
                content = {
                    DropdownMenuItem(
                        text = { Text(text = "Change Background") },
                        onClick = { expandedChangeBackground = true }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Sort") },
                        onClick = {
                            expandedSortingMenu = true
                        }
                    )
                }
            )
            DropdownMenu(
                expanded = expandedSortingMenu,
                onDismissRequest = { expandedSortingMenu = false },
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
