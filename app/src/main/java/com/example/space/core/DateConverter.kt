package org.michaelbel.movies.common.converter

import java.text.SimpleDateFormat
import java.util.*

object DateConverter {

    private const val DEFAULT_FORMAT = "yyyy-MM-dd"
    private const val DISPLAY_FORMAT = "MMM dd yyyy"

    fun formatDate(date: String, format: String = DEFAULT_FORMAT): String {
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        val input = parseDate(date)
        val output = input?.let { formatter.format(it) }
        return output.toString()
    }

    fun parseDate(dateString: String, format: String = DEFAULT_FORMAT): Date? {
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        return formatter.parse(dateString)
    }

    fun formatDisplayDate(date: String): String {
        return formatDate(date, DISPLAY_FORMAT)
    }
}
