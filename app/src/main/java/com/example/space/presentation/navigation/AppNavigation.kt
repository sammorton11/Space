package com.example.space.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.space.presentation.nasa_media_library.details_screen.DetailsScreen
import com.example.space.presentation.nasa_media_library.details_screen.DetailsScreenContent
import com.example.space.presentation.nasa_media_library.library_search_screen.LibrarySearchScreen
import com.example.space.presentation.view_model.NasaLibraryViewModel
import com.example.space.presentation.view_model.VideoDataViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val libraryViewModel: NasaLibraryViewModel = hiltViewModel()
    val videoViewModel: VideoDataViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "library_search_screen") {
        composable("library_search_screen") {
            LibrarySearchScreen(
                viewModel = libraryViewModel,
                videoViewModel = videoViewModel,
                navController = navController)
        }
        composable(
            "cardDetails/{url}/{description}",
            arguments = listOf(navArgument("url") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val descriptionId = backStackEntry.arguments?.getString("description")
            val urlId = backStackEntry.arguments?.getString("url")
            if (urlId != null && descriptionId != null) {
                DetailsScreen(url = urlId, description = descriptionId, viewModel = videoViewModel)
            }
        }
    }
}