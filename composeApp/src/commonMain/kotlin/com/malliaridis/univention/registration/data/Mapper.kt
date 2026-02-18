package com.malliaridis.univention.registration.data

import com.malliaridis.univention.dto.CreateUserRequestDto
import com.malliaridis.univention.registration.RegistrationFormUiState

/**
 * Converts a [RegistrationFormUiState] to a [CreateUserRequestDto].
 */
fun RegistrationFormUiState.toUserRegistrationDto(): CreateUserRequestDto = CreateUserRequestDto(
    firstName = firstName,
    lastName = lastName,
    username = username,
    phoneNumber = phoneNumber,
    address = address,
)
