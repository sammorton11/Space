package com.samm.space.common.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.samm.space.pages.nasa_media_library_page.domain.models.Data
import com.samm.space.pages.nasa_media_library_page.domain.models.Link

class ListConverter {
    @TypeConverter
    fun fromString(value: String): List<String>? {
        return value.split(",").map { it.trim() }
    }

    @TypeConverter
    fun toString(list: List<String>?): String {
        return list?.joinToString(",") ?: ""
    }
}

class ItemConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromDataString(dataString: String): List<Data> {
        val type = object : TypeToken<List<Data>>() {}.type
        return gson.fromJson(dataString, type)
    }

    @TypeConverter
    fun toDataString(data: List<Data>): String {
        return gson.toJson(data)
    }

    @TypeConverter
    fun fromLinkString(linkString: String?): List<Link> {
        val type = object : TypeToken<List<Link>>() {}.type
        return gson.fromJson(linkString, type)
    }

    @TypeConverter
    fun toLinkString(links: List<Link>?): String {
        links?.let {
            return gson.toJson(links)
        }
        return gson.toJson(listOf(Link("", "", "")))
    }
}
