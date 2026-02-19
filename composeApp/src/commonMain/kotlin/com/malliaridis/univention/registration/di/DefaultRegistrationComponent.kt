package com.malliaridis.univention.registration.di

import com.malliaridis.univention.platformDispatchers
import com.malliaridis.univention.registration.domain.DefaultRegisterUserUseCase
import com.malliaridis.univention.registration.domain.RegisterUserUseCase
import com.malliaridis.univention.registration.ui.RegistrationFormViewModel
import com.malliaridis.univention.registration.ui.UserRegistrationViewModel
import com.malliaridis.univention.registration.data.HttpUserRegistrationRepository
import com.malliaridis.univention.registration.repository.UserRegistrationRepository
import io.ktor.client.HttpClient

/**
 * Default implementation of [RegistrationComponent].
 *
 * This implementation is using HTTP for user registration operations.
 *
 * @property httpClient The pre-configured HTTP client to use for user registration operations.
 */
internal class DefaultRegistrationComponent(
    httpClient: HttpClient,
) : RegistrationComponent {

    private val dispatchers by lazy { platformDispatchers() }

    override val userRegistrationRepository: UserRegistrationRepository by lazy {
        HttpUserRegistrationRepository(httpClient)
    }

    override val registerUserUseCase: RegisterUserUseCase by lazy {
        DefaultRegisterUserUseCase(userRegistrationRepository)
    }

    override fun createUserRegistrationViewModel() = UserRegistrationViewModel()

    override fun createRegistrationFormViewModel() =
        RegistrationFormViewModel(
            registerUser = registerUserUseCase,
            dispatchers = dispatchers,
        )
}
