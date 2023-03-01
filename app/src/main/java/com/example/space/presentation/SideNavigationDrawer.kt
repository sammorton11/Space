package com.example.space.presentation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SideNavigationDrawer(
        navController: NavController,
        drawerState: DrawerState,
        scope: CoroutineScope,
        content: @Composable () -> Unit
    ){
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                Button(onClick = {
                    navController.navigate("library_search_screen")
                    scope.launch(Dispatchers.Main) {
                        drawerState.close()
                    }
                }) {
                    Text(text = "Media Library")
                }
                Button(onClick = {
                    navController.navigate("mars_weather_screen")
                    scope.launch(Dispatchers.Main) {
                        drawerState.close()
                    }
                }) {
                    Text(text = "Mars Weather")
                }
                Button(onClick = {
                    navController.navigate("apod_screen")
                    scope.launch(Dispatchers.Main) {
                        drawerState.close()
                    }
                }) {
                    Text(text = "Picture of the Day")
                }
            },
            gesturesEnabled = true
        ) { content() }
    }