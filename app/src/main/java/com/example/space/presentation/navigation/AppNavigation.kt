package com.example.space.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.space.presentation.mars_weather.MarsWeatherScreen
import com.example.space.presentation.nasa_media_library.details_screen.DetailsScreen
import com.example.space.presentation.nasa_media_library.library_search_screen.LibrarySearchScreen
import com.example.space.presentation.nasa_media_library.view_models.NasaLibraryViewModel
import com.example.space.presentation.nasa_media_library.view_models.VideoDataViewModel
import com.example.space.presentation.mars_weather.view_models.MarsWeatherViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val libraryViewModel: NasaLibraryViewModel = hiltViewModel()
    val videoViewModel: VideoDataViewModel = hiltViewModel()
    val marsWeatherViewModel: MarsWeatherViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "library_search_screen") {
        composable("library_search_screen") {
            LibrarySearchScreen(
                viewModel = libraryViewModel,
                videoViewModel = videoViewModel,
                navController = navController)
        }
        composable(
            "cardDetails/{url}/{description}/{mediaType}",
            arguments = listOf(navArgument("url") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val descriptionId = backStackEntry.arguments?.getString("description")
            val urlId = backStackEntry.arguments?.getString("url")
            val mediaType = backStackEntry.arguments?.getString("mediaType")

            if (urlId != null && descriptionId != null && mediaType != null) {
                DetailsScreen(
                    url = urlId,
                    description = descriptionId,
                    mediaType = mediaType,
                    viewModel = videoViewModel
                )
            }
        }

        composable("mars_weather_screen") {
            MarsWeatherScreen(marsWeatherViewModel)
        }
    }
}