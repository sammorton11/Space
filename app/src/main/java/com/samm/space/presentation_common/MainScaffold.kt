package com.samm.space.presentation_common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.samm.space.navigation.AppNavigation
import com.samm.space.pages.nasa_media_library_page.presentation.view_models.MediaLibraryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    viewModel: MediaLibraryViewModel,
    drawerState: DrawerState,
    navController: NavHostController
) {
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            MyToolbar(
                viewModel = viewModel,
                drawerState = drawerState,
                scope = scope
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