package com.example.space.domain.models

data class NasaLibraryResponse(
    val collection: Collection
)

data class Collection(
    val version: String?,
    val href: String?,
    val items: List<Item>?
)

data class Item(
    val href: String?,
    val data: List<Data>,
    val links: List<Link>
)

data class Data(
    val center: String?,
    val title: String?,
    val nasa_id: String?,
    val date_created: String?,
    val keywords: List<String>,
    val media_type: String?,
    val description_508: String?,
    val secondary_creator: String?,
    val description: String?
)

data class Link(
    val href: String?,
    val rel: String?,
    val render: String?
)




