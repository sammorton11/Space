package com.example.space

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.space.presentation.navigation.AppNavigation
import com.example.space.presentation.nasa_media_library.view_models.NasaLibraryViewModel
import com.example.space.ui.theme.SpaceTheme
import dagger.hilt.android.AndroidEntryPoint

/*
    Todo:
        - Add test tags to everything - yes.. everything
 */


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: NasaLibraryViewModel = hiltViewModel()
                    viewModel.getData("Nasa Videos")
                    AppNavigation()
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