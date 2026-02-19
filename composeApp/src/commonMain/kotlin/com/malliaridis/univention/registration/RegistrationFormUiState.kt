package com.malliaridis.univention.registration

import com.malliaridis.univention.domain.Address

/**
 * State for the registration form UI.
 *
 * @property firstName Input value for the user's first name.
 * @property lastName Input value for the user's last name.
 * @property phoneNumber Input value for the user's phone number.
 * @property address Input value for the user's address. This is a composite value that is updated as an object.
 * @property username Input value for the user's username.
 * @property usernameEdited Whether the username has been edited since the last successful registration.
 * Affects auto-completion of the username field.
 * @property isLoading Whether the registration process is currently in progress. Affects enabled state of input fields.
 * @property firstNameError Error message for the [firstName] field.
 * @property lastNameError Error message for the [lastName] field.
 * @property usernameError Error message for the [username] field.
 * @property phoneNumberError Error message for the [phoneNumber] field.
 * @property generalError Error message for the form as a whole.
 */
data class RegistrationFormUiState(
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val address: Address = Address(),
    val username: String = "",
    val usernameEdited: Boolean = false,
    val isLoading: Boolean = false,
    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val usernameError: String? = null,
    val phoneNumberError: String? = null,
    val generalError: String? = null,
    // TODO Add address field errors too
)
