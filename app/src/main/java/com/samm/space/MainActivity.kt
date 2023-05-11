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
import com.samm.space.pages.nasa_media_library_page.presentation.view_models.MediaLibraryViewModel
import com.samm.space.common.presentation.MainScaffold
import com.samm.space.common.presentation.SideNavigationDrawer
import com.samm.space.ui.theme.SpaceTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/*

    Todo:
        ----
        - Need to add more UI and integration tests - mock web server is now working
        - Refactoring to a single repo - media library repo, media details repo, apod repo
        -

        - FAVORITES DATABASE:
            - Create database class - annotations
            - Create Dao interface - annotations
            - Create Dao functions
            - Inject Dao to Repositories
            - Use Dao functions Dao->Repo->ViewModels
            - Add the response data to the database
            - Use this data for the Favorites Screen
            - Favorites Screen should be copy of the Main Screen
              but use the data from the database instead
            - Create Favorites Screen State
            - Set up Repo and ViewModel for Favorite Screen
            - Set up Favorites Screen like the Main Screen

            How do I use the data models as tables in db?

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
                            event = viewModel::sendEvent,
                            drawerState = drawerState,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}