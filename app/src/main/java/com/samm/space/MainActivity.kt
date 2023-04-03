package com.samm.space

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.samm.space.core.Constants.NO_BACKGROUND
import com.samm.space.core.DataStoreManager
import com.samm.space.nasa_media_library.presentation.view_models.MediaLibraryViewModel
import com.samm.space.presentation.MainScaffold
import com.samm.space.presentation.SideNavigationDrawer
import com.samm.space.ui.theme.SpaceTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/*
    In-Progress:

    Todo:
        ----
        - Add the background type as a value to pass through to the apod screen and details screen
        - Instead of passing mutable states around - lets use the view model instead
        ----
        - Orientation Issue - Details Screen
        - Need to add more UI and integration tests - mock web server is now working
        - Need fake JSON for the details screen
        - Details Screen tests
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
                ) {

                    val navController = rememberNavController()
                    val drawerState = rememberDrawerState(DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
                    val listFilterType = remember { mutableStateOf("") }
                    val backgroundType = remember { mutableStateOf(NO_BACKGROUND) }
                    val title = remember { mutableStateOf("NASA Media Library") }

                    val viewModel: MediaLibraryViewModel = hiltViewModel()
                    viewModel.getData("Mars")

                    SideNavigationDrawer(
                        navController = navController,
                        drawerState = drawerState,
                        scope = scope,
                        title = title
                    ) {
                        MainScaffold(
                            filterType = listFilterType,
                            drawerState = drawerState,
                            scope = scope,
                            backgroundType = backgroundType,
                            title = title,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}