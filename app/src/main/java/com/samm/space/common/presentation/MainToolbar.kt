package com.samm.space.common.presentation

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.samm.space.R
import com.samm.space.common.presentation.labels.Title
import com.samm.space.core.Constants
import com.samm.space.features.nasa_media_library_page.util.LibraryUiEvent
import java.util.Locale

@Composable
fun MyToolbar(
    event: (LibraryUiEvent) -> Unit,
    navBackStackEntry: NavBackStackEntry?
) {

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val current = navBackStackEntry?.destination?.route
    Log.d("route", current.toString())

    var expandedOptionsMenu by remember { mutableStateOf(false) }
    var expandedSortingMenu by remember { mutableStateOf(false) }
    var expandedChangeBackground by remember { mutableStateOf(false) }

    val changeBackGroundMenuOffset = DpOffset(0.dp, 12.dp)
    val backgroundListMenuOffset = DpOffset(0.dp, 130.dp)
    val sortMenuOffset = DpOffset(0.dp, 130.dp)

    val onTertiaryContainer = MaterialTheme.colorScheme.onTertiaryContainer
    val container = MaterialTheme.colorScheme.onPrimaryContainer

    var selectedItemIndex by remember { mutableStateOf(0) }

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
                        if (isPortrait) {
                            DropdownMenuItem(
                                text = { Text(text = "Change Background") },
                                modifier = Modifier.semantics { testTag = "Change Background Button" },
                                onClick = { expandedChangeBackground = true }
                            )
                        }
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

                    val itemList = listOf("", "image", "video", "audio")

                    itemList.forEachIndexed { index, item ->

                        var label by remember { mutableStateOf("") }
                        label = if (item == "") "All" else item

                        val capitalizedLabel = label.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.ENGLISH
                            ) else it.toString()
                        }

                        DropdownMenuItem(
                            text = { Text(text = capitalizedLabel) },
                            modifier = Modifier.testTag("Filter List Button"),
                            colors = MenuDefaults.itemColors(if (index == selectedItemIndex) onTertiaryContainer else container),
                            onClick = {
                                selectedItemIndex = index
                                event(LibraryUiEvent.UpdateFilterType(item))
                            }
                        )
                    }
                }

                DropdownMenu(
                    expanded = expandedChangeBackground,
                    modifier = Modifier.semantics { testTag = "Change Background Drop Down" },
                    onDismissRequest = { expandedChangeBackground = false },
                    offset = backgroundListMenuOffset
                ) {
                    val backgroundItems = mapOf(
                        "Mountains & Galaxy Background" to R.drawable.space_background_01,
                        "Galaxy Background" to R.drawable.space_background_03,
                        "Planet Background" to R.drawable.space_background_04,
                        "No Background" to Constants.NO_BACKGROUND
                    )

                    backgroundItems.forEach { (text, backgroundResId) ->
                        DropdownMenuItem(
                            text = { Text(text) },
                            modifier = Modifier.semantics { testTag = "$text Button" },
                            onClick = {
                                event(LibraryUiEvent.ChangeBackground(backgroundResId))
                            }
                        )
                    }
                }
            }
        }
    )
}