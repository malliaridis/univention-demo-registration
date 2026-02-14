package com.malliaridis.univention.registration

import kotlinx.serialization.Serializable

/**
 * Represents different scenes or views within the
 * [com.malliaridis.univention.registration.ui.UserRegistrationActivity].
 *
 * These scenes can be seen as configurations that are used for initializing each
 * screen / content section. They are used as navigation elements allowing navigation
 * between different parts of the registration flow.
 */
@Serializable
sealed interface RegistrationScene {

    /**
     * Scene representing the welcome screen.
     */
    @Serializable
    data object WelcomeScene : RegistrationScene

    /**
     * Scene representing the registration form.
     */
    @Serializable
    data object RegistrationFormScene : RegistrationScene

    /**
     * Scene representing a successful registration attempt.
     */
    @Serializable
    data object SuccessScene : RegistrationScene

    /**
     * Scene representing a final failed registration attempt.
     * @property message The error message to display to the user. If null,
     * a default error message will be used.
     */
    @Serializable
    data class FailureScene(val message: String? = null) : RegistrationScene
}
