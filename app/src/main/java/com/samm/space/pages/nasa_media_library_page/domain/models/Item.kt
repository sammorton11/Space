package com.samm.space.pages.nasa_media_library_page.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.samm.space.pages.favorites_page.data.database.ItemConverter
import com.samm.space.pages.favorites_page.data.database.ListConverter

@Entity(tableName = "library_items_table")
@TypeConverters(ListConverter::class, ItemConverter::class)
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val href: String?,
    val data: List<Data>,
    val links: List<Link>
)