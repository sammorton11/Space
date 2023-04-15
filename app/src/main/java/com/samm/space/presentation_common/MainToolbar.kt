package com.samm.space.presentation_common

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
import com.samm.space.nasa_media_library_page.presentation.view_models.MediaLibraryViewModel
import com.samm.space.presentation_common.labels.Title
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyToolbar(
    viewModel: MediaLibraryViewModel,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    var expandedOptionsMenu by remember { mutableStateOf(false) }
    var expandedSortingMenu by remember { mutableStateOf(false) }
    var expandedChangeBackground by remember { mutableStateOf(false) }

    val changeBackGroundMenuOffset = DpOffset(0.dp, 12.dp)
    val backgroundListMenuOffset = DpOffset(0.dp, 130.dp)
    val sortMenuOffset = DpOffset(0.dp, 130.dp)

    CenterAlignedTopAppBar(
        modifier = Modifier
            .semantics {
                testTag = "Toolbar"
            },
        title = {
            Title("Nasa Media Library", 15.dp)
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
                        viewModel.updateListFilterType("image")
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Videos") },
                    onClick = {
                        viewModel.updateListFilterType("video")
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Audio") },
                    onClick = {
                        viewModel.updateListFilterType("audio")
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "All") },
                    onClick = {
                        viewModel.updateListFilterType("")
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
                        //backgroundType.value = R.drawable.space_background_01
                        viewModel.updateBackgroundType(R.drawable.space_background_01)
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Space Man Background") },
                    onClick = {
                        //backgroundType.value = R.drawable.space_background_02
                        viewModel.updateBackgroundType(R.drawable.space_background_02)
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Galaxy Background") },
                    onClick = {
                        //backgroundType.value = R.drawable.space_background_03
                        viewModel.updateBackgroundType(R.drawable.space_background_03)
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Sci-Fi Planets Background") },
                    onClick = {
                       // backgroundType.value = R.drawable.space_background_04
                        viewModel.updateBackgroundType(R.drawable.space_background_04)
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "No Background") },
                    onClick = {
                        //backgroundType.value = NO_BACKGROUND
                        viewModel.updateBackgroundType(NO_BACKGROUND)
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
