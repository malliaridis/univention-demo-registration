package com.malliaridis.univention.registration.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.malliaridis.univention.domain.Address
import com.malliaridis.univention.registration.RegistrationFormUiState
import kotlinx.coroutines.flow.collectLatest

/**
 * Registration form route that uses the [viewModel] for handling interactions with
 * the registration form.
 *
 * This is a stateful composable used for routing.
 *
 * @param viewModel Registration form view model for handling interactions.
 * @param onRegistrationSuccess Callback invoked when registration is successful.
 * @param onRegistrationFailure Callback invoked when registration fails, providing
 * the failure message.
 * @param modifier Modifier to be applied to the layout.
 */
@Composable
internal fun RegistrationFormRoute(
    viewModel: RegistrationFormViewModel,
    onRegistrationSuccess: () -> Unit,
    onRegistrationFailure: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val onSuccess by rememberUpdatedState(onRegistrationSuccess)
    val onFailure by rememberUpdatedState(onRegistrationFailure)
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is RegistrationFormEvent.RegistrationSucceeded -> onSuccess()
                is RegistrationFormEvent.RegistrationFailed -> onFailure(event.message)
            }
        }
    }

    RegistrationFormContent(
        modifier = modifier,
        state = state,
        onUpdateFirstName = viewModel::onUpdateFirstName,
        onUpdateLastName = viewModel::onUpdateLastName,
        onUpdateUsername = viewModel::onUpdateUsername,
        onUpdatePhoneNumber = viewModel::onUpdatePhoneNumber,
        onUpdateAddress = viewModel::onUpdateAddress,
        onRegister = viewModel::onRegister,
    )
}

/**
 * Registration form content that displays user registration form fields and handles
 * user interactions.
 *
 * This is a stateless composable.
 *
 * @param state State of the registration form.
 * @param onUpdateFirstName Callback invoked when the first name field is updated.
 * @param onUpdateLastName Callback invoked when the last name field is updated.
 * @param onUpdateUsername Callback invoked when the username field is updated.
 * @param onUpdatePhoneNumber Callback invoked when the phone number field is updated.
 * @param onUpdateAddress Callback invoked when a field updates the [Address].
 * @param onRegister Callback invoked when the user clicks the register button.
 * @param modifier Modifier to be applied to the layout.
 */
@Composable
internal fun RegistrationFormContent(
    state: RegistrationFormUiState,
    onUpdateFirstName: (String) -> Unit,
    onUpdateLastName: (String) -> Unit,
    onUpdateUsername: (String) -> Unit,
    onUpdatePhoneNumber: (String) -> Unit,
    onUpdateAddress: (Address) -> Unit,
    onRegister: () -> Unit,
    modifier: Modifier = Modifier,
) = RegistrationCard(
    modifier = modifier,
    contentPadding = PaddingValues(all = 16.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp),
) {
    Text(
        text = "Registration Form",
        style = MaterialTheme.typography.titleLarge,
    )

    Text(
        text = "To register to our platform you have to provide some basic information.",
        style = MaterialTheme.typography.bodyMedium,
    )

    PersonalInfoSection(
        state = state,
        onUpdateFirstName = onUpdateFirstName,
        onUpdateLastName = onUpdateLastName,
        onUpdateUsername = onUpdateUsername,
        onUpdatePhoneNumber = onUpdatePhoneNumber,
    )

    AddressSection(
        state = state,
        onAddressChange = onUpdateAddress,
    )

    if (state.generalError != null) Text(
        text = state.generalError,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.error,
    )

    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onRegister,
        shape = MaterialTheme.shapes.extraSmall,
        enabled = !state.isLoading,
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Loading...")
        } else Text("Register")
    }
}

/**
 * Group composable that contains all personal information fields.
 *
 * @param state State of the registration form.
 * @param onUpdateFirstName Callback invoked when the first name field is updated.
 * @param onUpdateLastName Callback invoked when the last name field is updated.
 * @param onUpdateUsername Callback invoked when the username field is updated.
 * @param onUpdatePhoneNumber Callback invoked when the phone number field is updated.
 */
@Composable
private fun PersonalInfoSection(
    state: RegistrationFormUiState,
    onUpdateFirstName: (String) -> Unit,
    onUpdateLastName: (String) -> Unit,
    onUpdateUsername: (String) -> Unit,
    onUpdatePhoneNumber: (String) -> Unit,
) {
    Text(
        text = "Personal Information",
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = state.firstName,
            label = { Text("First name") },
            singleLine = true,
            enabled = !state.isLoading,
            onValueChange = onUpdateFirstName,
            isError = state.firstNameError != null,
            supportingText = state.firstNameError?.let { { Text(it) } },
        )

        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = state.lastName,
            label = { Text("Last name") },
            singleLine = true,
            enabled = !state.isLoading,
            onValueChange = onUpdateLastName,
            isError = state.lastNameError != null,
            supportingText = state.lastNameError?.let { { Text(it) } },
        )
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = state.username,
        onValueChange = onUpdateUsername,
        label = { Text("Username") },
        singleLine = true,
        enabled = !state.isLoading,
        isError = state.usernameError != null,
        supportingText = state.usernameError?.let { { Text(it) } },
    )

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = state.phoneNumber,
        onValueChange = onUpdatePhoneNumber,
        label = { Text("Phone number (optional)") },
        singleLine = true,
        enabled = !state.isLoading,
        placeholder = { Text("+49 123 4567890") },
        isError = state.phoneNumberError != null,
        supportingText = state.phoneNumberError?.let { { Text(it) } },
    )
}

/**
 * Group composable that contains all address fields.
 *
 * @param state State of the registration form.
 * @param onAddressChange Callback invoked when an address field updates the current [Address].
 */
@Composable
private fun AddressSection(
    state: RegistrationFormUiState,
    onAddressChange: (Address) -> Unit,
) = Column {
    val address = state.address
    Text(
        text = "Address",
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            modifier = Modifier.weight(weight = 4f),
            value = address.street,
            onValueChange = {
                onAddressChange(address.copy(street = it))
            },
            label = { Text("Street") },
            singleLine = true,
            enabled = !state.isLoading,
        )
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = address.houseNumber,
            onValueChange = {
                onAddressChange(address.copy(houseNumber = it))
            },
            label = { Text("Nr.") },
            singleLine = true,
            enabled = !state.isLoading,
        )
    }

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = address.zipCode,
            onValueChange = {
                onAddressChange(address.copy(zipCode = it))
            },
            label = { Text("ZIP") },
            singleLine = true,
            enabled = !state.isLoading,
        )
        OutlinedTextField(
            modifier = Modifier.weight(weight = 3f),
            value = address.city,
            onValueChange = {
                onAddressChange(address.copy(city = it))
            },
            label = { Text("City") },
            singleLine = true,
            enabled = !state.isLoading,
        )
    }
}
