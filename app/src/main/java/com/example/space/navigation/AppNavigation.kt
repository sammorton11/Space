package com.example.space.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.space.mars_weather.presentation.MarsWeatherScreen
import com.example.space.mars_weather.presentation.view_models.MarsWeatherViewModel
import com.example.space.nasa_media_library.presentation.details_screen.DetailsScreen
import com.example.space.nasa_media_library.presentation.library_search_screen.LibrarySearchScreen
import com.example.space.nasa_media_library.presentation.view_models.MediaLibraryViewModel
import com.example.space.nasa_media_library.presentation.view_models.VideoDataViewModel
import com.example.space.picture_of_the_day.presentation.ApodScreen
import com.example.space.picture_of_the_day.presentation.ApodViewModel

@Composable
fun AppNavigation(
    filterType: MutableState<String>,
    navController: NavHostController,
    libraryViewModel: MediaLibraryViewModel
) {
    val videoViewModel: VideoDataViewModel = hiltViewModel()
    val marsWeatherViewModel: MarsWeatherViewModel = hiltViewModel()
    val apodViewModel: ApodViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "library_search_screen") {
        composable("library_search_screen") {
            LibrarySearchScreen(
                viewModel = libraryViewModel,
                videoViewModel = videoViewModel,
                navController = navController,
                filterType = filterType
            )
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
            marsWeatherViewModel.getData()
            MarsWeatherScreen(marsWeatherViewModel)
        }
        composable("apod_screen"){
            apodViewModel.getApodState()
            ApodScreen(viewModel = apodViewModel)
        }
    }
}