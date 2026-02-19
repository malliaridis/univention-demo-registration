package com.malliaridis.univention.validation

import com.malliaridis.univention.PHONE_NUMBER_REGEX
import com.malliaridis.univention.USERNAME_RANGE
import com.malliaridis.univention.USERNAME_REGEX
import com.malliaridis.univention.dto.CreateUserRequestDto

/**
 * Validator for CreateUserRequestDto.
 */
object CreateUserRequestValidator {
    fun validate(request: CreateUserRequestDto): ValidationResult {
        val errors = buildList {
            if (request.firstName.isBlank())
                add(ValidationError(field = "firstName", message = "First name must not be blank"))
            if (request.lastName.isBlank())
                add(ValidationError(field = "lastName", message = "Last name must not be blank"))
            if (request.username.isBlank())
                add(ValidationError(field = "username", message = "Username must not be blank"))
            if (request.username.length !in USERNAME_RANGE)
                add(ValidationError(field = "username", message = "Username must be between 3 and 16 characters long"))
            if (!request.username.matches(USERNAME_REGEX))
                add(ValidationError(field = "username", message = "Username must contain only letters, numbers, dots, dashes and underscores"))
            if (request.phoneNumber.isNotBlank() && !request.phoneNumber.matches(PHONE_NUMBER_REGEX))
                add(ValidationError(field = "phoneNumber", message = "Phone number must be valid"))

            // TODO Add address validation
        }
        return ValidationResult(errors)
    }
}
