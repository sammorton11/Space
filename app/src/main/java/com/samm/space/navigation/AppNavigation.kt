package com.samm.space.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.samm.space.core.Constants
import com.samm.space.features.favorites_page.presentation.FavoriteScreen
import com.samm.space.features.nasa_media_library_page.presentation.details_screen.DetailsScreen
import com.samm.space.features.nasa_media_library_page.presentation.library_search_screen.LibraryListScreen
import com.samm.space.features.nasa_media_library_page.presentation.view_models.DetailsViewModel
import com.samm.space.features.nasa_media_library_page.presentation.view_models.MediaLibraryViewModel
import com.samm.space.features.picture_of_the_day_page.presentation.ApodScreen
import com.samm.space.features.picture_of_the_day_page.presentation.ApodViewModel

@Composable
fun AppNavigation(
    navController: NavController
) {

    val mediaDataViewModel: DetailsViewModel = hiltViewModel()
    val apodViewModel: ApodViewModel = hiltViewModel()
    val libraryViewModel: MediaLibraryViewModel = hiltViewModel()

    val favorites = libraryViewModel.favoriteState.collectAsState().value
    libraryViewModel.getAllFavorites()

    NavHost(
        navController = navController as NavHostController,
        startDestination = "library_search_screen"
    ) {

        composable("library_search_screen") {
            val state = libraryViewModel.state.value

            val backgroundType = libraryViewModel.backgroundType
                .observeAsState(initial = Constants.NO_BACKGROUND).value

            val filterType = libraryViewModel.listFilterType
                .observeAsState("").value

            val updatedState = state.copy(
                favorites = favorites.libraryFavorites,
                listFilterType = filterType,
                backgroundType = backgroundType
            )

            LibraryListScreen(
                event = libraryViewModel::sendEvent,
                state = updatedState,
                navigate = navController::navigate,
                getSavedSearchText = libraryViewModel::getSavedSearchText,
                filteredList = libraryViewModel::filterList,
                encodeText = libraryViewModel::encodeText
            )
        }

        composable(
            route = "cardDetails/{url}/{description}/{mediaType}/{title}/{date}",
            arguments = listOf(
                navArgument("url") { type = NavType.StringType },
                navArgument("description") { type = NavType.StringType },
                navArgument("mediaType") { type = NavType.StringType },
                navArgument("title") { type = NavType.StringType },
                navArgument("date") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val state = mediaDataViewModel.state.collectAsStateWithLifecycle().value

            val urlId = backStackEntry.arguments?.getString("url")
            val descriptionId = backStackEntry.arguments?.getString("description")
            val mediaType = backStackEntry.arguments?.getString("mediaType")
            val title = backStackEntry.arguments?.getString("title")
            val date = backStackEntry.arguments?.getString("date")


            if(urlId != null && descriptionId != null && mediaType != null) {

                val updatedState = state.copy(
                    date = date,
                    description = descriptionId,
                    title = title,
                    metaDataUrl = urlId,
                    type = mediaType
                )

                DetailsScreen(
                    state = updatedState,
                    getMediaData = mediaDataViewModel::getMediaData,
                    getUri = mediaDataViewModel::getUri
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
            FavoriteScreen(
                libraryFavoriteState = favorites,
                sendEvent = libraryViewModel::sendEvent,
                navigate = navController::navigate,
                encodeText = libraryViewModel::encodeText
            )
        }
    }
}