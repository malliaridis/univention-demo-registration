package com.malliaridis.univention.registration.data

import com.malliaridis.univention.ApiV1Endpoints.USERS
import com.malliaridis.univention.dto.CreateUserRequestDto
import com.malliaridis.univention.dto.CreateUserResponseDto
import com.malliaridis.univention.registration.repository.UserRegistrationRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.isSuccess
import kotlin.uuid.Uuid

/**
 * Implementation of [UserRegistrationRepository] that uses HTTP for user registration operations.
 *
 * @property httpClient The pre-configured HTTP client to use for user registration operations.
 */
class HttpUserRegistrationRepository(private val httpClient: HttpClient) : UserRegistrationRepository {

    override suspend fun registerUser(userRegistration: CreateUserRequestDto) : Result<Uuid> = runCatching {
        val response = httpClient.post(urlString = USERS) {
            setBody(userRegistration)
        }

        if (!response.status.isSuccess()) {
            error("Registration failed with status ${response.status}")
        }

        Uuid.parse(response.body<CreateUserResponseDto>().userId)
    }
}
