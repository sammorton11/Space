package com.samm.space.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.samm.space.nasa_media_library_page.presentation.details_screen.DetailsScreen
import com.samm.space.nasa_media_library_page.presentation.library_search_screen.MediaLibraryScreen
import com.samm.space.nasa_media_library_page.presentation.view_models.MediaDataViewModel
import com.samm.space.nasa_media_library_page.presentation.view_models.MediaLibraryViewModel
import com.samm.space.picture_of_the_day_page.presentation.ApodScreen
import com.samm.space.picture_of_the_day_page.presentation.ApodViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
) {

    val mediaDataViewModel: MediaDataViewModel = hiltViewModel()
    val apodViewModel: ApodViewModel = hiltViewModel()
    val libraryViewModel: MediaLibraryViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = "library_search_screen"
    ) {

        composable("library_search_screen") {
            MediaLibraryScreen(
                viewModel = libraryViewModel,
                navController = navController
            )
        }

        composable(
            "cardDetails/{url}/{description}/{mediaType}/{title}/{date}",
            arguments = listOf(navArgument("url") {
                type = NavType.StringType
            })
        ) { backStackEntry ->

            val descriptionId = backStackEntry.arguments?.getString("description")
            val urlId = backStackEntry.arguments?.getString("url")
            val mediaType = backStackEntry.arguments?.getString("mediaType")
            val title = backStackEntry.arguments?.getString("title")
            val date = backStackEntry.arguments?.getString("date")

            if ((urlId != null) && (descriptionId != null) && (mediaType != null)) {

                val state = mediaDataViewModel.state.value

                DetailsScreen(
                    metaDataUrl = urlId,
                    description = descriptionId,
                    type = mediaType,
                    title = title,
                    date = date,
                    state = state,
                    getMediaData = mediaDataViewModel::getMediaData,
                    decodeText = mediaDataViewModel::decodeText,
                    getUri = mediaDataViewModel::getUri,
                    extractUrlsFromJsonArray = mediaDataViewModel::extractUrlsFromJsonArray,
                    fileTypeCheck = mediaDataViewModel::fileTypeCheck
                )
            }
        }

        composable("apod_screen") {
            val state = apodViewModel.state
            ApodScreen(stateFlow = state)
        }
    }
}