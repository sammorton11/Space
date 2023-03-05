package com.example.space.nasa_media_library.presentation.library_search_screen

import android.annotation.SuppressLint
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.space.nasa_media_library.presentation.view_models.MediaLibraryViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LibrarySearchScreen(
    viewModel: MediaLibraryViewModel,
    navController: NavController,
    filterType: MutableState<String>,
    backgroundType: MutableState<Int>
) {
    LibraryScreenContent(
        viewModel = viewModel,
        navController = navController,
        filterType = filterType,
        backgroundType = backgroundType
    )
}
@Preview
@Composable
fun LibrarySearchScreenPreview() {

}