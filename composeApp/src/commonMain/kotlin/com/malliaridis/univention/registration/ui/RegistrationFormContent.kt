package com.malliaridis.univention.registration.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isShiftPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import com.malliaridis.univention.domain.Address
import com.malliaridis.univention.registration.RegistrationFormUiState
import com.malliaridis.univention.registration.RegistrationFormViewModel

@Composable
fun RegistrationFormContent(
    viewModel: RegistrationFormViewModel,
    modifier: Modifier = Modifier,
) = RegistrationCard(
    modifier = modifier,
    contentPadding = PaddingValues(all = 16.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp),
) {
    val state by viewModel.uiState.collectAsState()

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
        viewModel = viewModel,
    )

    AddressSection(
        address = state.address,
        onAddressChange = viewModel::onUpdateAddress,
    )

    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = viewModel::onRegister,
        shape = MaterialTheme.shapes.extraSmall,
        enabled = !state.isLoading,
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Loading...")
        }
        else Text("Register")
    }
}

/**
 * Group composable that contains all personal information fields.
 */
@Composable
private fun ColumnScope.PersonalInfoSection(
    state: RegistrationFormUiState,
    viewModel: RegistrationFormViewModel,
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
            onValueChange = viewModel::onUpdateFirstName,
        )

        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = state.lastName,
            label = { Text("Last name") },
            singleLine = true,
            onValueChange = viewModel::onUpdateLastName,
        )
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = state.username,
        onValueChange = viewModel::onUpdateUsername,
        label = { Text("Username") },
        singleLine = true,
    )

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = state.phoneNumber,
        onValueChange = viewModel::onUpdatePhoneNumber,
        label = { Text("Phone number (optional)") },
        singleLine = true,
        placeholder = { Text("+49 123 4567890") },
    )
}

/**
 * Group composable that contains all address fields.
 */
@Composable
private fun AddressSection(
    address: Address,
    onAddressChange: (Address) -> Unit,
) = Column {
    Text(
        text = "Address",
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            modifier = Modifier.weight(4f),
            value = address.street,
            onValueChange = {
                onAddressChange(address.copy(street = it))
            },
            label = { Text("Street") },
            singleLine = true,
        )
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = address.houseNumber,
            onValueChange = {
                onAddressChange(address.copy(houseNumber = it))
            },
            label = { Text("Nr.") },
            singleLine = true,
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
        )
        OutlinedTextField(
            modifier = Modifier.weight(3f),
            value = address.city,
            onValueChange = {
                onAddressChange(address.copy(city = it))
            },
            label = { Text("City") },
            singleLine = true,
        )
    }

    // TODO Check if country is required and add dropdown with filter input if necessary
}

/**
 * Supporting modifier for adding focus navigation with a tab key in text fields.
 */
private fun Modifier.addFocusNavigation(focusManager: FocusManager) = this.onPreviewKeyEvent {
    when (it.type) {
        KeyEventType.KeyDown if it.key == Key.Tab -> {
            focusManager.moveFocus(
                if (it.isShiftPressed) FocusDirection.Previous
                else FocusDirection.Next,
            )
            true
        }
        else -> false
    }
}
