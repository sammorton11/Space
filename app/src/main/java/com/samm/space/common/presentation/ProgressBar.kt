package com.samm.space.common.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBar() {
    CircularProgressIndicator(
        modifier = Modifier
            .padding(25.dp)
            .semantics {
                contentDescription = "Progress Bar"
                testTag = "Progress Bar Tag"
            })
}