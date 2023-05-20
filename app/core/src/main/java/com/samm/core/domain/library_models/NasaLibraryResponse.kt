package com.samm.core.domain.library_models

data class Collection(
    val version: String?,
    val href: String?,
    val items: List<Item?>
)

data class NasaLibraryResponse(
    val collection: Collection
)




