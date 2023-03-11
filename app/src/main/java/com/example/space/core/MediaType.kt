package com.example.space.core

enum class MediaType(val type: String) {

    IMAGE("image"),
    VIDEO("video"),
    AUDIO("audio");

    companion object {
        fun fromString(type: String): MediaType? = values().find {
            it.type.equals(type, ignoreCase = true)
        }
        fun MediaType.toBundle(): String {
            return this.type
        }
        fun String.toMediaType(): MediaType {
            return MediaType.values().firstOrNull {
                it.type == this
            } ?: IMAGE
        }
    }
}