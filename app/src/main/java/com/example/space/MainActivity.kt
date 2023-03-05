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
    Todo (Ideas):
        - App icon
        - Broadcast Receiver when download is completed
        - Expandable details card for each type of card
        - Save background state in Data Store
        - Settings page?
        - Favorites page - Media Cards and APOD - separate database or separate table or?
        - Database Cache
        - Links to websites for card data
            - Some cards have links to more information about the media
        - Custom Audio Player or Exoplayer?
            - Custom Audio Player icons need to be able to be resized
            - Image in audio player needs tweaking
            - Exoplayer placeholder?
            - Possibly use a different video player?
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
                    val navController = rememberNavController()
                    val drawerState = rememberDrawerState(DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
                    val filterType = remember { mutableStateOf("") }
                    val backgroundType = remember { mutableStateOf(NO_BACKGROUND) }
                    val title = remember { mutableStateOf("NASA Media Library") }
                    viewModel.getData("Audio")

                    SideNavigationDrawer(navController, drawerState, scope, title) {
                        Scaffold(
                            topBar = {
                                MyToolbar(
                                    filterType = filterType,
                                    drawerState = drawerState,
                                    scope = scope,
                                    backgroundType = backgroundType,
                                    title = title
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
