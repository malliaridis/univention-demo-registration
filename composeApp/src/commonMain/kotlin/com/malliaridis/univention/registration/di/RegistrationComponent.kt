package com.malliaridis.univention.registration.di

import com.malliaridis.univention.registration.ui.RegistrationFormViewModel
import com.malliaridis.univention.registration.ui.UserRegistrationViewModel
import com.malliaridis.univention.registration.domain.RegisterUserUseCase
import com.malliaridis.univention.registration.repository.UserRegistrationRepository

/**
 * Component responsible for the dependencies of the user registration flow.
 *
 * This component provides access to essential dependencies and factory methods for creating viewmodels
 * used in the registration UI flow.
 */
interface RegistrationComponent {

    /**
     * Dependencies provided by the application.
     */
    val userRegistrationRepository: UserRegistrationRepository

    /**
     * Use case responsible for registering a new user.
     */
    val registerUserUseCase: RegisterUserUseCase

    /**
     * Factory method to create a [UserRegistrationViewModel] instance.
     */
    fun createUserRegistrationViewModel(): UserRegistrationViewModel

    /**
     * Factory method to create a [RegistrationFormViewModel] instance.
     */
    fun createRegistrationFormViewModel(): RegistrationFormViewModel
}
