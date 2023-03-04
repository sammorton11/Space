package com.example.space

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.space.core.Constants.NO_BACKGROUND
import com.example.space.core.DataStoreManager
import com.example.space.nasa_media_library.presentation.view_models.MediaLibraryViewModel
import com.example.space.navigation.AppNavigation
import com.example.space.presentation.MyToolbar
import com.example.space.presentation.SideNavigationDrawer
import com.example.space.ui.theme.SpaceTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/*
    Todo:
        - App icon
        - Broadcast Receiver
 */


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val dataStore = DataStoreManager
            dataStore.init(applicationContext)
        }

        setContent {
            SpaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    val viewModel:  MediaLibraryViewModel = hiltViewModel()
                    viewModel.getData("Black Holes")
                    val navController = rememberNavController()
                    val drawerState = rememberDrawerState(DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
                    val filterType = remember { mutableStateOf("") }
                    val backgroundType = remember { mutableStateOf(NO_BACKGROUND) }

                    SideNavigationDrawer(navController, drawerState, scope) {
                        Scaffold(
                            topBar = {
                                MyToolbar(
                                    filterType = filterType,
                                    drawerState = drawerState,
                                    scope = scope,
                                    backgroundType = backgroundType
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
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SpaceTheme {

    }
}
