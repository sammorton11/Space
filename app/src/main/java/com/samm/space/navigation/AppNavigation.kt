package com.samm.space.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.samm.space.core.Constants
import com.samm.space.pages.favorites_page.presentation.FavoriteScreen
import com.samm.space.pages.favorites_page.presentation.FavoritesViewModel
import com.samm.space.pages.nasa_media_library_page.presentation.details_screen.DetailsScreen
import com.samm.space.pages.nasa_media_library_page.presentation.library_search_screen.MediaLibraryScreen
import com.samm.space.pages.nasa_media_library_page.presentation.view_models.MediaDataViewModel
import com.samm.space.pages.nasa_media_library_page.presentation.view_models.MediaLibraryViewModel
import com.samm.space.pages.picture_of_the_day_page.presentation.ApodScreen
import com.samm.space.pages.picture_of_the_day_page.presentation.ApodViewModel

@Composable
fun AppNavigation(
    navController: NavHostController
) {

    val mediaDataViewModel: MediaDataViewModel = hiltViewModel()
    val apodViewModel: ApodViewModel = hiltViewModel()
    val libraryViewModel: MediaLibraryViewModel = hiltViewModel()
    val favoritesViewModel: FavoritesViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = "library_search_screen"
    ) {

        composable("library_search_screen") {
            val state = libraryViewModel.state.value
            val backgroundType = libraryViewModel.backgroundType
                .observeAsState(initial = Constants.NO_BACKGROUND)
                .value
            val filterType = libraryViewModel.listFilterType
                .observeAsState("")
                .value

            MediaLibraryScreen(
                event = libraryViewModel::sendEvent,
                state = state,
                navController = navController,
                getSavedSearchText = libraryViewModel::getSavedSearchText,
                listFilterType = filterType,
                backgroundType = backgroundType,
                filteredList = libraryViewModel::filterList,
                encodeText = libraryViewModel::encodeText
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
                    extractUrlsFromJsonArray = mediaDataViewModel::createJsonArrayFromString,
                    fileTypeCheck = mediaDataViewModel::fileTypeCheck
                )
            }
        }

        composable("apod_screen") {
            val state = apodViewModel.state
            ApodScreen(
                stateFlow = state,
                refresh = apodViewModel::getApodState
            )
        }

        composable("favorites_screen") {
            favoritesViewModel.getFavorites()
            val state = favoritesViewModel.state.value
            FavoriteScreen(
                libraryFavoriteState = state,
                navController = navController,
                encodeText = libraryViewModel::encodeText
            )
        }
    }
}