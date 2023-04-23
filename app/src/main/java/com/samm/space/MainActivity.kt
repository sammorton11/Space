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
import com.samm.space.core.DataStoreManager
import com.samm.space.nasa_media_library_page.presentation.view_models.MediaLibraryViewModel
import com.samm.space.presentation_common.MainScaffold
import com.samm.space.presentation_common.SideNavigationDrawer
import com.samm.space.ui.theme.SpaceTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/*

    Todo:
        ----
        - Add the background type as a value to pass through to the apod screen and details screen
        - Orientation Issue - Details Screen
        - Need to add more UI and integration tests - mock web server is now working
        - Need fake JSON for the details screen
        - Details Screen tests
        ----
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
                    val viewModel: MediaLibraryViewModel = hiltViewModel()

                    SideNavigationDrawer(
                        navController = navController,
                        drawerState = drawerState
                    ) {
                        MainScaffold(
                            viewModel = viewModel,
                            drawerState = drawerState,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}