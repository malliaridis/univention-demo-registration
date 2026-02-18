package com.malliaridis.univention.registration.domain

import com.malliaridis.univention.dto.CreateUserRequestDto
import com.malliaridis.univention.registration.repository.UserRegistrationRepository
import kotlin.uuid.Uuid

/**
 * Default implementation of [RegisterUserUseCase].
 *
 * @property repository The repository to use for user registration.
 */
class DefaultRegisterUserUseCase(
    private val repository: UserRegistrationRepository,
) : RegisterUserUseCase {
    override suspend fun invoke(userRegistration: CreateUserRequestDto): Result<Uuid> =
        // TODO Consider validating input here again to avoid misuse
        repository.registerUser(userRegistration)
}
