package com.example.space

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.space.core.Constants.NO_BACKGROUND
import com.example.space.core.DataStoreManager
import com.example.space.presentation.MainScaffold
import com.example.space.presentation.SideNavigationDrawer
import com.example.space.ui.theme.SpaceTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/*
    Todo (Fix):
        - Error response test is failing for some reason
        - ExoPlayer not showing up in tests
        - Image not showing up in details screen for image cards in tests


        Todo:
            - Add a 'Title' key to the app nav route in app navigation
            - Details Screen needs to take a title string as an argument
            - Use the Title composable within the Details Screen composable
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

                    val navController = rememberNavController()
                    val drawerState = rememberDrawerState(DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
                    val filterType = remember { mutableStateOf("") }
                    val backgroundType = remember { mutableStateOf(NO_BACKGROUND) }
                    val title = remember { mutableStateOf("NASA Media Library") }

                    SideNavigationDrawer(
                        navController = navController,
                        drawerState = drawerState,
                        scope = scope,
                        title = title
                    ) {
                        MainScaffold(
                            filterType = filterType,
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