package com.samm.space.common.presentation.labels

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.samm.space.common.presentation.util.DateConverter

@Composable
fun DateLabel(date: String?, padding: Dp = 15.dp) {
    date?.let {
        Text(
            text = DateConverter.formatDisplayDate(it),
            modifier = Modifier
                .padding(padding)
        )
    }
}