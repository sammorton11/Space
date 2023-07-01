package com.samm.space.features.favorites_page.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samm.space.common.presentation.labels.Title

@Composable
fun FavoritesScreen(navController: NavController) {

    Column(
        modifier = Modifier.padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Title(text = "Favorites", paddingValue = 5.dp)

        Spacer(modifier = Modifier.padding(bottom = 15.dp))
        
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            onClick = { navController.navigate("library-favorites-screen") }) {
            Text(text = "Library")
        }
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { navController.navigate("apod-favorites-screen") }) {
            Text(text = "APOD")
        }
    }
}