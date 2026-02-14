package com.malliaridis.univention.registration.domain

import com.malliaridis.univention.dto.CreateUserRequestDto
import kotlin.uuid.Uuid

/**
 * Use case for registering a new user.
 */
interface RegisterUserUseCase {
    /**
     * Use case for registering a new user.
     *
     * @param userRegistration The user registration data to be processed. Note that the input
     * is expected to be valid. You may use
     * [com.malliaridis.univention.validation.CreateUserRequestValidator] to validate the input.
     * @return A Result containing the UUID of the newly registered user or an error
     * if registration fails.
     */
    suspend operator fun invoke(userRegistration: CreateUserRequestDto): Result<Uuid>
}
