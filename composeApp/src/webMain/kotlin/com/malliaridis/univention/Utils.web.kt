package com.malliaridis.univention

import com.malliaridis.univention.ApiV1Endpoints.BASE_PATH

/**
 * Provides the base URL for API requests for web targets.
 *
 * Note that this implementation works for both development and production (docker) builds.
 *
 * @return The base URL for the API as a string.
 */
internal actual fun getApiBaseUrl(): String = BASE_PATH
