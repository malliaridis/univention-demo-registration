package com.malliaridis.univention.registration.integration

import androidx.lifecycle.ViewModel
import com.malliaridis.univention.domain.Address
import com.malliaridis.univention.registration.RegistrationFormViewModel
import com.malliaridis.univention.registration.RegistrationFormUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * TODO Use store provider
 */
internal class DefaultRegistrationFormViewModel : ViewModel(), RegistrationFormViewModel {
    final override val uiState: StateFlow<RegistrationFormUiState>
        field = MutableStateFlow(RegistrationFormUiState())

    override fun onUpdateFirstName(firstName: String) {
        uiState.value = uiState.value.copy(
            firstName = firstName,
            username = if (useUsername()) uiState.value.username
            else "${firstName.lowercase()}.${uiState.value.lastName.lowercase()}",
        )
    }

    override fun onUpdateLastName(lastName: String) {
        uiState.value = uiState.value.copy(
            lastName = lastName,
            username = if (useUsername()) uiState.value.username
            else "${uiState.value.firstName.lowercase()}.${lastName.lowercase()}",
        )
    }

    override fun onUpdatePhoneNumber(phoneNumber: String) {
        // TODO Add pre-validation to filter out invalid characters
        // TODO Add post-validation to verify validity of phone number on submit, e.g. via
        //  - regex ^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$
        //  - library like libphonenumber
        uiState.value = uiState.value.copy(phoneNumber = phoneNumber)
    }

    override fun onUpdateUsername(username: String) {
        uiState.value = uiState.value.copy(
            username = username,
            // Once the user makes any manual changes, the auto-suggest feature is disabled
            usernameEdited = true,
        )
    }

    override fun onUpdateAddress(address: Address) {
        uiState.value = uiState.value.copy(address = address)
    }

    override fun onRegister() {
        uiState.value = uiState.value.copy(isLoading = true)
        // TODO Not yet implemented
    }

    private fun useUsername(): Boolean =
        uiState.value.usernameEdited || uiState.value.firstName.isBlank() || uiState.value.lastName.isBlank()
}
