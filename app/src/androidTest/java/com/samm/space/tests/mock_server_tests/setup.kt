package com.samm.space.tests.mock_server_tests

import org.mockserver.client.server.MockServerClient
import org.mockserver.model.Header.header
import org.mockserver.model.HttpRequest.request
import org.mockserver.model.HttpResponse.response

internal fun MockServerClient.setup(
    requestMethod:String,
    requestPath:String,
    responseStatus: Int,
    responseBody:String
) {

    this.`when`(
        request()
            .withMethod(requestMethod)
            .withPath(requestPath)

    )
        .respond(
            response()
              .withStatusCode(responseStatus)
              .withBody(responseBody)
        )
}

internal fun MockServerClient.verifyRequest(
    method: String?,
    path: String?,
    headers: Map<String, *>?
) {
    val request = request()

    method?.let { request.withMethod(method) }
    path?.let { request.withPath(path) }
    headers?.let {
        for ((key, value) in headers) {
            request.withHeader(
              header(key, value.toString())
            )
        }
    }

    this.verify(request)
}