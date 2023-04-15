package com.samm.space.presentation_common

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag

@Composable
fun ProgressBar() {
    CircularProgressIndicator(modifier = Modifier
        .semantics {
            contentDescription = "Progress Bar"
            testTag = "Progress Bar Tag"
        })
}