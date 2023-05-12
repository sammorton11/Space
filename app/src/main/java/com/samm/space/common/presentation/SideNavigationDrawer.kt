package com.samm.space.common.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SideNavigationDrawer(
    navController: NavController,
    drawerState: DrawerState,
    content: @Composable () -> Unit
){
    val scope = rememberCoroutineScope()

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

                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(paddingValue)
                            .semantics { testTag = "Favorites Screen Button" },
                        onClick = {
                            navController.navigate("favorites_screen")
                            scope.launch(Dispatchers.Main) {
                                drawerState.close()
                            }
                        }
                    ) {
                        Text(
                            text = "Favorites",
                            modifier = Modifier.padding(20.dp)
                        )
                    }
                }
            }
        },
        gesturesEnabled = true
    ) { content() }
}