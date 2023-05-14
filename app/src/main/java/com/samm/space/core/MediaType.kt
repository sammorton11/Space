package com.samm.space.core

enum class MediaType(val type: String) {

    IMAGE("image"),
    VIDEO("video"),
    AUDIO("audio");

    companion object {

        private val valuesMap = HashMap<String, MediaType>()

        init {
            values().forEach {
                valuesMap[it.type.lowercase()] = it
            }
        }

        fun fromString(type: String): MediaType? = valuesMap[type.lowercase()]

        fun String.toMediaType(): MediaType {
            return MediaType.values().firstOrNull { mediaTypeValue ->
                mediaTypeValue.type == this
            } ?: IMAGE
        }
    }
}