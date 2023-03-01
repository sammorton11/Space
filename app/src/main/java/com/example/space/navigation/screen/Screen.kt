package com.example.space.navigation.screen

sealed class Screen(val route: String) {
    object LibrarySearchScreen: Screen("library_search_screen")
    object DetailsScreen: Screen("details_screen") {
        fun createRoute(data: String): String {
            return "details_screen/$data"
        }
    }
}