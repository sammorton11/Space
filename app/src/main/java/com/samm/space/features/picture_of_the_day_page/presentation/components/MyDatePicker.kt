package com.samm.space.features.picture_of_the_day_page.presentation.components

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.util.Calendar
import java.util.Date

@Composable
fun MyDatePicker(getData: (String) -> Unit)  {
    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

    calendar.time = Date()
    val maxDateInMillis = calendar.timeInMillis

    val selectedDate = remember { mutableStateOf("") }

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                selectedDate.value = "$year-${month + 1}-$dayOfMonth"
            },
            year, month, dayOfMonth
        ).apply {
            datePicker.maxDate = maxDateInMillis
            setOnDismissListener {
                if (selectedDate.value.isNotBlank()) {
                    getData(selectedDate.value)
                }
            }
        }
    }

    // Button to open date picker
    FloatingActionButton(
        onClick = {
            datePickerDialog.show()
        },
        modifier = Modifier.padding(15.dp)
    ) {
        Icon(imageVector = Icons.Default.DateRange, contentDescription = "Date Picker")
    }
}