package com.malliaridis.univention.registration.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malliaridis.univention.AppDispatchers
import com.malliaridis.univention.domain.Address
import com.malliaridis.univention.registration.RegistrationFormUiState
import com.malliaridis.univention.registration.domain.RegisterUserUseCase
import com.malliaridis.univention.registration.data.toUserRegistrationDto
import com.malliaridis.univention.validation.CreateUserRequestValidator
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Registration form viewmodel.
 *
 * This viewmodel holds the state of the registration form and handles user interactions.
 *
 * @property registerUser Use case for registering a new user.
 * @property dispatchers Coroutine dispatchers used for IO operations.
 */
class RegistrationFormViewModel(
    private val registerUser: RegisterUserUseCase,
    private val dispatchers: AppDispatchers,
) : ViewModel() {

    /**
     * State of the registration form.
     */
    val uiState: StateFlow<RegistrationFormUiState>
        field = MutableStateFlow(RegistrationFormUiState())

    /**
     * Events emitted by the viewmodel.
     */
    val events: SharedFlow<RegistrationFormEvent>
        field = MutableSharedFlow<RegistrationFormEvent>(extraBufferCapacity = 1)

    /**
     * Handles user input for the first name field.
     *
     * @param firstName New first name value provided by the user.
     */
    fun onUpdateFirstName(firstName: String) {
        uiState.value = uiState.value.copy(
            firstName = firstName,
            firstNameError = null,
            generalError = null,
            username = if (useUsername()) uiState.value.username
            else "${firstName.lowercase()}.${uiState.value.lastName.lowercase()}",
        )
    }

    /**
     * Handles user input for the last name field.
     *
     * @param lastName New last name value provided by the user.
     */
    fun onUpdateLastName(lastName: String) {
        uiState.value = uiState.value.copy(
            lastName = lastName,
            lastNameError = null,
            generalError = null,
            username = if (useUsername()) uiState.value.username
            else "${uiState.value.firstName.lowercase()}.${lastName.lowercase()}",
        )
    }

    /**
     * Handles user input for the phone number field.
     *
     * @param phoneNumber New phone number value provided by the user.
     */
    fun onUpdatePhoneNumber(phoneNumber: String) {
        uiState.value = uiState.value.copy(
            phoneNumber = phoneNumber,
            phoneNumberError = null,
            generalError = null,
        )
    }

    /**
     * Handles user input for the username field.
     *
     * @param username New username value provided by the user.
     */
    fun onUpdateUsername(username: String) {
        uiState.value = uiState.value.copy(
            username = username,
            usernameError = null,
            generalError = null,
            // Once the user makes any manual changes,
            // the auto-suggest feature is disabled
            usernameEdited = true,
        )
    }


    /**
     * Handles user input for the address field.
     *
     * @param address New address value provided by the user.
     */
    fun onUpdateAddress(address: Address) {
        uiState.value = uiState.value.copy(
            address = address,
            // TODO Update address errors once introduced
            generalError = null,
        )
    }

    /**
     * Initiates the registration process with the provided data.
     *
     * This function resets the errors, evaluates the input, and triggers the
     * [registerUser] use case.
     */
    fun onRegister() {
        uiState.value = uiState.value.copy(
            isLoading = true,
            firstNameError = null,
            lastNameError = null,
            usernameError = null,
            phoneNumberError = null,
            generalError = null,
        )

        val request = uiState.value.toUserRegistrationDto()
        val validation = CreateUserRequestValidator.validate(request)
        if (!validation.isValid) {
            uiState.value = uiState.value.copy(
                isLoading = false,
                firstNameError = validation.errorMessage("firstName"),
                lastNameError = validation.errorMessage("lastName"),
                usernameError = validation.errorMessage("username"),
                phoneNumberError = validation.errorMessage("phoneNumber"),
                generalError = null,
            )
            return
        }

        uiState.value = uiState.value.copy(
            isLoading = true,
            generalError = null,
        )

        viewModelScope.launch(context = dispatchers.io) {
            registerUser(userRegistration = request).onSuccess {
                resetForm()
                events.tryEmit(RegistrationFormEvent.RegistrationSucceeded(userId = it))
            }.onFailure { error ->
                uiState.value = uiState.value.copy(
                    isLoading = false,
                    generalError = error.message ?: uiState.value.generalError,
                )

                // If the error is critical and the user should not retry,
                // reset the state and navigate to error
                // resetForm()
                // events.tryEmit(
                //     RegistrationFormEvent.RegistrationFailed(message = error.message),
                // )
            }
        }
    }

    private fun resetForm() {
        uiState.value = RegistrationFormUiState()
    }

    private fun useUsername(): Boolean =
        uiState.value.usernameEdited
            || uiState.value.firstName.isBlank()
            || uiState.value.lastName.isBlank()
}
