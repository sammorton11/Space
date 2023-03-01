package com.example.space.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.space.presentation.nasa_media_library.components.other.Title
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyToolbar(gridCells: MutableState<Int>, navController: NavController, drawerState: DrawerState) {
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
                    DropdownMenuItem(text = { Text(text = "1 column") }, onClick = { gridCells.value = 1 })
                    DropdownMenuItem(text = { Text(text = "2 columns") }, onClick = { gridCells.value = 2 })
                    DropdownMenuItem(text = { Text(text = "3 columns") }, onClick = { gridCells.value = 3 })
                    DropdownMenuItem(text = { Text(text = "4 columns") }, onClick = { gridCells.value = 4 })
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
                    contentDescription = "stringResource(R.string.drawer_toggle)"
                )
            }
        }
    )
}
