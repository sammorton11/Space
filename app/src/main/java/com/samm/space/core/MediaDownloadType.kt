package com.samm.space.core

enum class MediaDownloadType(val mimeType: String, val subPath: String) {
    IMAGE_JPEG("image/jpeg", "image.jpeg"),
    AUDIO_WAV("audio/x-wav", "audio.wav"),
    VIDEO("video/mp4", "video.mp4");
}