package com.samm.space.navigation

import android.util.Log
import androidx.compose.runtime.Composable
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
import com.samm.space.pages.favorites_page.presentation.FavoriteScreen
import com.samm.space.pages.nasa_media_library_page.presentation.details_screen.DetailsScreen
import com.samm.space.pages.nasa_media_library_page.presentation.library_search_screen.MediaLibraryScreen
import com.samm.space.pages.nasa_media_library_page.presentation.view_models.MediaDataViewModel
import com.samm.space.pages.nasa_media_library_page.presentation.view_models.MediaLibraryViewModel
import com.samm.space.pages.picture_of_the_day_page.presentation.ApodScreen
import com.samm.space.pages.picture_of_the_day_page.presentation.ApodViewModel

@Composable
fun AppNavigation(
    navController: NavController
) {

    val mediaDataViewModel: MediaDataViewModel = hiltViewModel()
    val apodViewModel: ApodViewModel = hiltViewModel()
    val libraryViewModel: MediaLibraryViewModel = hiltViewModel()
    val favorites = libraryViewModel.favorites.collectAsStateWithLifecycle().value
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

            MediaLibraryScreen(
                event = libraryViewModel::sendEvent,
                state = state,
                favorites = favorites,
                navController = navController,
                getSavedSearchText = libraryViewModel::getSavedSearchText,
                listFilterType = filterType,
                backgroundType = backgroundType,
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

            val urlId = backStackEntry.arguments?.getString("url")
            val descriptionId = backStackEntry.arguments?.getString("description")
            val mediaType = backStackEntry.arguments?.getString("mediaType")
            val title = backStackEntry.arguments?.getString("title")
            val date = backStackEntry.arguments?.getString("date")

            val state = mediaDataViewModel.state.value

            if(urlId != null && descriptionId != null && mediaType != null) {
                Log.d("details url", urlId)
                Log.d("details descriptionId", descriptionId)
                Log.d("details url", mediaType)

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
            libraryViewModel.getFavorites()
            val state = libraryViewModel.favoriteState.value

            FavoriteScreen(
                libraryFavoriteState = state,
                sendEvent = libraryViewModel::sendEvent,
                navController = navController,
                encodeText = libraryViewModel::encodeText
            )
        }
    }
}