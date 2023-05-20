package com.samm.core.domain.apod_models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "apod")
data class Apod(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val copyright: String,
    val date: String,
    val explanation: String,
    val hdurl: String,
    val media_type: String,
    val service_version: String,
    val title: String,
    val url: String
)