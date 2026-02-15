package com.malliaridis.univention.registration

import com.malliaridis.univention.domain.Address
import kotlinx.coroutines.flow.StateFlow

interface RegistrationFormViewModel {

    val uiState: StateFlow<RegistrationFormUiState>

    fun onUpdateFirstName(firstName: String)

    fun onUpdateLastName(lastName: String)

    fun onUpdatePhoneNumber(phoneNumber: String)

    fun onUpdateUsername(username: String)

    fun onUpdateAddress(address: Address)

    fun onRegister()
}
