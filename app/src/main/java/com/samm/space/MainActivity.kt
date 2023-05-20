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
import com.samm.space.common.presentation.SideNavigationDrawer
import com.samm.media_library.nasa_media_library_page.presentation.view_models.MediaLibraryViewModel
import com.samm.media_library.nasa_media_library_page.util.LibraryUiEvent
import com.samm.space.common.presentation.MainScaffold
import com.samm.space.ui.theme.SpaceTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 *      TODO Issues:
 *          - Orientation changes restarts media players - this must be persisted - use view model?
 *          - Titles in list cards not visible sometimes - need hue or whatever
 *          - More pages - consume another nasa api
 *          - Bottom navigation for screens instead of side navigation
 *          - Maybe have a search icon in the toolbar the opens a search dialog instead of the search field
 *              - pass the view model function to call the data into the main tool bar as a high fun
 *          - Move Favorites button to the right side of the cards instead of the left
 *          - Favorites Screen tests
 *          - Database integration tests
 *          - App is finished once these are fixed and added
 */


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val dataStore = com.samm.core.common.data.DataStoreManager
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
                    val libraryViewModel: MediaLibraryViewModel = hiltViewModel()

                    libraryViewModel.sendEvent(LibraryUiEvent.SearchLibrary("2023"))

                    SideNavigationDrawer(
                        navController = navController,
                        drawerState = drawerState
                    ) {
                        MainScaffold(
                            event = libraryViewModel::sendEvent,
                            drawerState = drawerState,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}