package com.samm.core.domain.library_models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.samm.core.data.database.ItemConverter
import com.samm.core.data.database.ListConverter


@Entity(tableName = "library_items_table", indices = [Index(value = ["href"], unique = true)])
@TypeConverters(ListConverter::class, ItemConverter::class)
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false,
    val href: String?,
    val data: List<Data?>,
    val links: List<Link?>
)