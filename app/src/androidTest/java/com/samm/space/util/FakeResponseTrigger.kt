package com.samm.space.util

enum class FakeResponseTrigger(val value: String) {
    SUCCESS("success"),
    HTTP_ERROR("httpError"),
    IO_ERROR("ioError")
}

fun determineResponseTrigger(query: String): FakeResponseTrigger {
    return FakeResponseTrigger.values().find { it.value == query }
        ?: throw IllegalArgumentException("Unknown trigger: $query")
}