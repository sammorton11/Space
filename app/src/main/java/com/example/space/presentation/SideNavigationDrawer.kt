package com.example.space.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SideNavigationDrawer(navController: NavController, drawerState: DrawerState, scope: CoroutineScope, content: @Composable () -> Unit) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Button(onClick = {
                navController.navigate("mars_weather_screen")
                scope.launch(Dispatchers.Main) {
                    drawerState.close()
                }

            }) {
                Text(text = "Mars Weather")
            }
        },
        gesturesEnabled = true
    ) { content() }
}