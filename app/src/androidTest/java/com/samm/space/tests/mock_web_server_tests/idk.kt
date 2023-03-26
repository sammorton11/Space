package com.samm.space.tests.mock_web_server_tests

import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class idk {

    private lateinit var server: MockWebServer

    @Before
    fun setup() {
        server = MockWebServer()
        server.start()
    }

    @After
    fun tearDown() {
        server.shutdown()
        server.close()
    }

    @Test
    fun testServer() {
        assert(server.port == 0)

    }
}