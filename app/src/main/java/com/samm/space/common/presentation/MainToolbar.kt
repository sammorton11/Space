package com.samm.space.common.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallTopAppBar
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
import androidx.navigation.NavBackStackEntry
import com.samm.space.R
import com.samm.space.common.presentation.labels.Title
import com.samm.space.core.Constants
import com.samm.space.pages.nasa_media_library_page.util.LibraryUiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyToolbar(
    drawerState: DrawerState,
    event: (LibraryUiEvent) -> Unit,
    navBackStackEntry: NavBackStackEntry?
) {

    val current = navBackStackEntry?.destination?.route
    Log.d("route", current.toString())

    var expandedOptionsMenu by remember { mutableStateOf(false) }
    var expandedSortingMenu by remember { mutableStateOf(false) }
    var expandedChangeBackground by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val changeBackGroundMenuOffset = DpOffset(0.dp, 12.dp)
    val backgroundListMenuOffset = DpOffset(0.dp, 130.dp)
    val sortMenuOffset = DpOffset(0.dp, 130.dp)

    SmallTopAppBar(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .semantics {
                testTag = "Toolbar"
            },
        title = {
            Title("Space Explorer", 0.dp)
        },
        actions = {
            if (current == "library_search_screen") {
                IconButton(
                    onClick = { expandedOptionsMenu = true },
                    modifier = Modifier.semantics { testTag = "Options Menu Button" }
                ){
                    Icon(Icons.Filled.MoreVert, contentDescription = "More")
                }
                DropdownMenu(
                    expanded = expandedOptionsMenu,
                    onDismissRequest = { expandedOptionsMenu = false },
                    modifier = Modifier.semantics { testTag = "Options Menu Drop Down" },
                    offset = changeBackGroundMenuOffset,
                    content = {
                        DropdownMenuItem(
                            text = { Text(text = "Change Background") },
                            modifier = Modifier.semantics { testTag = "Change Background Button" },
                            onClick = { expandedChangeBackground = true }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Sort") },
                            modifier = Modifier.semantics { testTag = "Sort Button" },
                            onClick = {
                                expandedSortingMenu = true
                            }
                        )
                    }
                )
                DropdownMenu(
                    expanded = expandedSortingMenu,
                    onDismissRequest = { expandedSortingMenu = false },
                    modifier = Modifier.semantics { testTag = "Sorting Menu Drop Down" },
                    offset = sortMenuOffset
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "Images") },
                        modifier = Modifier.semantics { testTag = "Filter Images Button" },
                        onClick = {
                            event(LibraryUiEvent.UpdateFilterType("image"))
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Videos") },
                        modifier = Modifier.semantics { testTag = "Filter Videos Button" },
                        onClick = {
                            event(LibraryUiEvent.UpdateFilterType("video"))
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Audio") },
                        modifier = Modifier.semantics { testTag = "Filter Audio Button" },
                        onClick = {
                            event(LibraryUiEvent.UpdateFilterType("audio"))
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "All") },
                        modifier = Modifier.semantics { testTag = "Filter All Button" },
                        onClick = {
                            event(LibraryUiEvent.UpdateFilterType(""))
                        }
                    )
                }

                DropdownMenu(
                    expanded = expandedChangeBackground,
                    modifier = Modifier.semantics { testTag = "Change Background Drop Down" },
                    onDismissRequest = { expandedChangeBackground = false },
                    offset = backgroundListMenuOffset
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "Planets Background") },
                        modifier = Modifier.semantics { testTag = "Planets Background Button" },
                        onClick = {
                            event(LibraryUiEvent.ChangeBackground(R.drawable.space_background_01))
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Space Man Background") },
                        modifier = Modifier.semantics { testTag = "Space Man Background Button" },
                        onClick = {

                            event(LibraryUiEvent.ChangeBackground(R.drawable.space_background_02))
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Galaxy Background") },
                        modifier = Modifier.semantics { testTag = "Galaxy Background Button" },
                        onClick = {
                            event(LibraryUiEvent.ChangeBackground(R.drawable.space_background_03))
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Sci-Fi Planets Background") },
                        modifier = Modifier.semantics { testTag = "Sci-Fi Planets Button" },
                        onClick = {
                            event(LibraryUiEvent.ChangeBackground(R.drawable.space_background_04))
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "No Background") },
                        modifier = Modifier.semantics { testTag = "No Background Button" },
                        onClick = {
                            event(LibraryUiEvent.ChangeBackground(Constants.NO_BACKGROUND))
                        }
                    )
                }
            }
        }
    )
}
