package com.example.space.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

                val paddingValue = 10.dp

                Spacer(modifier = Modifier.padding(top = paddingValue))

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(paddingValue),
                            onClick = {
                                navController.navigate("library_search_screen")
                                scope.launch(Dispatchers.Main) { drawerState.close() }
                            }
                        ) {
                            Text(
                                text = "Media Library",
                                modifier = Modifier.padding(20.dp)
                            )
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(paddingValue),
                            onClick = {
                                navController.navigate("mars_weather_screen")
                                scope.launch(Dispatchers.Main) {
                                    drawerState.close()
                                }
                            }
                        ) {
                            Text(
                                text = "Mars Weather",
                                modifier = Modifier.padding(20.dp)
                            )
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(paddingValue),
                            onClick = {
                                navController.navigate("apod_screen")
                                scope.launch(Dispatchers.Main) {
                                    drawerState.close()
                                }
                            }
                        ) {
                            Text(
                                text = "Picture of the Day",
                                modifier = Modifier.padding(20.dp)
                            )
                        }
                    }
                }
//                Row(
//                    modifier = Modifier.fillMaxWidth()
//                ){
//
//                }

            },
            gesturesEnabled = true
        ) { content() }
    }