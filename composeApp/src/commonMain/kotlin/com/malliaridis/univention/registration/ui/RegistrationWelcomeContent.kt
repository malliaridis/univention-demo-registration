package com.malliaridis.univention.registration.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Content for displaying a welcome message and button for starting the registration process.
 *
 * @param onRegister Callback invoked when the start registration button is clicked.
 * @param modifier Modifier to be applied to the layout.
 */
@Composable
fun RegistrationWelcomeContent(
    onRegister: () -> Unit,
    modifier: Modifier = Modifier,
) = RegistrationCard(
    modifier = modifier,
    contentPadding = PaddingValues(all = 16.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp),
) {
    Text(
        text = "Welcome to the registration process!",
        style = MaterialTheme.typography.titleLarge,
    )

    Text(
        text = "You are about to register on a test platform.",
        style = MaterialTheme.typography.bodyMedium,
    )

    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onRegister,
        shape = MaterialTheme.shapes.extraSmall,
    ) {
        Text("Start Registration")
    }
}
