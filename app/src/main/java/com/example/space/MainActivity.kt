package com.example.space

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.space.presentation.MyToolbar
import com.example.space.presentation.SideNavigationDrawer
import com.example.space.presentation.navigation.AppNavigation
import com.example.space.presentation.nasa_media_library.view_models.NasaLibraryViewModel
import com.example.space.ui.theme.SpaceTheme
import dagger.hilt.android.AndroidEntryPoint

/*
    Todo:
        - Add test tags to everything - yes.. everything
        - Landscape mode causing issues for videos in description screen
            - Video restarts when phone switches orientation
            - Video screen is too large when in landscape
 */


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: NasaLibraryViewModel = hiltViewModel()
                    viewModel.getData("Nasa Videos")
                    val navController = rememberNavController()
                    val gridCells = remember { mutableStateOf(2) }
                    val drawerState = rememberDrawerState(DrawerValue.Closed)
                    val scope = rememberCoroutineScope()

                    SideNavigationDrawer(navController, drawerState, scope) {
                        Scaffold(topBar = { MyToolbar(gridCells, navController, drawerState)} ) { padding ->
                            Box(modifier = Modifier.fillMaxSize().padding(padding)) {
                                AppNavigation(drawerState, gridCells, navController)
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SpaceTheme {
        Greeting("Android")
    }
}
