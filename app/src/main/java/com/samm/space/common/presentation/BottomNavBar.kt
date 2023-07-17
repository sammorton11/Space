package com.samm.space.common.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class NavItem(var route: String, val icon: ImageVector)

@Composable
fun BottomNavBar(onClick: (index: Int) -> Unit) {

    val iconList = listOf(
            NavItem("library_screen", Icons.Default.Home),
            NavItem("apod_screen", Icons.Default.DateRange),
            NavItem("favorites_screen", Icons.Default.Favorite),
        )


    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {

        iconList.forEachIndexed { index, _ ->
            var title by remember { mutableStateOf("") }
            when(index) {
                0 -> {title = "Library"}
                1 -> {title = "Apod"}
                2 -> {title = "Favorites"}
            }

            IconButton(
                onClick = {
                    onClick(index)
                },
                modifier = Modifier
                    .weight(1f)
                    .semantics { testTag = "$title Screen Navigation Button" }
            ) {
                Column(
                    modifier = Modifier.padding(2.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = iconList[index].icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Text(text = title, fontSize = 11.sp)
                }
            }
        }
    }
}