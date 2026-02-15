package com.malliaridis.univention.registration

import kotlinx.serialization.Serializable

@Serializable
sealed interface RegistrationScene {

    @Serializable
    object WelcomeScene : RegistrationScene

    @Serializable
    data class RegistrationFormScene(val viewModel: RegistrationFormViewModel) : RegistrationScene

    @Serializable
    object SuccessScene : RegistrationScene

    @Serializable
    object FailureScene : RegistrationScene
}
