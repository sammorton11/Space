package com.samm.space.presentation.buttons

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import com.samm.space.core.Constants

@Composable
fun ExpandButton(expanded: MutableState<Boolean>) {

    var text by remember {
        mutableStateOf("Read More")
    }

    OutlinedButton(
        onClick = {
            expanded.value = !expanded.value
        },
        modifier = Modifier
            .padding(top = 8.dp)
            .width(Constants.buttonWidth)
            .semantics { testTag = "Expand Button" }

    ) {

        text = if (expanded.value) {
            "Show Less"
        } else {
            "Read More"
        }

        Text(text = text)
    }
}