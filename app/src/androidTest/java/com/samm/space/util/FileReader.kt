package com.samm.space.util

import androidx.test.platform.app.InstrumentationRegistry
import java.io.InputStreamReader

object FileReader {

    fun getStringFromFile(fileName: String): String {
        try {
            val inputStream = InstrumentationRegistry.getInstrumentation()
                .context.resources.assets.open(fileName)
            val builder = java.lang.StringBuilder()
            val reader = InputStreamReader(inputStream, "UTF-8")
            reader.readLines().forEach {
                builder.append(it)
            }
            return builder.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}