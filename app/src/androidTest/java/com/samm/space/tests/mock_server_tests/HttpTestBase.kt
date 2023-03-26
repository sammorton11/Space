package com.samm.space.tests.mock_server_tests
import org.mockserver.client.server.MockServerClient
import org.mockserver.integration.ClientAndServer
import java.util.*

val random = Random()
internal fun randomFrom(from: Int = 1024, to: Int = 65535) : Int {
    return random.nextInt(to - from) + from
}

abstract class HttpTestBase {

    private val port = randomFrom()
    var mockServer: MockServerClient = MockServerClient("localhost", port)
    val url = "http://localhost:$port"

    fun prepare() {
        mockServer = ClientAndServer.startClientAndServer(port)
    }


    fun tearDown() {
        mockServer.close()
    }
}