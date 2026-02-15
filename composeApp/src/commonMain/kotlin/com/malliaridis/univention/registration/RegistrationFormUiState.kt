package com.malliaridis.univention.registration

import com.malliaridis.univention.domain.Address

data class RegistrationFormUiState(
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val address: Address = Address(),
    val username: String = "",
    // TODO Move usernameEdited to store
    val usernameEdited: Boolean = false,
    val isLoading: Boolean = false,
)
