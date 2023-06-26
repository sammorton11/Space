package com.samm.space.common.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.samm.space.navigation.AppNavigation
import com.samm.space.features.nasa_media_library_page.util.LibraryUiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    event: (LibraryUiEvent) -> Unit,
    navController: NavController
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        topBar = {
            MyToolbar(
                event = event,
                navBackStackEntry = navBackStackEntry
            )
        },
        bottomBar = {
            BottomNavBar { iconIndex ->
                when (iconIndex) {
                    0 -> {
                        navController.navigate("library_search_screen")
                    }
                    1 -> {
                        navController.navigate("apod_screen")
                    }
                    2 -> {
                        navController.navigate("favorites_screen")
                    }
                }
            }
        }
    ){ padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ){
            AppNavigation(
                navController = navController
            )
        }
    }
}