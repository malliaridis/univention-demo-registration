package com.malliaridis.univention

import kotlinx.coroutines.Dispatchers

actual fun platformDispatchers(): AppDispatchers = object : AppDispatchers {
    override val io = Dispatchers.Default
}
