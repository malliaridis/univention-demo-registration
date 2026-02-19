package com.malliaridis.univention

import com.malliaridis.univention.ApiV1Endpoints.BASE_PATH
import com.malliaridis.univention.registration.API_BASE_URL_KEY

/**
 * Provides the base URL for API requests for JVM targets.
 *
 * Note that for local development the application is using a fallback and works out of the box.
 * For production, the API_BASE_URL environment variable must be set to point to the right URL if it is
 * other than http://127.0.0.1:3000.
 */
internal actual fun getApiBaseUrl(): String {
    val override = System.getProperty(API_BASE_URL_KEY) ?: System.getenv(API_BASE_URL_KEY)
    if (!override.isNullOrBlank()) return override

    // Default for local dev when running server directly:
    return "http://127.0.0.1:$SERVER_PORT$BASE_PATH"
}
