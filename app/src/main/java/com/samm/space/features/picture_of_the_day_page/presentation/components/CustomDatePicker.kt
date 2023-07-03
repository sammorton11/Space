package com.samm.space.features.picture_of_the_day_page.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun CustomDatePicker(
    onDateSelected: (String) -> Unit,
    initialDate: String
) {
    var selectedDate by remember { mutableStateOf(initialDate) }
    var showDialog by remember { mutableStateOf(false) }

    Column {
        TextField(
            value = selectedDate,
            onValueChange = { selectedDate = it },
            readOnly = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            colors = TextFieldDefaults.textFieldColors(MaterialTheme.colorScheme.surface)
        )

        Button(
            onClick = { showDialog = true },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(text = "Select Date")
        }

        if (showDialog) {
            Dialog(
                onDismissRequest = { showDialog = false }
            ) {
                // Replace the DatePicker with your custom date picker component
                // Customize the appearance, theming, and behavior as needed
                // Use the selectedDate variable to pass the selected date back to the Composable

                // For example:
                Column {
                    Button(
                        onClick = {
                            onDateSelected(selectedDate)
                            showDialog = false
                        },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text(text = "Confirm")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CustomDatePickerPreview() {
    CustomDatePicker(
        onDateSelected = {},
        initialDate = "2023-07-03"
    )
}
