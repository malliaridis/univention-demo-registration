package com.malliaridis.univention.registration.ui

import kotlin.uuid.Uuid

/**
 * Events the RegistrationFormViewModel can emit.
 */
sealed interface RegistrationFormEvent {
    /**
     * Indicates that the registration process has succeeded and a user has been created.
     *
     * @property userId The id of the newly created user.
     */
    data class RegistrationSucceeded(val userId: Uuid): RegistrationFormEvent

    /**
     * Indicates that the registration process has failed. This error is only emitted if the user
     * should not attempt to retry the registration with the same data again.
     *
     * @property message The error message.
     */
    data class RegistrationFailed(val message: String): RegistrationFormEvent
}
