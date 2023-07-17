package com.samm.space.features.picture_of_the_day_page.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.samm.space.features.favorites_page.data.database.ApodTypeConverters

@Entity(tableName = "apod")
@TypeConverters(ApodTypeConverters::class)
data class Apod(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val copyright: String? = null ?: "",
    val date: String,
    val explanation: String,
    val hdurl: String,
    val media_type: String,
    val service_version: String,
    val title: String,
    val url: String
)