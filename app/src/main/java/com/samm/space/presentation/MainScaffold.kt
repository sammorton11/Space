package com.samm.space.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.samm.space.navigation.AppNavigation
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    filterType: MutableState<String>,
    backgroundType: MutableState<Int>,
    drawerState: DrawerState,
    scope: CoroutineScope,
    title: MutableState<String>,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            MyToolbar(
                filterType = filterType,
                drawerState = drawerState,
                scope = scope,
                backgroundType = backgroundType,
                title = title
            )
        }
    ){ padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ){
            AppNavigation(
                filterType = filterType,
                navController = navController,
                backgroundType = backgroundType
            )
        }
    }
}