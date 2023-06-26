package com.samm.space.features.nasa_media_library_page.domain.models

data class Collection(
    val version: String?,
    val href: String?,
    val items: List<Item?>
)

data class NasaLibraryResponse(
    val collection: Collection
)




