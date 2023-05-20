package com.samm.shared_resources.util

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateConverter {

    private const val DEFAULT_FORMAT = "yyyy-MM-dd"
    private const val DISPLAY_FORMAT = "MMM dd yyyy"

    private fun String.formatDate(date: String): String {
        val formatter = SimpleDateFormat(this, Locale.getDefault())
        val input = parseDate(date)
        val output = input?.let { formatter.format(it) }
        return output.toString()
    }

    private fun parseDate(dateString: String, format: String = DEFAULT_FORMAT): Date? {
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        return formatter.parse(dateString)
    }

    fun formatDisplayDate(date: String?): String {
        if (date == "null" || date == null) {
            return ""
        }
        Log.d("Date:", date)
        return DISPLAY_FORMAT.formatDate(date)
    }
}
