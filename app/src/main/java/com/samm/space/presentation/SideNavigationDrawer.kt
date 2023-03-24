package com.samm.space.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
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
        title: MutableState<String>,
        content: @Composable () -> Unit
    ){
        ModalNavigationDrawer(
            drawerState = drawerState,
            modifier = Modifier.semantics { testTag = "Navigation Drawer" },
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
                                title.value = "Nasa Media Library"
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
                                .padding(paddingValue)
                                .semantics { testTag = "Apod Screen Button" },
                            onClick = {
                                navController.navigate("apod_screen")
                                title.value = "Picture of the Day"
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
            },
            gesturesEnabled = true
        ) { content() }
    }