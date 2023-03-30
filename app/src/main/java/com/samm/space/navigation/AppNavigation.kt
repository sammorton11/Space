package com.samm.space.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.samm.space.nasa_media_library.presentation.details_screen.DetailsScreen
import com.samm.space.nasa_media_library.presentation.library_search_screen.LibrarySearchScreen
import com.samm.space.nasa_media_library.presentation.view_models.MediaLibraryViewModel
import com.samm.space.nasa_media_library.presentation.view_models.VideoDataViewModel
import com.samm.space.picture_of_the_day.presentation.ApodScreen
import com.samm.space.picture_of_the_day.presentation.ApodViewModel

@Composable
fun AppNavigation(
    filterType: MutableState<String>,
    backgroundType: MutableState<Int>,
    navController: NavHostController
) {

    val videoViewModel: VideoDataViewModel = hiltViewModel()
    val apodViewModel: ApodViewModel = hiltViewModel()
    val libraryViewModel: MediaLibraryViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = "library_search_screen"
    ) {

        composable("library_search_screen") {
            LibrarySearchScreen(
                viewModel = libraryViewModel,
                navController = navController,
                filterType = filterType,
                backgroundType = backgroundType
            )
        }

        composable(
            "cardDetails/{url}/{description}/{mediaType}/{title}/{date}",
            arguments = listOf(navArgument("url") {
                type = NavType.StringType
            })
        ){ backStackEntry ->

            val descriptionId = backStackEntry.arguments?.getString("description")
            val urlId = backStackEntry.arguments?.getString("url")
            val mediaType = backStackEntry.arguments?.getString("mediaType")
            val title = backStackEntry.arguments?.getString("title")
            val date = backStackEntry.arguments?.getString("date")

            if ((urlId != null) && (descriptionId != null) && (mediaType != null)) {
                DetailsScreen(
                    url = urlId,
                    description = descriptionId,
                    mediaType = mediaType,
                    viewModel = videoViewModel,
                    title = title,
                    date = date
                )
            }
        }

        composable("apod_screen"){
            ApodScreen(viewModel = apodViewModel)
        }
    }
}