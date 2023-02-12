package com.example.space.domain.models

data class ItemData(
    var center: String,
    var dateCreated: String,
    var description: String,
    var href: String,
    var itemLinks: ItemDataLinks,
    var title: String
)

data class ItemDataLinks(
    var href: String,
    var prompt: String,
    var rel: String
)