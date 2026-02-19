package com.malliaridis.univention.database

/**
 * Default path for database migration scripts.
 *
 * TODO Consider making this value configurable via environment variables or application properties.
 */
internal const val MIGRATIONS_DIRECTORY = "db/migration"

/**
 * Database URL for in-memory H2 database.
 *
 * TODO Consider making this value configurable via environment variables or application properties.
 */
internal const val DATABASE_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"

/**
 * Database driver for in-memory H2 database.
 */
internal const val H2_DATABASE_DRIVER = "org.h2.Driver"

/**
 * Database driver for PostgreSQL database.
 */
internal const val POSTGRES_DATABASE_DRIVER = "org.postgresql.Driver"

/**
 * Max character limit for varchar table columns.
 *
 * This serves as a general-purpose length, and is not intended for production.
 * TODO Use more realistic length based on the input fields.
 */
internal const val MAX_VARCHAR_LENGTH = 128

/**
 * Database connection URL environment variable.
 */
internal const val DATABASE_JDBC_URL_KEY = "DB_JDBC_URL"

/**
 * Database driver environment variable.
 */
internal const val DATABASE_DRIVER_KEY = "DB_DRIVER"

/**
 * Database user environment variable.
 */
internal const val DATABASE_USER_KEY = "DB_USER"

/**
 * Database password environment variable.
 */
internal const val DATABASE_PASSWORD_KEY = "DB_PASSWORD"
