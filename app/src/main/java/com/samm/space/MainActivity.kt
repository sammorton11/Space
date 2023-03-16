package com.samm.space

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
import com.samm.space.core.Constants.NO_BACKGROUND
import com.samm.space.core.DataStoreManager
import com.samm.space.presentation.MainScaffold
import com.samm.space.presentation.SideNavigationDrawer
import com.samm.space.ui.theme.SpaceTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/*
    In-Progress:

    Todo (Fix):
        - ExoPlayer not showing up in tests
        - Image not showing up in details screen for image cards in tests
        - Replace hardcoded strings
        - All error tests are failing - I must be handling errors incorrectly

        Todo:
            - Network logic shouldn't be in the main activity but im not sure how to handle missing network errors
            - Try StateFlow instead of State/MutableState
            - Alright.. didn't need to use Response<T> for the get request response - can remove that from the rest of the code
            - Progress bar or placeholder image for apod screen when image is loading
            - Options menu should change when on details or apod screens

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