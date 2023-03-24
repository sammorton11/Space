package com.samm.space.tests.mock_web_server_tests

import okhttp3.mockwebserver.MockWebServer

class MockServer {
    companion object {
        val server = MockWebServer()
    }
}