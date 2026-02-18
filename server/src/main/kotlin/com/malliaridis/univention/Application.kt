package com.malliaridis.univention

import com.malliaridis.univention.ApiV1Endpoints.BASE_PATH
import com.malliaridis.univention.ApiV1Endpoints.USERS
import com.malliaridis.univention.database.DATABASE_URL
import com.malliaridis.univention.database.H2_DATABASE_DRIVER
import com.malliaridis.univention.database.MIGRATIONS_DIRECTORY
import com.malliaridis.univention.database.tables.AddressesTable
import com.malliaridis.univention.database.tables.UsersTable
import com.malliaridis.univention.dto.CreateUserRequestDto
import com.malliaridis.univention.dto.CreateUserResponseDto
import com.malliaridis.univention.registration.UserRepository
import com.malliaridis.univention.registration.integration.H2UserRepository
import com.malliaridis.univention.validation.CreateUserRequestValidator
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.v1.core.ExperimentalDatabaseMigrationApi
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.migration.jdbc.MigrationUtils
import kotlin.io.path.Path
import kotlin.io.path.createDirectories

/**
 * Main application entry point.
 *
 * Before the server is started, a database connection is established.
 */
@OptIn(ExperimentalDatabaseMigrationApi::class)
fun main() {
    Database.connect(url = DATABASE_URL, driver = H2_DATABASE_DRIVER)
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
 * This module is responsible for configuring the application's routing, content negotiation, and dependency injection.
 * Note that in case the application scales, this module should be split into multiple modules instead.
 */
fun Application.module() {
    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
                explicitNulls = false
            }
        )
    }

    // TODO Consider parameterizing the repository instantiation
    val userRepository: UserRepository = H2UserRepository()

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
