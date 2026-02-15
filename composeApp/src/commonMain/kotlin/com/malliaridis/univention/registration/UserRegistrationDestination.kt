package com.malliaridis.univention.registration

// TODO See if this should be sealed interface with data class support
enum class UserRegistrationDestination {
    Welcome,
    RegistrationForm,
    CompletionSuccess,
    CompletionFailure,
}
