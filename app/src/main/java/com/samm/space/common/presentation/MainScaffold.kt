package com.samm.space.common.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.samm.space.navigation.AppNavigation
import com.samm.space.pages.nasa_media_library_page.util.LibraryUiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    event: (LibraryUiEvent) -> Unit,
    drawerState: DrawerState,
    navController: NavHostController
) {

    Scaffold(
        topBar = {
            MyToolbar(
                drawerState = drawerState,
                event = event
            )
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