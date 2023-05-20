package com.samm.space.common.presentation

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.samm.media_library.nasa_media_library_page.util.LibraryUiEvent
import com.samm.shared_resources.R
import com.samm.space.common.presentation.labels.Title
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyToolbar(
    drawerState: DrawerState,
    event: (LibraryUiEvent) -> Unit
) {

    var expandedOptionsMenu by remember { mutableStateOf(false) }
    var expandedSortingMenu by remember { mutableStateOf(false) }
    var expandedChangeBackground by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val changeBackGroundMenuOffset = DpOffset(0.dp, 12.dp)
    val backgroundListMenuOffset = DpOffset(0.dp, 130.dp)
    val sortMenuOffset = DpOffset(0.dp, 130.dp)

    CenterAlignedTopAppBar(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .semantics {
                testTag = "Toolbar"
            },
        title = {
            Title("Space Explorer", 15.dp)
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
                offset = changeBackGroundMenuOffset,
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
                offset = sortMenuOffset
            ) {
                DropdownMenuItem(
                    text = { Text(text = "Images") },
                    onClick = {
                        event(LibraryUiEvent.UpdateFilterType("image"))
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Videos") },
                    onClick = {
                        event(LibraryUiEvent.UpdateFilterType("video"))
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Audio") },
                    onClick = {
                        event(LibraryUiEvent.UpdateFilterType("audio"))
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "All") },
                    onClick = {
                        event(LibraryUiEvent.UpdateFilterType(""))
                    }
                )
            }

            DropdownMenu(
                expanded = expandedChangeBackground,
                onDismissRequest = { expandedChangeBackground = false },
                offset = backgroundListMenuOffset
            ) {
                DropdownMenuItem(
                    text = { Text(text = "Planets Background") },
                    onClick = {
                        event(LibraryUiEvent.ChangeBackground(R.drawable.space_background_01))
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Space Man Background") },
                    onClick = {

                        event(LibraryUiEvent.ChangeBackground(R.drawable.space_background_02))
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Galaxy Background") },
                    onClick = {
                        event(LibraryUiEvent.ChangeBackground(R.drawable.space_background_03))
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Sci-Fi Planets Background") },
                    onClick = {
                        event(LibraryUiEvent.ChangeBackground(R.drawable.space_background_04))
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "No Background") },
                    onClick = {
                        event(LibraryUiEvent.ChangeBackground(NO_BACKGROUND))
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
