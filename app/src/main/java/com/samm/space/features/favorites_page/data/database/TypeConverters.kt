package com.samm.space.features.favorites_page.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.samm.space.features.nasa_media_library_page.domain.models.Data
import com.samm.space.features.nasa_media_library_page.domain.models.Link
import com.samm.space.features.picture_of_the_day_page.domain.models.Apod

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


class ApodTypeConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromApod(apod: Apod): String {
        return gson.toJson(apod)
    }

    @TypeConverter
    fun toApod(json: String): Apod {
        return gson.fromJson(json, Apod::class.java)
    }
}