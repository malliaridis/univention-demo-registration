package com.malliaridis.univention

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform