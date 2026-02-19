package com.malliaridis.univention

import kotlinx.coroutines.CoroutineDispatcher

/**
 * App dispatchers used for coroutines.
 */
interface AppDispatchers {

    /**
     * Coroutine dispatcher used for IO operations.
     */
    val io: CoroutineDispatcher
}

/**
 * Factory function to provide platform-specific dispatchers.
 */
expect fun platformDispatchers(): AppDispatchers
