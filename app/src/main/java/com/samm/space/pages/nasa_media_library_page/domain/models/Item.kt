package com.samm.space.pages.nasa_media_library_page.domain.models

data class Item(
    val href: String?,
    val data: List<Data>,
    val links: List<Link>
)