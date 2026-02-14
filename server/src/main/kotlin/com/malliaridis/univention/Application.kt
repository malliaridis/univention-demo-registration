package com.malliaridis.univention

import com.malliaridis.univention.ApiV1Endpoints.BASE_PATH
import com.malliaridis.univention.ApiV1Endpoints.USERS
import com.malliaridis.univention.database.DatabaseConfig
import com.malliaridis.univention.database.MIGRATIONS_DIRECTORY
import com.malliaridis.univention.database.tables.AddressesTable
import com.malliaridis.univention.database.tables.UsersTable
import com.malliaridis.univention.dto.CreateUserRequestDto
import com.malliaridis.univention.dto.CreateUserResponseDto
import com.malliaridis.univention.registration.UserRepository
import com.malliaridis.univention.registration.integration.JDBCUserRepository
import com.malliaridis.univention.validation.CreateUserRequestValidator
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.v1.core.ExperimentalDatabaseMigrationApi
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.migration.jdbc.MigrationUtils

/**
 * Main application entry point.
 *
 * Before the server is started, a database connection is established.
 */
@OptIn(ExperimentalDatabaseMigrationApi::class)
fun main() {
    val config = DatabaseConfig.fromEnvironment()

    if (config.user != null) {
        Database.connect(
            url = config.jdbcUrl,
            driver = config.driver,
            user = config.user,
            password = config.password ?: "",
        )
    } else {
        Database.connect(url = config.jdbcUrl, driver = config.driver)
    }
    // TODO Add database connection error handling

    transaction {
        Path(MIGRATIONS_DIRECTORY).createDirectories()
        MigrationUtils.generateMigrationScript(
            AddressesTable, UsersTable,
            scriptDirectory = MIGRATIONS_DIRECTORY,
            // TODO Make script name based on current release version
            scriptName = "v1_create_tables",
            // TODO Introduce logic for applying database migrations
        )
        SchemaUtils.create(AddressesTable, UsersTable)
    }

    embeddedServer(
        factory = Netty,
        port = SERVER_PORT,
        host = "0.0.0.0",
        module = Application::module,
    ).start(wait = true)
}

/**
 * Application module configuration.
 *
 * This module is responsible for configuring the application's routing, content negotiation,
 * and dependency injection.
 *
 * Note that in case the application scales, this module should be split into
 * multiple modules instead.
 */
fun Application.module() {
    log.info("developmentMode={}", developmentMode)
    val isDev = developmentMode

    if (isDev) {
        install(CORS) {
            anyHost() // allow other origins, should be limited to specific hosts in the future
            allowHeader(HttpHeaders.ContentType)
        }
    }
    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
                explicitNulls = false
            }
        )
    }

    val userRepository: UserRepository = JDBCUserRepository()

    routing {
        route(BASE_PATH) {
            post(USERS) {
                val request = call.receive<CreateUserRequestDto>()
                val validation = CreateUserRequestValidator.validate(request)

                if (!validation.isValid) {
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        // TODO Create a proper error response DTO and wrap errors
                        //  so that consumers can use it
                        message = validation.errors,
                    )
                    return@post
                }

                // TODO Catch errors and wrap data to error response DTO
                val created = userRepository.createUser(request).getOrThrow()
                call.respond(CreateUserResponseDto(userId = created.toString()))
            }
        }
    }
}
