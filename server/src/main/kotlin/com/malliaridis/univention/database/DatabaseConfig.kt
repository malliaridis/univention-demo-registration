package com.malliaridis.univention.database

data class DatabaseConfig(
    val jdbcUrl: String,
    val driver: String,
    val user: String? = null,
    val password: String? = null,
) {
    companion object {
        fun fromEnvironment(): DatabaseConfig {
            val jdbcUrl: String? = System.getenv(DATABASE_JDBC_URL_KEY)
            val driver: String? = System.getenv(DATABASE_DRIVER_KEY)
            val user: String? = System.getenv(DATABASE_USER_KEY)
            val password: String? = System.getenv(DATABASE_PASSWORD_KEY)

            // If DB_JDBC_URL is present, assume external DB config
            if (!jdbcUrl.isNullOrBlank()) {
                val resolvedDriver = driver ?: when {
                    jdbcUrl.startsWith(prefix = "jdbc:postgresql:") -> POSTGRES_DATABASE_DRIVER
                    jdbcUrl.startsWith(prefix = "jdbc:h2:") -> H2_DATABASE_DRIVER
                    else -> error(
                        "$DATABASE_DRIVER_KEY is required for $DATABASE_JDBC_URL_KEY=$jdbcUrl"
                    )
                }

                return DatabaseConfig(
                    jdbcUrl = jdbcUrl,
                    driver = resolvedDriver,
                    user = user,
                    password = password,
                )
            }

            // Otherwise fallback for local development and tests to in-memory database
            return DatabaseConfig(
                jdbcUrl = DATABASE_URL,
                driver = H2_DATABASE_DRIVER,
            )
        }
    }
}
