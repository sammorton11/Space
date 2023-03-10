package com.example.space

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
        - Clicking cards to navigate to Details screen crashes with null pointer exception
        -
        -
        - App navigation composable and List Card composable do not show up when
          using SideNavigationDrawer and MainScaffold as the setup composable.
          These call AppNavigation - Cannot click ListCards to navigate to Details screen in test
          without this.
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SpaceTheme {

    }
}
