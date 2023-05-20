package com.samm.space

import org.junit.Assert.assertEquals
import org.junit.Test

class MediaTypeTests {

    @Test
    fun `test fromString() with valid type`() {
        val result = MediaType.fromString("image")
        assertEquals(MediaType.IMAGE, result)
    }

    @Test
    fun `test fromString() with invalid type`() {
        val result = MediaType.fromString("invalid")
        assertEquals(null, result)
    }

    @Test
    fun `test toMediaType() with valid type`() {
        val result = "audio".toMediaType()
        assertEquals(MediaType.AUDIO, result)
    }

    @Test
    fun `test toMediaType() with invalid type`() {
        val result = "invalid".toMediaType()
        assertEquals(MediaType.IMAGE, result)
    }
}
