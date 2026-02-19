package com.malliaridis.univention.registration.repository

import com.malliaridis.univention.dto.CreateUserRequestDto
import kotlin.uuid.Uuid

/**
 * Interface for user registration operations.
 */
interface UserRegistrationRepository {

    /**
     * Registers a new user.
     *
     * @param userRegistration The user registration data to process.
     */
    suspend fun registerUser(userRegistration: CreateUserRequestDto): Result<Uuid>
}
