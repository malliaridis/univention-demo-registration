package com.malliaridis.univention

import com.malliaridis.univention.ApiV1Endpoints.BASE_PATH
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.json.Json

/**
 * Retrieve an HTTP client configured to communicate with the server.
 */
internal fun getHttpClient() = HttpClient {
    install(ContentNegotiation) {
        json(
            Json {
                isLenient = true
                ignoreUnknownKeys = true
            }
        )
    }
    defaultRequest {
        // TODO Make server URL configurable
        url("http://127.0.0.1:$SERVER_PORT$BASE_PATH")
        contentType(ContentType.Application.Json)
    }
}
