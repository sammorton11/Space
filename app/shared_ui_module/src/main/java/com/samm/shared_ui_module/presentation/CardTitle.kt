package com.samm.shared_ui_module.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardTitle(title: String?) {

    val materialThemeColor = MaterialTheme.colorScheme.primary

    var textColor by remember {
        mutableStateOf(materialThemeColor)
    }

    if (!isSystemInDarkTheme()) {
        textColor = MaterialTheme.colorScheme.inversePrimary
    }

    title?.let { text ->
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = Bold,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .semantics { testTag = "Card Title" },
            color = textColor,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            softWrap = true,
            textAlign = TextAlign.Center,
            style = TextStyle(fontFamily = FontFamily.SansSerif),
            textDecoration = TextDecoration.None
        )
    }
}

@Preview
@Composable
fun CardTitlePreview() {
    CardTitle(title = "This is a title for a card.")
}